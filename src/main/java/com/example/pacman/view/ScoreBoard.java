package com.example.pacman.view;

import com.example.pacman.db.ScoreDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScoreBoard {
    private Stage stage;
    private Label scoreLabel;
    private Label powerModeLabel;
    private TableView<LeaderboardRow> table;

    public ScoreBoard() {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Consolas", 28));
        scoreLabel.setTextFill(Color.ORANGE);
        scoreLabel.setEffect(new DropShadow(5, Color.DARKORANGE));

        powerModeLabel = new Label("Power Mode: OFF");
        powerModeLabel.setFont(Font.font("Consolas", 24));
        powerModeLabel.setTextFill(Color.CYAN);
        powerModeLabel.setEffect(new DropShadow(4, Color.CYAN));

        table = new TableView<>();
        setupLeaderboardTable();

        VBox root = new VBox(15, scoreLabel, powerModeLabel, table);
        root.setStyle("""
            -fx-background-color: linear-gradient(to bottom, #000000, #1a1a1a);
            -fx-padding: 20px;
        """);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 360, 500);
        stage = new Stage();
        stage.setTitle("🟡 Scoreboard");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setX(1100);
        stage.setY(100);
        stage.show();
        loadLeaderboard();
    }

    private void setupLeaderboardTable() {
        TableColumn<LeaderboardRow, String> nameCol = new TableColumn<>("Player");
        nameCol.setCellValueFactory(data -> data.getValue().playerNameProperty());
        nameCol.setMinWidth(120);

        TableColumn<LeaderboardRow, String> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(data -> data.getValue().scoreProperty());
        scoreCol.setMinWidth(80);

        TableColumn<LeaderboardRow, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(data -> data.getValue().timestampProperty());
        timeCol.setMinWidth(140);

        table.getColumns().addAll(nameCol, scoreCol, timeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No scores yet"));
    }

    private void loadLeaderboard() {
        ObservableList<LeaderboardRow> rows = FXCollections.observableArrayList();
        ScoreDAO dao = ScoreDAO.getInstance();
        dao.getTopScores(10).forEach(entry -> {
            String[] parts = entry.split(" - ");
            if (parts.length == 2) {
                String name = parts[0];
                String[] scoreAndTime = parts[1].split(" \\(");
                if (scoreAndTime.length == 2) {
                    String score = scoreAndTime[0];
                    String time = scoreAndTime[1].replace(")", "");
                    rows.add(new LeaderboardRow(name, score, time));
                }
            }
        });
        table.setItems(rows);
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