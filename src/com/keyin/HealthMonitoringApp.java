package com.keyin;

import com.keyin.client.DatabaseConnection;
import com.keyin.model.*;
import com.keyin.service.MedicineReminderManager;
import com.keyin.service.RecommendationSystem;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HealthMonitoringApp {

    public static void main(String[] args) throws SQLException {
        // test register a new user
        User kennedy = new User(1, "Kennedy", "Azupwah", "kazu@example.com", "examplePass", false);
        UserDao.createUser(kennedy);

        // test Login user (call testLoginUser() here)
        testLoginUser();

        // Add health data
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int kennedyUserId = UserDao.getUserByEmail("kazu@example.com").getId();
        HealthData kennedyHealth = new HealthData(1, kennedyUserId, 160.00, 190, 8000, 43, currentDate);
        HealthDataDao.createHealthData(kennedyHealth);

        // Generate recommendations
        RecommendationSystem.generateRecommendations(kennedyHealth);
        RecommendationSystem.createRecommendation(kennedyHealth);

        // Add a medicine reminder
        MedicineReminder kennedyMedicine = new MedicineReminder(
                1, kennedyHealth.getUserId(), "Flintstone Gummies", "3 per day",
                LocalDateTime.now().minusHours(1).getHour() + ":00",
                // set schedule time to about 1 hour before current time
                "2020-04-10", "2112-12-21");
        MedicineReminderManager mrm = new MedicineReminderManager();
        mrm.addReminder(kennedyMedicine);

        // Get reminders for a specific user
        System.out.println(mrm.getRemindersForUser(kennedyUserId));

        // Get due reminders for a specific user
        // will only return reminders within 3 hours of their scheduled time & not past end date
        System.out.println(mrm.getDueReminders(kennedyUserId));

        //test doctor portal (call testDoctorPortal() here)\
        testDoctorPortal();
    }


    public static boolean loginUser(String email, String password) throws SQLException {
        //implement method to login user.
        boolean bool = false;
        User user = UserDao.getUserByEmail(email);

        if ((user.getEmail() != null) && (user.getPassword() != null)) {
            bool = UserDao.verifyPassword(user.getEmail(), password);
        }

        return bool;

    }

    public static void testDoctorPortal() {
        // Replace the doctorId with a valid ID from your database
        int doctorId = 1;
        DoctorPortalDao dpd = new DoctorPortalDao();
        // Add code to Fetch the doctor by ID
        System.out.println("Doctor by ID: " + dpd.getDoctorById(doctorId));
        // Add code to Fetch patients associated with the doctor
        System.out.println("Patients by Doctor ID: " + dpd.getPatientsByDoctorId(doctorId));
        // Add code to Fetch health data for the patient
        System.out.println("Health Data by Patient Name: " + dpd.getHealthDataByPatientName("Nick", "Hodder"));
    }

    public static void testLoginUser() throws SQLException {
        // Replace the email and password with valid credentials from your database
        String userEmail = "kazu@example.com";
        String userPassword = "examplePass";

        boolean loginSuccess = loginUser(userEmail, userPassword);

        if (loginSuccess) {
            System.out.println("Test Login: Login Successful");
        } else {
            System.out.println("Test Login: Login Failed");
        }
    }

}
