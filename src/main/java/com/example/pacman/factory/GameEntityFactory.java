package com.example.pacman.factory;

import com.example.pacman.model.GameEntity;
import com.example.pacman.model.Player;

public class GameEntityFactory {

    public static GameEntity createEntity(String type) {
        EntityFactory factory;

        if (type.equalsIgnoreCase("pacman")) {
            factory = new PlayerFactory();
        } else {
            factory = new GhostFactory();
        }

        return factory.createEntity(type);
    }
}