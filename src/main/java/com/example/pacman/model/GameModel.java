package com.example.pacman.model;

import com.example.pacman.db.ScoreDAO;
import com.example.pacman.factory.EntityFactory;
import com.example.pacman.factory.GhostFactory;
import com.example.pacman.factory.PlayerFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import com.example.pacman.observer.CollisionObserver;
import com.example.pacman.observer.CollisionLogger;

public class GameModel {
    private static GameModel instance;
    private Player player;
    private List<Wall> walls;
    private List<Ghost> ghosts;
    private List<Pellet> pellets;
    private final List<CollisionObserver> observers = new ArrayList<>();
    private boolean powerModeActive = false;
    private long powerModeStartTime;
    private static final int POWER_MODE_DURATION = 7000; // milliseconds
    private Image frightenedGhostImage;
    private String playerName = "Unknown";

    // Factories
    private final EntityFactory playerFactory = new PlayerFactory();
    private final EntityFactory ghostFactory = new GhostFactory();

    private GameModel() {
        // Create the player using PlayerFactory
        player = (Player) playerFactory.createEntity("pacman");

        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        pellets = new ArrayList<>();
        frightenedGhostImage = new Image(getClass().getResourceAsStream("/frightenedghost.gif"));

        generateMaze();
        generateGhosts();
        generatePellets();

        registerObserver(new CollisionLogger());
    }

    public static GameModel getInstance() {
        if (instance == null) instance = new GameModel();
        return instance;
    }

    public static void resetInstance() {
        instance = new GameModel();
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

    public List<Pellet> getPellets() {
        return pellets;
    }

    public Image getFrightenedGhostImage() {
        return frightenedGhostImage;
    }

    public void registerObserver(CollisionObserver observer) {
        observers.add(observer);
    }

    private void notifyCollisionObservers(String message) {
        for (CollisionObserver observer : observers) {
            observer.onGhostCollision(message);
        }
    }

    private void generateMaze() {
        walls.add(new Wall(0, 0, 600, 30));
        walls.add(new Wall(0, 570, 600, 30));
        walls.add(new Wall(0, 30, 30, 540));
        walls.add(new Wall(570, 30, 30, 540));

        // Top section - simplified
        walls.add(new Wall(90, 90, 90, 60));
        walls.add(new Wall(420, 90, 90, 60));
        walls.add(new Wall(255, 90, 90, 60));

        // Middle section
        walls.add(new Wall(90, 210, 60, 120));
        walls.add(new Wall(450, 210, 60, 120));
        walls.add(new Wall(210, 210, 60, 30));
        walls.add(new Wall(330, 210, 60, 30));
        walls.add(new Wall(210, 240, 30, 90));
        walls.add(new Wall(360, 240, 30, 90));
        walls.add(new Wall(210, 330, 60, 30));
        walls.add(new Wall(330, 330, 60, 30));

        // Bottom section
        walls.add(new Wall(90, 450, 90, 60));
        walls.add(new Wall(420, 450, 90, 60));
        walls.add(new Wall(255, 450, 90, 60));
        walls.add(new Wall(180, 390, 90, 30));
        walls.add(new Wall(330, 390, 90, 30));
    }

    private void generateGhosts() {
        // Create ghosts using GhostFactory
        ghosts.add((Ghost) ghostFactory.createEntity("red"));
        ghosts.add((Ghost) ghostFactory.createEntity("pink"));
        ghosts.add((Ghost) ghostFactory.createEntity("cyan"));
        ghosts.add((Ghost) ghostFactory.createEntity("yellow"));
    }

    private void generatePellets() {
        for (int i = 30; i < 570; i += 30) {
            for (int j = 30; j < 570; j += 30) {
                Rectangle2D pelletBounds = new Rectangle2D(i, j, 30, 30);
                boolean isInsideWall = walls.stream().anyMatch(w -> w.getBounds().intersects(pelletBounds));
                if (!isInsideWall) {
                    boolean isPower = (i % 120 == 0 && j % 120 == 0);
                    pellets.add(new Pellet(i, j, isPower));
                }
            }
        }
    }

    public void activatePowerMode() {
        powerModeActive = true;
        powerModeStartTime = System.currentTimeMillis();
        for (Ghost ghost : ghosts) {
            ghost.setFrightened(true);
        }
    }

    private void checkPowerModeTimeout() {
        if (powerModeActive && System.currentTimeMillis() - powerModeStartTime > POWER_MODE_DURATION) {
            powerModeActive = false;
            for (Ghost ghost : ghosts) {
                ghost.setFrightened(false);
            }
        }
    }

    public boolean isPowerModeActive() {
        return powerModeActive;
    }

    public void draw(GraphicsContext gc) {
        checkPowerModeTimeout();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 600);

        for (Wall wall : walls) {
            wall.draw(gc);
        }

        for (Pellet pellet : pellets) {
            pellet.draw(gc);
        }

        List<Pellet> toRemove = new ArrayList<>();
        for (Pellet pellet : pellets) {
            if (player.collidesWith(pellet)) {
                if (pellet.isPowerPellet()) {
                    activatePowerMode();
                }
                player.addScore(10);
                toRemove.add(pellet);
            }
        }
        pellets.removeAll(toRemove);

        for (Ghost ghost : ghosts) {
            ghost.move(walls);
            ghost.draw(gc);

            if (ghost.collidesWith(player)) {
                if (ghost.isFrightened()) {
                    player.addScore(200);
                    ghost.setFrightened(false);
                    ghost.resetPosition();
                    notifyCollisionObservers("Pac-Man ate a frightened ghost at (" + ghost.getX() + ", " + ghost.getY() + ")");
                } else {
                    notifyCollisionObservers("Pac-Man collided with a ghost at (" + ghost.getX() + ", " + ghost.getY() + ")");
                }
            }
        }

        player.draw(gc);
    }

    public void copyFrom(GameModel other) {
        this.player = other.player;
        this.walls = new ArrayList<>(other.walls);
        this.ghosts = new ArrayList<>(other.ghosts);
        this.pellets = new ArrayList<>(other.pellets);
        this.powerModeActive = other.powerModeActive;
        this.powerModeStartTime = other.powerModeStartTime;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }
}