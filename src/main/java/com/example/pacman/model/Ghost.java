package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import java.util.List;
import java.util.Random;

public class Ghost extends GameEntity {
    private final String colorName;          
    private int dx, dy;                      
    private final Random random = new Random();
    private boolean isChasing = false;       
    private Player targetPlayer;            
    private boolean isFrightened = false;     
    private Image ghostImage;           
    private Image frightenedImage;       
    private final double startX, startY;   

    public Ghost(double x, double y, String colorName) {
        super(x, y); 
        this.startX = x;
        this.startY = y;
        this.colorName = colorName;
        loadImage(); 
        setRandomDirection(); 

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
        int dir = random.nextInt(4); 
        switch (dir) {
            case 0 -> { dx = 2; dy = 0; }  
            case 1 -> { dx = -2; dy = 0; } 
            case 2 -> { dx = 0; dy = 2; } 
            case 3 -> { dx = 0; dy = -2; } 
        }
    }

    private void setChaseDirection() {
        if (targetPlayer == null) return; 

        double px = targetPlayer.getX();
        double py = targetPlayer.getY();

        dx = 0;
        dy = 0;

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
            setChaseDirection(); 
        }

        setX(getX() + dx);
        setY(getY() + dy);

        Rectangle2D bounds = new Rectangle2D(getX(), getY(), 30, 30);
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(bounds)) {
                setX(oldX);
                setY(oldY);
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
        Rectangle2D ghostBounds = new Rectangle2D(getX(), getY(), 30, 30);
        Rectangle2D playerBounds = new Rectangle2D(player.getX(), player.getY(), 30, 30);
        return ghostBounds.intersects(playerBounds);
    }

    public void draw(GraphicsContext gc) {
        Image imgToDraw = isFrightened ? frightenedImage : ghostImage;
        gc.drawImage(imgToDraw, getX(), getY(), 30, 30);
    }

    public void resetPosition() {
        setX(startX);
        setY(startY);
    }

    public boolean isFrightened() {
        return isFrightened;
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(getX(), getY(), 30, 30);
    }
}