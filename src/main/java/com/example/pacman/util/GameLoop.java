package com.example.pacman.util;

import com.example.pacman.model.GameModel;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameLoop extends AnimationTimer {
    private final GraphicsContext gc;
    private final GameModel model;

    public GameLoop(GraphicsContext gc, GameModel model) {
        this.gc = gc;
        this.model = model;
    }

    @Override
    public void handle(long now) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 600);
        gc.clearRect(0, 0, 600, 600);
        model.draw(gc);
        model.getPlayer().draw(gc);
        model.getGhosts().forEach(ghost -> ghost.draw(gc));
        model.getWalls().forEach(wall -> wall.draw(gc));
    }
}