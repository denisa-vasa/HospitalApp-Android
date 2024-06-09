package com.example.hospitalapp.model;

import java.time.LocalDateTime;

public class AdmissionState {
    private Long id;
    private LocalDateTime enteringDate;
    private LocalDateTime exitingDate;
    private String cause;
    private String reason;
    private boolean discharge = false;

    public AdmissionState() {
    }

    public AdmissionState(Long id, LocalDateTime enteringDate, LocalDateTime exitingDate,
                          String cause, String reason, boolean discharge) {
        this.id = id;
        this.enteringDate = enteringDate;
        this.exitingDate = exitingDate;
        this.cause = cause;
        this.reason = reason;
        this.discharge = discharge;
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
}
