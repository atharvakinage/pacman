package com.example.pacman.controller;

import com.example.pacman.model.GameModel;
import com.example.pacman.util.GameLoop;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameModel model;
    private GameLoop loop;

    @FXML
    public void initialize() {
        System.out.println("Game controller initialized: Canvas =" + gameCanvas);
        model = GameModel.getInstance();
        loop = new GameLoop(gameCanvas.getGraphicsContext2D(), model);
        loop.start();
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        model.getPlayer().handleKey(event.getCode());
    }
}