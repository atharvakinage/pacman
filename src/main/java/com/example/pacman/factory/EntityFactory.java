package com.example.pacman.factory;

import com.example.pacman.model.GameEntity;
import com.example.pacman.model.Player;

public interface EntityFactory {
    GameEntity createEntity(String type);
}
