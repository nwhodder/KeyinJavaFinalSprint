package com.keyin.model;

import com.keyin.client.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao();
    }

    public Doctor getDoctorById(int doctorId) {
        int id = 0;
        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;
        boolean is_doctor = false;
        String specialization = null;
        String medicalLicense = null;
        String query = "SELECT * FROM users LEFT JOIN doctor_specialization " +
                "ON users.id = doctor_specialization.doctor_id " +
                "WHERE id = ? AND is_doctor = true";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                email = rs.getString("email");
                password = rs.getString("password");
                is_doctor = rs.getBoolean("is_doctor");
                specialization = rs.getString("specialization");
                int medicalLicenseInt = rs.getInt("medical_license");
                medicalLicense = String.valueOf(medicalLicenseInt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Doctor(id, firstName, lastName,email, password, is_doctor, medicalLicense, specialization);
    }

    public List<User> getPatientsByDoctorId(int doctorId) {
        List<User> patientList = new ArrayList<>();
        String query = "SELECT * FROM doctor_patient LEFT JOIN users " +
                        "ON doctor_patient.patient_id = users.id " +
                        "WHERE doctor_id = ?";

        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean isDoctor = rs.getBoolean("is_doctor");
                User user = new User(id, firstName, lastName, email, password, isDoctor);
                patientList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }

    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }

    public List<HealthData> getHealthDataByPatientName(String patientFirstName, String patientLastName) {
        List<HealthData> healthDataList = new ArrayList<>();
        String query = "SELECT * FROM health_data LEFT JOIN users " +
                "ON health_data.user_id = users.id " +
                "WHERE users.first_name = ? AND users.last_name = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, patientFirstName);
            statement.setString(2, patientLastName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                double weight = rs.getDouble("weight");
                double height = rs.getDouble("height");
                int steps = rs.getInt("steps");
                int heartRate = rs.getInt("heart_rate");
                Date dateObj = rs.getDate("date");
                String date = dateObj.toString();

                HealthData healthData = new HealthData(id, userId, weight, height, steps, heartRate, date);
                healthDataList.add(healthData);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return healthDataList;
    }
}

