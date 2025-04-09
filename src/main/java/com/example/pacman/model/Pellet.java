package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;

public class Pellet {
    private final double x, y;
    private final boolean isPowerPellet;
    private boolean visible;

    public Pellet(double x, double y, boolean isPowerPellet) {
        this.x = x;
        this.y = y;
        this.isPowerPellet = isPowerPellet;
        this.visible = true;
    }

    public void draw(GraphicsContext gc) {
        if (!visible) return;
        gc.setFill(Color.WHITE);
        double size = isPowerPellet ? 10 : 4;
        gc.fillOval(x + (15 - size / 2), y + (15 - size / 2), size, size);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, 30, 30);
    }

    public boolean isPowerPellet() {
        return isPowerPellet;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
}