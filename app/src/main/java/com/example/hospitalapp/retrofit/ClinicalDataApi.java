package com.example.hospitalapp.retrofit;

import com.example.hospitalapp.dto.ClinicalDataDto;
import com.example.hospitalapp.dto.PatientDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface ClinicalDataApi {
    @POST("/api/saveClinicalRecord")
    Call<String> saveClinicalRecord(@Body ClinicalDataDto clinicalDataDto);
    @POST("/api/filterClinicalRecord")
    Call<List<ClinicalDataDto>> filterClinicalRecord(@Body ClinicalDataDto clinicalDataDto);
    @HTTP(method = "DELETE", path = "/api/deleteClinicalRecord", hasBody = true)
    Call<String> deleteClinicalRecord(@Body ClinicalDataDto clinicalDataDto);
    @GET("/api/getAllClinicalRecords")
    Call<List<ClinicalDataDto>> getAllClinicalRecords();
    @POST("/api/getClinicalRecordsByPatientName")
    Call<List<ClinicalDataDto>> getClinicalRecordsByPatientName(@Body PatientDto patientDto);
}
