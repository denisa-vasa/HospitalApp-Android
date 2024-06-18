package com.example.hospitalapp.dto;

import org.threeten.bp.LocalDateTime;

public class AdmissionStateDto {
    private Long id;
    private LocalDateTime enteringDate;
    private LocalDateTime exitingDate;
    private String cause;
    private String reason;
    private boolean discharge = false;
    private Long departmentId;
    private Long patientId;

    public AdmissionStateDto() {
    }

    public AdmissionStateDto(Long id, LocalDateTime enteringDate, LocalDateTime exitingDate,
                             String cause, String reason, boolean discharge, Long departmentId,
                             Long patientId) {
        this.id = id;
        this.enteringDate = enteringDate;
        this.exitingDate = exitingDate;
        this.cause = cause;
        this.reason = reason;
        this.discharge = discharge;
        this.departmentId = departmentId;
        this.patientId = patientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEnteringDate() {
        return enteringDate;
    }

    public void setEnteringDate(LocalDateTime enteringDate) {
        this.enteringDate = enteringDate;
    }

    public LocalDateTime getExitingDate() {
        return exitingDate;
    }

    public void setExitingDate(LocalDateTime exitingDate) {
        this.exitingDate = exitingDate;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isDischarge() {
        return discharge;
    }

    public void setDischarge(boolean discharge) {
        this.discharge = discharge;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
