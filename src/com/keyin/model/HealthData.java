package com.keyin.model;

public class HealthData {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private String date;

    // Constructor, getters, and setters

    public HealthData(int id, int userId, double weight, double height, int steps, int heartRate, String date) {
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getSteps() {
        return steps;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "HealthData{" +
                "id=" + id +
                ", userId=" + userId +
                ", weight=" + weight +
                ", height=" + height +
                ", steps=" + steps +
                ", heartRate=" + heartRate +
                ", date='" + date + '\'' +
                '}';
    }
}
