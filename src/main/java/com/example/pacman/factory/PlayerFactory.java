package com.example.pacman.factory;

import com.example.pacman.model.GameEntity;
import com.example.pacman.model.Player;

public class PlayerFactory implements EntityFactory {
    @Override
    public GameEntity createEntity(String type) {
        // For simplicity, ignore `type` for now (needed for interface compatibility).
        return new Player(315, 270);
    }

}
