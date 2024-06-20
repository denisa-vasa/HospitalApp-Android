package com.example.hospitalapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;
import com.example.hospitalapp.activities.DepartmentActivity;
import com.example.hospitalapp.adapter.DepartmentAdapter;
import com.example.hospitalapp.dto.DepartmentDto;
import com.example.hospitalapp.dto.FilterDto;
import com.example.hospitalapp.dto.StringDto;
import com.example.hospitalapp.retrofit.DepartmentApi;
import com.example.hospitalapp.retrofit.RetrofitService;
import com.example.hospitalapp.utils.DepartmentAdapterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentFragment extends Fragment {

    private static final String TAG = "DepartmentFragment";
    private RecyclerView recyclerView;
    private Button searchButton, addButton;
    private EditText searchField;
    private DepartmentApi departmentApi;
    private DepartmentAdapter departmentAdapter;
    DepartmentFragment departmentUtils;
    public DepartmentFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_department, container, false);

        searchField = view.findViewById(R.id.searchField);
        searchButton = view.findViewById(R.id.searchButton);
        addButton = view.findViewById(R.id.addButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        departmentAdapter = new DepartmentAdapter(new ArrayList<>());
        recyclerView.setAdapter(departmentAdapter);

        departmentAdapter.setOnItemClickListener(new DepartmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DepartmentDto departmentDto) {

            }

            @Override
            public void onEditClick(DepartmentDto departmentDto) {
                showDepartmentDialog(departmentDto);
            }

            @Override
            public void onDeleteClick(DepartmentDto departmentDto) {
                showDeleteConfirmationDialog(departmentDto);
            }
        });

        RetrofitService retrofitService = new RetrofitService();
        departmentApi = retrofitService.getDepartmentApi();

        searchButton.setOnClickListener(v -> loadDepartments());
        addButton.setOnClickListener(v -> showDepartmentDialog(null));

        loadDepartments();

        return view;
    }

    private void loadDepartments() {
        String searchQuery = searchField.getText().toString().trim();
        if (searchQuery.isEmpty()) {
            fetchAllDepartments();
        } else {
            Log.d(TAG, "Department Name: " + searchQuery);
            searchDepartments(searchQuery);
        }
    }

    private void fetchAllDepartments() {
        Log.d(TAG, "Fetching all departments..");
        departmentApi.getAllDepartments()
                .enqueue(new Callback<List<DepartmentDto>>() {
                    @Override
                    public void onResponse(Call<List<DepartmentDto>> call, Response<List<DepartmentDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "All departments fetched successfully.");
                            populateDepartment(response.body());
                        } else {
                            Log.e(TAG, "Failed to fetch all departments. Code: " + response.code());
                            Toast.makeText(requireContext(), "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DepartmentDto>> call, Throwable t) {
                        Log.e(TAG, "Failed to fetch all departments.", t);
                        Toast.makeText(requireContext(), "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void searchDepartments(String departmentName) {
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
                            populateDepartment(response.body());
                        } else {
                            Log.e(TAG, "Response failed or body is null. Code: " + response.code());
                            Toast.makeText(requireContext(), "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DepartmentDto>> call, Throwable t) {
                        Log.e(TAG, "onFailure called", t);
                        Toast.makeText(requireContext(), "Failed to load Departments!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDepartmentDialog(@Nullable DepartmentDto departmentDto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_department, null);
        builder.setView(dialogView);

        EditText departmentName = dialogView.findViewById(R.id.departmentName);
        EditText departmentCode = dialogView.findViewById(R.id.departmentCode);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        if (departmentDto != null) {
            departmentName.setText(departmentDto.getName());
            departmentCode.setText(departmentDto.getCode());
        }
        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = departmentName.getText().toString();
                String code = departmentCode.getText().toString();

                if (!name.isEmpty() && !code.isEmpty()) {
                    if (departmentDto != null) {
                        departmentDto.setName(name);
                        departmentDto.setCode(code);
                        saveDepartment(departmentDto);
                    } else {
                        saveDepartment(new DepartmentDto(name, code));
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDeleteConfirmationDialog(DepartmentDto departmentDto) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Deletion")
                .setMessage("Do you confirm deletion?")
                .setPositiveButton("Confirm", ((dialog, which) -> deleteDepartment(departmentDto)))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteDepartment(DepartmentDto departmentDto) {
        StringDto stringDto = new StringDto();
        stringDto.setName(departmentDto.getName());

        Call<String> call = departmentApi.deleteDepartment(stringDto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Department deleted successfully", Toast.LENGTH_SHORT).show();
                    loadDepartments(); // Refresh the list
                } else {
                    Toast.makeText(requireContext(), "Failed to delete department", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDepartment(DepartmentDto departmentDto) {
        Call<String> call = departmentApi.saveDepartment(departmentDto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    Toast.makeText(requireContext(), responseBody, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to add department", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateDepartment(List<DepartmentDto> departmentDtoList) {
        departmentAdapter.setData(departmentDtoList);
    }
}
