package com.example.pacman.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScoreBoard {
    private Stage stage;
    private Label scoreLabel;
    private Label powerModeLabel;

    public ScoreBoard() {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Consolas", 28));
        scoreLabel.setTextFill(Color.ORANGE);
        scoreLabel.setEffect(new DropShadow(5, Color.DARKORANGE));

        powerModeLabel = new Label("Power Mode: OFF");
        powerModeLabel.setFont(Font.font("Consolas", 24));
        powerModeLabel.setTextFill(Color.CYAN);
        powerModeLabel.setEffect(new DropShadow(4, Color.CYAN));

        VBox root = new VBox(15, scoreLabel, powerModeLabel);
        root.setStyle("""
            -fx-background-color: linear-gradient(to bottom, #000000, #1a1a1a);
            -fx-padding: 25px;
            -fx-alignment: center;
        """);

        Scene scene = new Scene(root, 240, 130);

        stage = new Stage();
        stage.setTitle("🟡 Scoreboard");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setX(1100);
        stage.setY(100);
        stage.show();
    }

    public void updateScore(int score) {
        Platform.runLater(() -> scoreLabel.setText("Score: " + score));
    }

    public void updatePowerMode(boolean powerMode) {
        Platform.runLater(() ->
                powerModeLabel.setText("Power Mode: " + (powerMode ? "ON" : "OFF"))
        );
    }

    public void close() {
        Platform.runLater(stage::close);
    }
}
