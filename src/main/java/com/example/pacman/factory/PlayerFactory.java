package com.example.pacman.factory;

import com.example.pacman.model.GameEntity;
import com.example.pacman.model.Player;

public class PlayerFactory implements EntityFactory {
    @Override
    public GameEntity createEntity(String type) {
        return new Player(315, 270);
    }

}
