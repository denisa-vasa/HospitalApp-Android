package com.example.hospitalapp.retrofit;

import com.example.hospitalapp.dto.DepartmentDto;
import com.example.hospitalapp.dto.FilterDto;
import com.example.hospitalapp.dto.StringDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface DepartmentApi {
    @POST("/api/saveDepartment")
    Call<String> saveDepartment(@Body DepartmentDto departmentDto);

    @POST("/api/filterDepartment")
    Call<List<DepartmentDto>> filterDepartment(@Body FilterDto filterDto);

    @HTTP(method = "DELETE", path = "/api/deleteDepartment", hasBody = true)
    Call<String> deleteDepartment(@Body StringDto stringDto);

    @GET("/api/getAllDepartments")
    Call<List<DepartmentDto>> getAllDepartments();
}
