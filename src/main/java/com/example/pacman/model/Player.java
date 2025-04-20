package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import java.util.List;

public class Player extends GameEntity {
    private double dx = 0, dy = 0; 
    private int score; 
    private Image pacmanRight;
    private Image pacmanLeft;
    private Image pacmanUp;
    private Image pacmanDown;
    private Image currentImage;

    public Player(double x, double y) {
        super(x, y); 
        this.score = 0;

        
        pacmanRight = new Image(getClass().getResourceAsStream("/pacmanRight.gif"));
        pacmanLeft = new Image(getClass().getResourceAsStream("/pacmanLeft.gif"));
        pacmanUp = new Image(getClass().getResourceAsStream("/pacmanUp.gif"));
        pacmanDown = new Image(getClass().getResourceAsStream("/pacmanDown.gif"));
        currentImage = pacmanRight;
    }

    public void handleKey(KeyCode code) {
       
        switch (code) {
            case UP -> {
                dx = 0;
                dy = -2;
                currentImage = pacmanUp;
            }
            case DOWN -> {
                dx = 0;
                dy = 2;
                currentImage = pacmanDown;
            }
            case LEFT -> {
                dx = -2;
                dy = 0;
                currentImage = pacmanLeft;
            }
            case RIGHT -> {
                dx = 2;
                dy = 0;
                currentImage = pacmanRight;
            }
        }
    }

    public void update(List<Wall> walls, List<Pellet> pellets) {
        double oldX = getX();
        double oldY = getY();
        setX(getX() + dx);
        setY(getY() + dy);

        if (checkCollision(walls)) {
            setX(oldX);
            setY(oldY);
        }

        Rectangle2D bounds = new Rectangle2D(getX(), getY(), 30, 30);
        for (Pellet pellet : pellets) {
            if (pellet.isVisible() && pellet.getBounds().intersects(bounds)) {
                addScore(pellet.isPowerPellet() ? 50 : 10);
                pellet.setVisible(false);
                if (pellet.isPowerPellet()) {
                    GameModel.getInstance().activatePowerMode();
                }
            }
        }
    }

    private boolean checkCollision(List<Wall> walls) {
        
        Rectangle2D bounds = new Rectangle2D(getX(), getY(), 30, 30);
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(bounds)) {
                return true;
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc) {
      
        update(GameModel.getInstance().getWalls(), GameModel.getInstance().getPellets());
        gc.drawImage(currentImage, getX(), getY(), 30, 30);

      
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillText("Score: " + score, 30, 30);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        this.score += value;
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(getX(), getY(), 30, 30);
    }

   
    public boolean collidesWith(Pellet pellet) {
        return this.getBounds().intersects(pellet.getBounds());
    }
}
