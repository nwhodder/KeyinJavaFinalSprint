package com.keyin.model;

public class Doctor extends User{
    private String medicalLicenseNumber;
    private String specialization;

    public Doctor(int id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        super(id, firstName, lastName, email, password, isDoctor);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
    }

    // Getters and setters for the new properties

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", isDoctor=" + this.isDoctor() + '\'' +
                ", medicalLicenseNumber='" + medicalLicenseNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}

