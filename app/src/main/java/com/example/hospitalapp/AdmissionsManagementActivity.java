package com.example.hospitalapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.adapter.AdmissionsManagementAdapter;
import com.example.hospitalapp.dto.AdmissionStateDto;
import com.example.hospitalapp.dto.DepartmentDto;
import com.example.hospitalapp.retrofit.AdmissionStateApi;
import com.example.hospitalapp.retrofit.DepartmentApi;
import com.example.hospitalapp.retrofit.RetrofitService;
import com.example.hospitalapp.utils.DepartmentAdapterInterface;
import com.example.hospitalapp.utils.DepartmentUtils;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdmissionsManagementActivity extends AppCompatActivity implements DepartmentAdapterInterface {
    private static final String TAG = "AdmissionsManagementActivity";
    private RecyclerView recyclerView;
    private Button searchButton;
    private EditText searchField;
    private DepartmentApi departmentApi;
    private AdmissionStateApi admissionStateApi;
    private AdmissionsManagementAdapter admissionsManagementAdapter;
    private DepartmentUtils departmentUtils;
    private Long patientId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_admissions_management);

        searchField = findViewById(R.id.searchFieldDepartments_admission);
        searchButton = findViewById(R.id.searchDepartmentsButton_admissions);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize the adapter with an empty list
        admissionsManagementAdapter = new AdmissionsManagementAdapter(this, new ArrayList<>());

        // Set the adapter to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(admissionsManagementAdapter);

        admissionsManagementAdapter.setOnItemClickListener(new AdmissionsManagementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DepartmentDto departmentDto) {

            }

            @Override
            public void onAdmitClick(DepartmentDto departmentDto) {
                showAdmissionDialog(departmentDto);
            }

            @Override
            public void onMoveToClick(DepartmentDto departmentDto) {
                showMoveToDialog(departmentDto);
            }
        });

        RetrofitService retrofitService = new RetrofitService();
        admissionStateApi = retrofitService.getAdmissionStateApi();
        departmentApi = retrofitService.getDepartmentApi();

        Intent intent = getIntent();
        if (intent != null) {
            patientId = intent.getLongExtra("PATIENT_ID", 0L); // Default value 0L or any appropriate default
        }

        departmentUtils = new DepartmentUtils();

        searchButton.setOnClickListener(v -> loadDepartments());

        loadDepartments();
    }

    private void loadDepartments() {
        String searchQuery = searchField.getText().toString().trim();
        departmentUtils.loadDepartments(this, departmentApi, searchQuery, this);
    }

    @Override
    public void setData(List<DepartmentDto> departmentDtoList) {
        admissionsManagementAdapter.setData(departmentDtoList);
    }

    private void showAdmissionDialog(DepartmentDto departmentDto) {
        showDialog("Admission Cause", departmentDto);
    }

    private void showMoveToDialog(DepartmentDto departmentDto) {
        showDialog("Transfer Cause", departmentDto);
    }

    private void showDialog(String title, DepartmentDto departmentDto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admission_move, null);
        builder.setView(dialogView);

        EditText causeEditText = dialogView.findViewById(R.id.causeEditText);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        AlertDialog dialog = builder.create();
        dialog.setTitle(title);

        confirmButton.setOnClickListener(v -> {
            String cause = causeEditText.getText().toString().trim();
            if (!cause.isEmpty()) {
                // Handle admission cause confirmation
                AdmissionStateDto admissionStateDto = new AdmissionStateDto();
                admissionStateDto.setCause(cause);
                admissionStateDto.setDischarge(false);

                LocalDateTime enteringDate = LocalDateTime.now();
                LocalDateTime exitingDate = enteringDate.plusDays(2);

                admissionStateDto.setEnteringDate(enteringDate);
                admissionStateDto.setExitingDate(exitingDate);
                admissionStateDto.setReason("Not Discharged");
                admissionStateDto.setDepartmentId(departmentDto.getId());
                admissionStateDto.setPatientId(patientId); // Assuming patient ID is 1 for this example

                saveAdmissionState(admissionStateDto);

            } else {
                Toast.makeText(AdmissionsManagementActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void saveAdmissionState(AdmissionStateDto admissionStateDto) {
        Call<String> call = admissionStateApi.saveCause(admissionStateDto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdmissionsManagementActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdmissionsManagementActivity.this, "Failed to add admission state", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AdmissionsManagementActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}