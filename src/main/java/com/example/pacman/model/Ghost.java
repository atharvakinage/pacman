// File: model/Ghost.java
package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import java.util.List;
import java.util.Random;

public class Ghost {
    private double x, y;
    private final String colorName;
    private int dx, dy;
    private final Random random = new Random();
    private boolean isChasing = false;
    private Player targetPlayer;
    private Image ghostImage;

    public Ghost(double x, double y, String colorName) {
        this.x = x;
        this.y = y;
        this.colorName = colorName;
        loadImage();
        setRandomDirection();
    }

    private void loadImage() {
        String imagePath = "/ghost_" + colorName.toLowerCase() + ".gif";
        ghostImage = new Image(getClass().getResourceAsStream(imagePath));
    }

    public void setChasing(Player player) {
        this.isChasing = true;
        this.targetPlayer = player;
    }

    private void setRandomDirection() {
        int dir = random.nextInt(4);
        switch (dir) {
            case 0 -> { dx = 2; dy = 0; } // Right
            case 1 -> { dx = -2; dy = 0; } // Left
            case 2 -> { dx = 0; dy = 2; } // Down
            case 3 -> { dx = 0; dy = -2; } // Up
        }
    }

    private void setChaseDirection() {
        if (targetPlayer == null) return;
        double px = targetPlayer.getX();
        double py = targetPlayer.getY();

        dx = 0;
        dy = 0;

        if (Math.abs(px - x) > Math.abs(py - y)) {
            dx = (px > x) ? 2 : -2;
        } else {
            dy = (py > y) ? 2 : -2;
        }
    }

    public void move(List<Wall> walls) {
        double oldX = x;
        double oldY = y;

        if (isChasing) {
            setChaseDirection();
        }

        x += dx;
        y += dy;

        Rectangle2D bounds = new Rectangle2D(x, y, 30, 30);
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(bounds)) {
                x = oldX;
                y = oldY;
                if (isChasing) {
                    dx = 0;
                    dy = 0;
                } else {
                    setRandomDirection();
                }
                break;
            }
        }
    }

    public boolean collidesWith(Player player) {
        Rectangle2D ghostBounds = new Rectangle2D(x, y, 30, 30);
        Rectangle2D playerBounds = new Rectangle2D(player.getX(), player.getY(), 30, 30);
        return ghostBounds.intersects(playerBounds);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(ghostImage, x, y, 30, 30);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
} 
