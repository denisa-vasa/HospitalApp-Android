package com.example.hospitalapp.dto;

public class DischargeReasonDto {
    private Long id;
    private String reason;

    public DischargeReasonDto() {
    }

    public DischargeReasonDto(Long id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
