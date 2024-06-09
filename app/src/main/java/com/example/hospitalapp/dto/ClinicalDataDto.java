package com.example.hospitalapp.dto;

import com.example.hospitalapp.model.ClinicalData;

public class ClinicalDataDto {
    private Long id;
    private String clinicalRecord;
    private Long patientId;
    private Long departmentId;
    private Long admissionStateId;

    public ClinicalDataDto(ClinicalData c) {
        id = c.getId();
        clinicalRecord = c.getClinicalRecord();
    }

    public ClinicalDataDto() {
    }

    public ClinicalDataDto(Long id, String clinicalRecord, Long patientId, Long departmentId,
                           Long admissionStateId) {
        this.id = id;
        this.clinicalRecord = clinicalRecord;
        this.patientId = patientId;
        this.departmentId = departmentId;
        this.admissionStateId = admissionStateId;
    }

    public ClinicalDataDto(String text) {
        clinicalRecord = text;
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getAdmissionStateId() {
        return admissionStateId;
    }

    public void setAdmissionStateId(Long admissionStateId) {
        this.admissionStateId = admissionStateId;
    }
}
