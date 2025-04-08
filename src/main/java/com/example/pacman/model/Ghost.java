package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;
import java.util.List;
import java.util.Random;
import com.example.pacman.model.Player;

public class Ghost {
    private double x, y;
    private final Color color;
    private int dx, dy;
    private final Random random = new Random();

    public Ghost(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        setRandomDirection();
    }

    private void setRandomDirection() {
        int dir = random.nextInt(4);
        switch (dir) {
            case 0 -> { dx = 2; dy = 0; }
            case 1 -> { dx = -2; dy = 0; }
            case 2 -> { dx = 0; dy = 2; }
            case 3 -> { dx = 0; dy = -2; }
        }
    }

    public void move(List<Wall> walls) {
        double oldX = x;
        double oldY = y;
        x += dx;
        y += dy;
        Rectangle2D bounds = new Rectangle2D(x, y, 30, 30);
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(bounds)) {
                x = oldX;
                y = oldY;
                setRandomDirection();
                break;
            }
        }
    }

    public boolean collidesWith(Player player) {
        Rectangle2D ghostBounds = new Rectangle2D(x, y, 30, 30);
        Rectangle2D playerBounds = new Rectangle2D(player.getX(), player.getY(), 30, 30);
        return ghostBounds.intersects(playerBounds);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x, y, 30, 30);
    }
}