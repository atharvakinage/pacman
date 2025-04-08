package com.example.pacman.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import com.example.pacman.observer.CollisionObserver;
import com.example.pacman.observer.CollisionLogger;

public class GameModel {
    private static GameModel instance;
    private final Player player;
    private final List<Wall> walls;
    private final List<Ghost> ghosts;
    private final List<CollisionObserver> observers = new ArrayList<>();

    private GameModel() {
        player = new Player(270, 270);
        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        generateMaze();
        generateGhosts();
        registerObserver(new CollisionLogger());
    }

    public static GameModel getInstance() {
        if (instance == null) instance = new GameModel();
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public void registerObserver(CollisionObserver observer) {
        observers.add(observer);
    }

    private void notifyCollisionObservers(String message) {
        for(CollisionObserver observer : observers) {
            observer.onGhostCollision(message);
        }
    }

    private void generateMaze() {
        for (int i = 0; i < 600; i += 30) {
            walls.add(new Wall(i, 0, 30, 30));
            walls.add(new Wall(i, 570, 30, 30));
            walls.add(new Wall(0, i, 30, 30));
            walls.add(new Wall(570, i, 30, 30));
        }
        // Add some internal walls
        walls.add(new Wall(120, 120, 30, 150));
        walls.add(new Wall(300, 300, 90, 30));
        walls.add(new Wall(450, 150, 30, 120));
    }

    private void generateGhosts() {
        ghosts.add(new Ghost(150, 150, Color.RED));
        ghosts.add(new Ghost(400, 400, Color.PINK));
        ghosts.add(new Ghost(300, 150, Color.CYAN));
        ghosts.add(new Ghost(150, 400, Color.ORANGE));
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 600);

        for (Wall wall : walls) {
            wall.draw(gc);
        }
        for (Ghost ghost : ghosts) {
            ghost.move(walls);
            ghost.draw(gc);
            if(ghost.collidesWith(player)) {
                notifyCollisionObservers("Pac-Man collided with a ghost at (" + ghost.getX() + ", " + ghost.getY() + ")");
            }
        }
        player.draw(gc);
    }
}