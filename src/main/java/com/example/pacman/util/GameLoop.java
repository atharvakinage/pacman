package com.example.pacman.util;

import com.example.pacman.model.GameModel;
import com.example.pacman.model.Ghost;
import com.example.pacman.model.Pellet;
import com.example.pacman.model.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLoop extends AnimationTimer {
    private final GraphicsContext gc;
    private final GameModel model;
    private boolean gameOver = false;
    private boolean playerWon = false;
    private Player player;

    public GameLoop(GraphicsContext gc, GameModel model) {
        this.gc = gc;
        this.model = model;
        this.player = model.getPlayer();
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

        // Check for pellet win
        if (model.getPellets().isEmpty()) {
            gameOver = true;
            playerWon = true;
        }

        // Check for ghost collision
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
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font("Arial", 48));

        String message = playerWon ? "You Win!" : "Game Over!";
        gc.fillText(message, 180, 300);

        gc.setFont(new Font("Arial", 24));
        gc.setFill(Color.WHITE);
        gc.fillText("Press SPACE to Restart", 170, 350);

        gc.getCanvas().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                restartGame();
            }
        });
    }

    private void restartGame() {
        GameModel.resetInstance();
        GameModel newModel = GameModel.getInstance();
        this.model.copyFrom(newModel);
        this.player = model.getPlayer();
        gameOver = false;
        playerWon = false;
    }
}
