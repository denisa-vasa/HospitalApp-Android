package com.example.hospitalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.adapter.ClinicalDataAdapter;
import com.example.hospitalapp.dto.ClinicalDataDto;
import com.example.hospitalapp.dto.LongDto;
import com.example.hospitalapp.dto.PatientDto;
import com.example.hospitalapp.retrofit.ClinicalDataApi;
import com.example.hospitalapp.retrofit.PatientsApi;
import com.example.hospitalapp.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicalRecordsActivity extends AppCompatActivity {
    private static final String TAG = "ClinicalDataActivity";
    private RecyclerView recyclerView;
    private Button searchButton, addButton, backButton;
    private EditText searchField;
    private TextView patientsName;
    private ClinicalDataApi clinicalDataApi;
    private PatientsApi patientsApi;
    private ClinicalDataAdapter clinicalDataAdapter;
    private String patientName;
    private Long selectedPatientId;
    private Long selectedDepartmentId;
    private Long selectedAdmissionStateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clinical_records);

        searchField = findViewById(R.id.searchField_clinicalRecord);
        searchButton = findViewById(R.id.searchButton_clinicalRecord);
        addButton = findViewById(R.id.addButton_clinicalRecord);
        backButton = findViewById(R.id.backButton_clinicalRecord);
        patientsName = findViewById(R.id.patientsName_clinicalRecord);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clinicalDataAdapter = new ClinicalDataAdapter(new ArrayList<>());
        recyclerView.setAdapter(clinicalDataAdapter);

        RetrofitService retrofitService = new RetrofitService();
        clinicalDataApi = retrofitService.getClinicalDataApi();
        patientsApi = retrofitService.getPatientsApi();
        if (clinicalDataApi == null) {
            Log.e(TAG, "ClinicalDataApi instance is null. Unable to make API calls.");
            return;
        }

        // Retrieve the patient's name from the intent extras
        Intent intent = getIntent();
        if (intent != null) {
            selectedPatientId = intent.getLongExtra("PATIENT_ID", 0L);
            patientName = intent.getStringExtra("PATIENT_NAME");

            if (patientName != null) {
                patientsName.setText(patientName);
                PatientDto patientDto = new PatientDto(selectedPatientId, patientName);
                loadClinicalData(patientDto);
            }

            if (selectedPatientId != null && selectedPatientId > 0) {
                // Fetch patient details using selectedPatientId
                fetchPatientById(selectedPatientId);
            } else {
                Log.e(TAG, "Invalid patient ID: " + selectedPatientId);
            }
        }

        clinicalDataAdapter.setOnItemClickListener(new ClinicalDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClinicalDataDto clinicalDataDto) {

            }
            @Override
            public void onEditClick(ClinicalDataDto clinicalDataDto) {
                showClinicalDataDialog(clinicalDataDto);
            }
            @Override
            public void onDeleteClick(ClinicalDataDto clinicalDataDto) {
                showDeleteConfirmationDialog(clinicalDataDto, new PatientDto());
            }
        });

        addButton.setOnClickListener(v -> showClinicalDataDialog(null));

        searchButton.setOnClickListener(v -> {
            String clinicalRecord = searchField.getText().toString().trim();
            if (clinicalRecord.isEmpty()) {
                loadClinicalData(new PatientDto(patientName));
            } else {
                Log.d(TAG, "Clinical Record: " + clinicalRecord);
                searchClinicalRecord(clinicalRecord);
            }
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void fetchPatientById(Long selectedPatientId) {
        LongDto longDto = new LongDto();
        longDto.setId(selectedPatientId);

        patientsApi.getPatientById(longDto).enqueue(new Callback<PatientDto>() {
            @Override
            public void onResponse(Call<PatientDto> call, Response<PatientDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PatientDto patientDto = response.body();
                    Log.d(TAG, "Patient fetched successfully: " + patientDto.getFirstName() + patientDto.getLastName());
                    selectedDepartmentId = patientDto.getDepartmentId(); // Assuming PatientDto contains these fields
                    selectedAdmissionStateId = patientDto.getAdmissionStateId(); // Assuming PatientDto contains these fields
                    // Do something with the patient data, e.g., update UI or load clinical data
                    loadClinicalData(patientDto);
                } else {
                    Log.e(TAG, "Failed to fetch patient details. Code: " + response.code());
                    Toast.makeText(ClinicalRecordsActivity.this, "Failed to fetch patient details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PatientDto> call, Throwable t) {
                Log.e(TAG, "Failed to fetch patient details", t);
                Toast.makeText(ClinicalRecordsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadClinicalData(PatientDto patientDto) {
        String clinicalRecord = searchField.getText().toString();
        if (clinicalRecord.isEmpty()) {
            fetchClinicalRecords(patientDto);
        } else {
            Log.d(TAG, "Clinical Record: " + clinicalRecord);
            searchClinicalRecord(clinicalRecord);
        }
    }

    private void fetchClinicalRecords(PatientDto patientDto) {
        Log.d(TAG, "Fetching all clinical records..");
        clinicalDataApi.getClinicalRecordsByPatientName(patientDto)
                .enqueue(new Callback<List<ClinicalDataDto>>() {
                    @Override
                    public void onResponse(Call<List<ClinicalDataDto>> call, Response<List<ClinicalDataDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "The Clinical Records fetched successfully!");
                            populateClinicalData(response.body());
                        } else {
                            Log.e(TAG, "Failed to fetch the Clinical Records! Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClinicalDataDto>> call, Throwable t) {
                        Log.e(TAG, "Failed to fetch the Clinical Records!", t);
                        Toast.makeText(ClinicalRecordsActivity.this, "Failed to load Clinical Records", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void searchClinicalRecord(String clinicalRecord) {
        ClinicalDataDto clinicalDataDto = new ClinicalDataDto();
        clinicalDataDto.setClinicalRecord(clinicalRecord);

        Log.d(TAG, "Starting network call...");
        clinicalDataApi.filterClinicalRecord(clinicalDataDto)
                .enqueue(new Callback<List<ClinicalDataDto>>() {
                    @Override
                    public void onResponse(Call<List<ClinicalDataDto>> call, Response<List<ClinicalDataDto>> response) {
                        Log.d(TAG, "onResponse called");
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "Response successful: " + response.body().toString());
                            populateClinicalData(response.body());
                        } else {
                            Log.e(TAG, "Response failed or body is null. Code: " + response.code());
                            Toast.makeText(ClinicalRecordsActivity.this, "Failed to load Clinical Records!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClinicalDataDto>> call, Throwable t) {
                        Log.e(TAG, "onFailure called", t);
                        Toast.makeText(ClinicalRecordsActivity.this, "Failed to load Clinical Records!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showClinicalDataDialog(@Nullable ClinicalDataDto clinicalDataDto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_clinical_record, null);
        builder.setView(dialogView);

        EditText clinicalRecord = dialogView.findViewById(R.id.bigTextField);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        if (clinicalDataDto != null) {
            clinicalRecord.setText(clinicalDataDto.getClinicalRecord());
        }
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            showKeyboard(clinicalRecord);
        });

        dialog.setOnDismissListener(dialogInterface -> {
            hideKeyboard(clinicalRecord);
        });
        
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = clinicalRecord.getText().toString();

                if (!text.isEmpty()) {
                    if (clinicalDataDto != null) {
                        clinicalDataDto.setClinicalRecord(text);
                        clinicalDataDto.setPatientId(selectedPatientId);
                        clinicalDataDto.setDepartmentId(selectedDepartmentId);
                        clinicalDataDto.setAdmissionStateId(selectedAdmissionStateId);
                        saveClinicalData(clinicalDataDto);
                    } else {
                        ClinicalDataDto newClinicalData = new ClinicalDataDto();
                        newClinicalData.setClinicalRecord(text);
                        newClinicalData.setPatientId(selectedPatientId);
                        newClinicalData.setDepartmentId(selectedDepartmentId);
                        newClinicalData.setAdmissionStateId(selectedAdmissionStateId);
                        saveClinicalData(newClinicalData);
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(ClinicalRecordsActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
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

    private void showKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showDeleteConfirmationDialog(ClinicalDataDto clinicalDataDto, PatientDto patientDto) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm deletion")
                .setMessage("Do you confirm deletion?")
                .setPositiveButton("Confirm", ((dialog, which) -> deleteClinicalData(clinicalDataDto, patientDto)))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteClinicalData(ClinicalDataDto clinicalDataDto, PatientDto patientDto) {
        LongDto longDto = new LongDto();
        longDto.setId(clinicalDataDto.getId());

        Call<String> call = clinicalDataApi.deleteClinicalRecord(clinicalDataDto);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ClinicalRecordsActivity.this, "Clinical Record deleted successfully!", Toast.LENGTH_SHORT).show();
                    loadClinicalData(new PatientDto(selectedPatientId, patientName));
                } else {
                    Toast.makeText(ClinicalRecordsActivity.this, "Failed to delete Clinical Record!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ClinicalRecordsActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveClinicalData(ClinicalDataDto clinicalDataDto) {
        Call<String> call = clinicalDataApi.saveClinicalRecord(clinicalDataDto);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ClinicalRecordsActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                    loadClinicalData(new PatientDto(selectedPatientId, patientName));
                } else {
                    Toast.makeText(ClinicalRecordsActivity.this, "Failed to add clinical record", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ClinicalRecordsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateClinicalData(List<ClinicalDataDto> clinicalDataDtoList) {
        clinicalDataAdapter.setData(clinicalDataDtoList);
    }
}