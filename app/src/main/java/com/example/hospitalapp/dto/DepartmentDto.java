package com.example.hospitalapp.dto;

import com.example.hospitalmanagementsystem.model.Department;

public class DepartmentDto {
    private Long id;
    private String name;
    private String code;
    private Long admissionStateId;
    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name, String code, Long admissionStateId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.admissionStateId = admissionStateId;
    }

    public DepartmentDto(Department d) {
        name = d.getName();
        code = d.getCode();
    }

    public DepartmentDto(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmissionStateId() {
        return admissionStateId;
    }

    public void setAdmissionStateId(Long admissionStateId) {
        this.admissionStateId = admissionStateId;
    }
}
