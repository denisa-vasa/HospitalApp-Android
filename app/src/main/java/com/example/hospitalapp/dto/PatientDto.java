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
