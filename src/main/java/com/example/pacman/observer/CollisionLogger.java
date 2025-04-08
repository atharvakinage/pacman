package com.example.pacman.observer;

public class CollisionLogger implements CollisionObserver {
    @Override
    public void onGhostCollision(String message) {
        System.out.println("Collision Detected: " + message);
    }
}