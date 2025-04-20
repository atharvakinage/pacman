package com.example.pacman.util;

import com.example.pacman.db.ScoreDAO;
import com.example.pacman.model.GameModel;
import com.example.pacman.model.Ghost;
import com.example.pacman.model.Pellet;
import com.example.pacman.model.Player;
import com.example.pacman.view.ScoreBoard;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLoop extends AnimationTimer {
    private final GraphicsContext gc;
    private final GameModel model;
    private boolean gameOver = false;
    private boolean playerWon = false;
    private Player player;
    private ScoreBoard scoreBoard;

    public GameLoop(GraphicsContext gc, GameModel model) {
        this.gc = gc;
        this.model = model;
        this.scoreBoard = new ScoreBoard();
        bindArrowKeys();
    }

    @Override
    public void handle(long now) {
        if (gameOver) {
            showEndScreen();
            return;
        }

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 600);
        gc.clearRect(0, 0, 600, 600);

        model.draw(gc);
        model.getPlayer().draw(gc);
        model.getGhosts().forEach(ghost -> ghost.draw(gc));
        model.getWalls().forEach(wall -> wall.draw(gc));
        scoreBoard.updateScore( model.getPlayer().getScore());
        scoreBoard.updatePowerMode( model.isPowerModeActive());

        
        if (model.getPellets().isEmpty()) {
            gameOver = true;
            playerWon = true;
        }

        
        Player player = model.getPlayer();
        for (Ghost ghost : model.getGhosts()) {
            if (ghost.collidesWith(player)) {
                if (!ghost.isFrightened()) {
                    gameOver = true;
                    playerWon = false;
                    break;
                }
            }
        }
    }

    private void showEndScreen() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 600);

        String message = playerWon ? "🎉 YOU WIN!" : "💀 GAME OVER";
        String restartMsg = "Press SPACE to Save Score!!\n & Restart";

       
        gc.setFill(playerWon ? Color.LIMEGREEN : Color.RED);
        gc.setFont(Font.font("Consolas", 52));
        gc.fillText(message, 110, 280);

        
        gc.setFill(Color.DARKGRAY);
        gc.setFont(Font.font("Consolas", 52));
        gc.fillText(message, 112, 282); 

        
        gc.setFill(Color.CYAN);
        gc.setFont(Font.font("Consolas", 26));
        gc.fillText(restartMsg, 150, 350);

        
        gc.getCanvas().requestFocus();
        gc.getCanvas().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                restartGame();
            }
        });
    }



    private void restartGame() {
        int finalScore = model.getPlayer().getScore();
        String playerName = model.getPlayerName();

        System.out.println("Saving score for: " + playerName + " | Score: " + finalScore);
        ScoreDAO dao = ScoreDAO.getInstance();
        dao.insertScore(playerName, finalScore);

        GameModel.resetInstance();
        GameModel newModel = GameModel.getInstance();
        this.model.copyFrom(newModel);
        this.player = model.getPlayer();
        gameOver = false;
        playerWon = false;
        bindArrowKeys();
        scoreBoard.close();
        scoreBoard = new ScoreBoard();



    }

    private void bindArrowKeys(){
        gc.getCanvas().requestFocus();
        gc.getCanvas().setOnKeyPressed(event -> {
            model.getPlayer().handleKey(event.getCode());
        });
    }
}
