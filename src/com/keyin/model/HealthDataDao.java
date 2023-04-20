package com.keyin.model;

import com.keyin.client.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDao {
    public static boolean createHealthData(HealthData healthData) { /* insert health data into database */
        boolean bool = false;
        String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, healthData.getUserId());
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            Date date = Date.valueOf(healthData.getDate());
            statement.setDate(6, date);
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public HealthData getHealthDataById(int id) { /* get health data by id from database */
        int user_id = 0;
        double weight = 0;
        double height = 0;
        int steps = 0;
        int heartRate = 0;
        String date = null;

        String query = "SELECT * FROM health_data WHERE id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
                weight = rs.getDouble("weight");
                height = rs.getDouble("height");
                steps = rs.getInt("steps");
                heartRate = rs.getInt("heart_rate");
                Date dateObj = rs.getDate("date");
                date = dateObj.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HealthData(id, user_id, weight, height, steps, heartRate, date);
    }

    public List<HealthData> getHealthDataByUserId(int userId) { /* get health data by user id from database */
        List<HealthData> healthDataList = new ArrayList<>();
        String query = "SELECT * FROM health_data WHERE user_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double weight = rs.getDouble("weight");
                double height = rs.getDouble("height");
                int steps = rs.getInt("steps");
                int hearRate = rs.getInt("heart_rate");
                Date dateObj = rs.getDate("date");
                String date = dateObj.toString();
                HealthData healthData = new HealthData(id, userId, weight, height, steps, hearRate, date);
                healthDataList.add(healthData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return healthDataList;
    }

    public boolean updateHealthData(HealthData healthData) { /* update health data in the database */
        boolean bool = false;
        String query = "UPDATE health_data " +
                        "SET user_id = ?, weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? " +
                        "WHERE id = ?";
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, healthData.getUserId());
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            Date date = Date.valueOf(healthData.getDate());
            statement.setDate(6, date);
            statement.setInt(7, healthData.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0) {
                bool = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public boolean deleteHealthData(int id) { /* delete health data from the database */
        boolean bool = false;
        String query = "DELETE FROM health_data WHERE id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0){
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
