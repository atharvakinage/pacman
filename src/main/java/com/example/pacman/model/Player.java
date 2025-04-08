package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;
import java.util.List;

public class Player {
    private double x, y;
    private double dx = 0, dy = 0;
    private int score;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.score = 0;
    }

    public void handleKey(KeyCode code) {
        switch (code) {
            case UP -> {
                dx = 0;
                dy = -2;
            }
            case DOWN -> {
                dx = 0;
                dy = 2;
            }
            case LEFT -> {
                dx = -2;
                dy = 0;
            }
            case RIGHT -> {
                dx = 2;
                dy = 0;
            }
        }
    }

    public void update(List<Wall> walls) {
        double oldX = x;
        double oldY = y;
        x += dx;
        y += dy;
        if (checkCollision(walls)) {
            x = oldX;
            y = oldY;
        }
    }

    private boolean checkCollision(List<Wall> walls) {
        Rectangle2D bounds = new Rectangle2D(x, y, 30, 30);
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(bounds)) {
                return true;
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc) {
        update(GameModel.getInstance().getWalls());
        gc.setFill(Color.YELLOW);
        gc.fillOval(x, y, 30, 30);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 10, 590);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        this.score += value;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}