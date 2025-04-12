package com.example.pacman.model;

public abstract class GameEntity {
    private double x;
    private double y;

    public GameEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public abstract javafx.geometry.Rectangle2D getBounds();
}