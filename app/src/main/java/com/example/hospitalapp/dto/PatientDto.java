package com.example.hospitalapp.dto;

import com.example.hospitalapp.adapter.LocalDateAdapter;
import com.example.hospitalapp.model.Patient;
import com.google.gson.annotations.JsonAdapter;

import org.threeten.bp.LocalDate;

public class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    public PatientDto(Patient p) {
        id = p.getId();
        firstName = p.getFirstName();
        lastName = p.getLastName();
        birthDate = p.getBirthDate();
    }

    public PatientDto(String patientFirstName, String patientLastName) {
        firstName = patientFirstName;
        lastName = patientLastName;
    }

    public PatientDto() {

    }

    public PatientDto(String name, String lastName, LocalDate birthDate) {
        firstName = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public PatientDto(String patientName) {
        // Split the patientName into first and last names
        String[] nameParts = patientName.split("\\s+", 2); // Split into two parts at most
        if (nameParts.length > 0) {
            this.firstName = nameParts[0]; // First part is first name
            if (nameParts.length > 1) {
                this.lastName = nameParts[1]; // Second part is last name
            }
        }
    }

    public PatientDto(Long patientId, String patientName) {
        id = patientId;
        String[] nameParts = patientName.split("\\s+", 2); // Split into two parts at most
        if (nameParts.length > 0) {
            this.firstName = nameParts[0]; // First part is first name
            if (nameParts.length > 1) {
                this.lastName = nameParts[1]; // Second part is last name
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
