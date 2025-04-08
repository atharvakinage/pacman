package com.example.pacman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/game-view.fxml"));
            stage.setTitle("Pac-Man Game");
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // <-- Shows the real issue
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}