package com.keyin.model;

public class MedicineReminder {
    private int id;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private String startDate;
    private String endDate;

    // Constructor, getters, and setters

    public MedicineReminder(int id, int userId, String medicineName, String dosage, String schedule, String startDate, String endDate) {
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "MedicineReminder{" +
                "id=" + id +
                ", userId=" + userId +
                ", medicineName='" + medicineName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", schedule='" + schedule + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
