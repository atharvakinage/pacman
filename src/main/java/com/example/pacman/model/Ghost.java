package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import java.util.List;
import java.util.Random;

public class Ghost extends GameEntity {
    private final String colorName;           // Name of the ghost's color
    private int dx, dy;                       // Movement deltas
    private final Random random = new Random();
    private boolean isChasing = false;        // Is the ghost chasing the player?
    private Player targetPlayer;              // The player being targeted
    private boolean isFrightened = false;     // Is the ghost frightened?
    private Image ghostImage;                 // Normal ghost image
    private Image frightenedImage;            // Frightened ghost image
    private final double startX, startY;      // Initial starting position

    public Ghost(double x, double y, String colorName) {
        super(x, y); // Call parent constructor to set initial position
        this.startX = x;
        this.startY = y;
        this.colorName = colorName;
        loadImage(); // Load ghost images
        setRandomDirection(); // Give the ghost a random initial direction
    }

    // Load ghost images
    private void loadImage() {
        String imagePath = "/ghost_" + colorName.toLowerCase() + ".gif";
        ghostImage = new Image(getClass().getResourceAsStream(imagePath));
        frightenedImage = new Image(getClass().getResourceAsStream("/frightenedghost.gif"));
    }

    public String getColorName() {
        return colorName;
    }

    public void setChasing(Player player) {
        this.isChasing = true;
        this.targetPlayer = player;
    }

    public void setFrightened(boolean frightened) {
        this.isFrightened = frightened;
    }

    private void setRandomDirection() {
        int dir = random.nextInt(4); // Randomly pick a direction
        switch (dir) {
            case 0 -> { dx = 2; dy = 0; }   // Right
            case 1 -> { dx = -2; dy = 0; }  // Left
            case 2 -> { dx = 0; dy = 2; }   // Down
            case 3 -> { dx = 0; dy = -2; }  // Up
        }
    }

    private void setChaseDirection() {
        if (targetPlayer == null) return; // No target player to chase

        double px = targetPlayer.getX();
        double py = targetPlayer.getY();

        dx = 0;
        dy = 0;

        // Prioritize movement direction based on distance from the player
        if (Math.abs(px - getX()) > Math.abs(py - getY())) {
            dx = (px > getX()) ? 2 : -2;
        } else {
            dy = (py > getY()) ? 2 : -2;
        }
    }

    public void move(List<Wall> walls) {
        double oldX = getX();
        double oldY = getY();

        if (isChasing && !isFrightened) {
            setChaseDirection(); // Adjust direction based on the player's position
        }

        setX(getX() + dx);
        setY(getY() + dy);

        // Check for wall collisions and handle appropriately
        Rectangle2D bounds = new Rectangle2D(getX(), getY(), 30, 30);
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(bounds)) {
                // Revert to previous position
                setX(oldX);
                setY(oldY);
                // If chasing and not frightened, stop moving; otherwise, pick a new random direction
                if (isChasing && !isFrightened) {
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
        // Check collision with the player
        Rectangle2D ghostBounds = new Rectangle2D(getX(), getY(), 30, 30);
        Rectangle2D playerBounds = new Rectangle2D(player.getX(), player.getY(), 30, 30);
        return ghostBounds.intersects(playerBounds);
    }

    public void draw(GraphicsContext gc) {
        // Draw ghost image based on its state (frightened or normal)
        Image imgToDraw = isFrightened ? frightenedImage : ghostImage;
        gc.drawImage(imgToDraw, getX(), getY(), 30, 30);
    }

    public void resetPosition() {
        // Reset ghost to its initial position
        setX(startX);
        setY(startY);
    }

    public boolean isFrightened() {
        return isFrightened;
    }

    @Override
    public Rectangle2D getBounds() {
        // Return the bounding rectangle of the ghost (used for collision detection)
        return new Rectangle2D(getX(), getY(), 30, 30);
    }
}