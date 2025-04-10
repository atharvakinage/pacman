package com.example.pacman.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pacman_db";
    private static final String USER = "root";
    private static final String PASS = "password";

    public void insertScore(String playerName, int score) {
        String sql = "INSERT INTO scores (player_name, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTopScores(int limit) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT player_name, score, timestamp FROM scores ORDER BY score DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("player_name");
                int score = rs.getInt("score");
                String time = rs.getString("timestamp");
                results.add(name + " - " + score + " (" + time + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
