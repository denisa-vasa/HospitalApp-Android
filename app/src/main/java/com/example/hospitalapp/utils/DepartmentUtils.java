package com.example.hospitalapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hospitalapp.dto.DepartmentDto;
import com.example.hospitalapp.dto.FilterDto;
import com.example.hospitalapp.retrofit.DepartmentApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentUtils {

    private static final String TAG = "DepartmentUtils";
    public void loadDepartments(Context context, DepartmentApi departmentApi, String searchQuery, DepartmentAdapterInterface departmentAdapterInterface) {
        if (searchQuery.isEmpty()) {
            fetchAllDepartments(context, departmentApi, departmentAdapterInterface);
        } else {
            Log.d(TAG, "Department Name: " + searchQuery);
            searchDepartments(context, departmentApi, searchQuery, departmentAdapterInterface);
        }
    }

    public void fetchAllDepartments(Context context, DepartmentApi departmentApi, DepartmentAdapterInterface departmentAdapterInterface) {
        Log.d(TAG, "Fetching all departments..");
        departmentApi.getAllDepartments()
                .enqueue(new Callback<List<DepartmentDto>>() {
                    @Override
                    public void onResponse(Call<List<DepartmentDto>> call, Response<List<DepartmentDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "All departments fetched successfully.");
                            departmentAdapterInterface.setData(response.body());
                        } else {
                            Log.e(TAG, "Failed to fetch all departments. Code: " + response.code());
                            Toast.makeText(context, "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DepartmentDto>> call, Throwable t) {
                        Log.e(TAG, "Failed to fetch all departments.", t);
                        Toast.makeText(context, "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void searchDepartments(Context context, DepartmentApi departmentApi, String departmentName, DepartmentAdapterInterface departmentAdapterInterface) {
        FilterDto filterDto = new FilterDto();
        filterDto.setName(departmentName);

        Log.d(TAG, "Starting network call...");
        departmentApi.filterDepartment(filterDto)
                .enqueue(new Callback<List<DepartmentDto>>() {
                    @Override
                    public void onResponse(Call<List<DepartmentDto>> call, Response<List<DepartmentDto>> response) {
                        Log.d(TAG, "onResponse called");
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "Response successful: " + response.body().toString());
                            departmentAdapterInterface.setData(response.body());
                        } else {
                            Log.e(TAG, "Response failed or body is null. Code: " + response.code());
                            Toast.makeText(context, "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DepartmentDto>> call, Throwable t) {
                        Log.e(TAG, "onFailure called", t);
                        Toast.makeText(context, "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
