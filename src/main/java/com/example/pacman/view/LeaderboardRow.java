package com.example.pacman.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeaderboardRow {
    private final StringProperty playerName;
    private final StringProperty score;
    private final StringProperty timestamp;

    public LeaderboardRow(String name, String score, String time) {
        this.playerName = new SimpleStringProperty(name);
        this.score = new SimpleStringProperty(score);
        this.timestamp = new SimpleStringProperty(time);
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public StringProperty scoreProperty() {
        return score;
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }
}
