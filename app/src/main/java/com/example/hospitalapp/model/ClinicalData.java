package com.example.hospitalapp.model;

public class ClinicalData {
    private Long id;
    private String clinicalRecord;

    public ClinicalData() {
    }

    public ClinicalData(Long id, String clinicalRecord) {
        this.id = id;
        this.clinicalRecord = clinicalRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClinicalRecord() {
        return clinicalRecord;
    }

    public void setClinicalRecord(String clinicalRecord) {
        this.clinicalRecord = clinicalRecord;
    }
}
