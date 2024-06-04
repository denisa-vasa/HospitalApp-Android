package com.example.hospitalapp.retrofit;

import com.example.hospitalapp.dto.PatientDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface PatientsApi {
    @POST("/api/savePatient")
    Call<String> savePatient(@Body PatientDto patientDto);

    @POST("/api/filterPatients")
    Call<List<PatientDto>> filterPatients(@Body PatientDto patientDto);

    @HTTP(method = "DELETE", path = "/api/deletePatient", hasBody = true)
    Call<String> deletePatient(@Body PatientDto patientDto);

    @GET("/api/getAllPatients")
    Call<List<PatientDto>> getAllPatients();
}
