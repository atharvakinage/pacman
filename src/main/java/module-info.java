module com.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pacman.controller to javafx.fxml;
    exports com.example.pacman;
}