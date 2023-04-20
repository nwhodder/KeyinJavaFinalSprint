package com.keyin.service;

import com.keyin.client.DatabaseConnection;
import com.keyin.model.MedicineReminder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class MedicineReminderManager {
    private List<MedicineReminder> reminders;

    public MedicineReminderManager() {
        this.reminders = new ArrayList<>();
    }

    public void addReminder(MedicineReminder reminder) {
        reminders.add(reminder);
        String query = "INSERT INTO medicine_reminders " +
                "(user_id, medicine_name, dosage, schedule, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, reminder.getUserId());
            statement.setString(2, reminder.getMedicineName());
            statement.setString(3, reminder.getDosage());
            statement.setString(4, reminder.getSchedule());
            Date startDate = Date.valueOf(reminder.getStartDate());
            statement.setDate(5, startDate);
            Date endDate = Date.valueOf(reminder.getEndDate());
            statement.setDate(6, endDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String medicine = rs.getString("medicine_name");
                String dosage = rs.getString("dosage");
                String schedule = rs.getString("schedule");
                Date startDateObj = rs.getDate("start_date");
                String startDate = startDateObj.toString();
                Date endDateObj = rs.getDate("end_date");
                String endDate = endDateObj.toString();
                userReminders.add(new MedicineReminder(id, userId, medicine, dosage, schedule, startDate, endDate));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userReminders;
    }

    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ? AND end_date > CURRENT_DATE";

        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String medicine = rs.getString("medicine_name");
                String dosage = rs.getString("dosage");
                String schedule = rs.getString("schedule");
                LocalTime scheduleLocalTime = LocalTime.parse(schedule, formatter);
                Date startDateObj = rs.getDate("start_date");
                String startDate = startDateObj.toString();
                Date endDateObj = rs.getDate("end_date");
                String endDate = endDateObj.toString();
                LocalDateTime parsedSchedule = LocalDateTime.of(LocalDate.now(), scheduleLocalTime);
                if (parsedSchedule.isEqual(now) || parsedSchedule.isBefore(now.plusHours(3))){
                    dueReminders.add(new MedicineReminder(id, userId, medicine, dosage, schedule, startDate, endDate));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return dueReminders;
    }
}
