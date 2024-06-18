package com.example.hospitalapp.retrofit;

import com.example.hospitalapp.dto.AdmissionStateDto;
import com.example.hospitalapp.dto.DischargeReasonDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdmissionStateApi {
    @POST("/api/dischargeReason")
    Call<String> setDischargeReason(@Body DischargeReasonDto dischargeReasonDto);
    @POST("/api/saveCause")
    Call<String> saveCause(@Body AdmissionStateDto admissionStateDto);
}
