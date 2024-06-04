package com.example.hospitalapp.dto;

import com.example.hospitalmanagementsystem.model.Department;

public class DepartmentDto {
    private Long id;
    private String name;
    private String code;

    public DepartmentDto() {
    }

    public DepartmentDto(String name, String code, Long id) {
        this.name = name;
        this.code = code;
        this.id = id;
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
}
