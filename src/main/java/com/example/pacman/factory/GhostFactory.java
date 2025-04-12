package com.example.pacman.factory;

import com.example.pacman.model.GameEntity;
import com.example.pacman.model.Ghost;
import com.example.pacman.model.Player;

public class GhostFactory implements EntityFactory {
    @Override
    public GameEntity createEntity(String type) {
        return switch (type.toLowerCase()) {
            case "red" -> new Ghost(285, 180, "red");
            case "pink" -> new Ghost(285, 180, "pink");
            case "cyan" -> new Ghost(285, 180, "cyan");
            case "yellow" -> new Ghost(285, 180, "yellow");
            default -> throw new IllegalArgumentException("Unknown ghost type: " + type);
        };
    }

}
