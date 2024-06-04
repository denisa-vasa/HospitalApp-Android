package com.example.hospitalapp.dto;

public class FilterDto {

    private String name;

    public FilterDto(){

    }

    public FilterDto(String name) {
        this.name = getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

