package com.keyin.service;

import com.keyin.client.DatabaseConnection;
import com.keyin.model.HealthData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;

    public static List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

        // Analyze heart rate
        int heartRate = healthData.getHeartRate();
        if (heartRate < MIN_HEART_RATE) {
            recommendations.add("Your resting heart rate is lower than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        } else if (heartRate > MAX_HEART_RATE) {
            recommendations.add("Your resting heart rate is higher than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        }

        // Analyze steps
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
            recommendations.add("You're not reaching the recommended daily step count. " +
                    "Try to incorporate more walking or other physical activities into your daily routine.");
        }
        return recommendations;
    }

    public static boolean createRecommendation(HealthData healthData) {
        boolean bool = false;
        List<String> recommendations = generateRecommendations(healthData);
        String query = "INSERT INTO recommendations (user_id, recommendation_text, date) " +
                        "VALUES (?, ?, ?)";
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, healthData.getUserId());
            StringBuilder sb = new StringBuilder();
            for (String recommendation : recommendations) {
                sb.append(recommendation);
                sb.append(" ");
            }
            sb.setLength(sb.length() - 1);
            statement.setString(2, sb.toString());
            Date date = Date.valueOf(LocalDate.now());
            statement.setDate(3, date);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
