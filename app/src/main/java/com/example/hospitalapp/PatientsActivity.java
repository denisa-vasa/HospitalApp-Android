package com.example.hospitalapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.adapter.PatientAdapter;
import com.example.hospitalapp.dto.PatientDto;
import com.example.hospitalapp.retrofit.PatientsApi;
import com.example.hospitalapp.retrofit.RetrofitService;
import com.example.hospitalapp.utils.DateUtils;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientsActivity extends AppCompatActivity {

    private static final String TAG = "PatientsActivity";
    private RecyclerView recyclerView;
    private Button addButton, searchButton, dischargeButton, clinicalRecordButton;
    private EditText searchField;
    private PatientsApi patientsApi;
    private PatientAdapter patientAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patients);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchField = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchPatientsButton);
        addButton = findViewById(R.id.addPatientsButton);

        patientAdapter = new PatientAdapter(new ArrayList<>());
        recyclerView.setAdapter(patientAdapter);

        patientAdapter.setOnItemClickListener(new PatientAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(PatientDto patientDto) {

            }

            @Override
            public void onEditClick(PatientDto patientDto) {
                showPatientDialog(patientDto);
            }

            @Override
            public void onDeleteClick(PatientDto patientDto) {
                showDeleteConfirmationDialog(patientDto);
            }
        });

        RetrofitService retrofitService = new RetrofitService();
        patientsApi = retrofitService.getPatientsApi();

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // After the text changes, load patients based on the new search query
                String searchQuery = s.toString().trim();
                if (searchQuery.isEmpty()) {
                    loadPatients();
                }
            }
        });

        searchButton.setOnClickListener(v -> loadPatients());
        addButton.setOnClickListener(v -> showPatientDialog(null));

        loadPatients();
    }

    private void loadPatients() {
        String searchQuery = searchField.getText().toString().trim();

        if (searchQuery.isEmpty()) {
            fetchAllPatients(); // Fetch all patients if search query is empty
        } else {
            // Split the search query into first and last names
            String[] names = searchQuery.split(" ");
            String firstName = "";
            String lastName = "";
            if (names.length >= 1) {
                firstName = names[0];
            }
            if (names.length >= 2) {
                lastName = names[1];
            }

            Log.d(TAG, "Patient's first name: " + firstName + ", last name: " + lastName);
            searchPatients(firstName, lastName);
        }
    }

    private void fetchAllPatients() {
        Log.d(TAG, "Fetching all patients..");
        patientsApi.getAllPatients()
                .enqueue(new Callback<List<PatientDto>>() {
                    @Override
                    public void onResponse(Call<List<PatientDto>> call, Response<List<PatientDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "All Patients fetched successfully!");
                            populatePatients(response.body());
                        } else {
                            Log.e(TAG, "Failed tp fetch al patients, Code: " + response.code());
                            Toast.makeText(PatientsActivity.this, "Failed to load Patients!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PatientDto>> call, Throwable t) {
                        Log.e(TAG, "Failed to fetch all departments.", t);
                        Toast.makeText(PatientsActivity.this, "Failed to load Patients!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void searchPatients(String patientFirstName, String patientLastName) {
        PatientDto patientDto = new PatientDto(patientFirstName, patientLastName);

        Log.d(TAG, "Starting network call...");
        patientsApi.filterPatients(patientDto)
                .enqueue(new Callback<List<PatientDto>>() {
                    @Override
                    public void onResponse(Call<List<PatientDto>> call, Response<List<PatientDto>> response) {
                        Log.d(TAG, "onResponse called");
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "Response successful: " + response.body());
                            populatePatients(response.body());
                        } else {
                            Log.e(TAG, "Response failed or body is null. Code: " + response.code());
                            Toast.makeText(PatientsActivity.this, "Failed to load Patients!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PatientDto>> call, Throwable t) {
                        Log.e(TAG, "onFailure called", t);
                        Toast.makeText(PatientsActivity.this, "Failed to load Patients!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showPatientDialog(@Nullable PatientDto patientDto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_patient, null);
        builder.setView(dialogView);

        EditText patientFirstName = dialogView.findViewById(R.id.patientsFirstName);
        EditText patientLastName = dialogView.findViewById(R.id.patientsLastName);
        EditText patientsBirthDate = dialogView.findViewById(R.id.patientsBirthDate);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        if (patientDto != null) {
            patientFirstName.setText(patientDto.getFirstName());
            patientLastName.setText(patientDto.getLastName());
            patientsBirthDate.setText(DateUtils.convertToLocalDateString(patientDto.getBirthDate()));
        }

        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = patientFirstName.getText().toString();
                String lastName = patientLastName.getText().toString();
                String birthDateStr = patientsBirthDate.getText().toString();
                // Convert the birthDateStr to LocalDate
                LocalDate birthDate = DateUtils.convertToLocalDate(birthDateStr);

                if (!name.isEmpty() && !lastName.isEmpty() && !birthDateStr.isEmpty()) {
                    if (patientDto != null) {
                        patientDto.setFirstName(name);
                        patientDto.setLastName(lastName);

                        // Set the birthDate of the newPatient
                        patientDto.setBirthDate(birthDate);

                        // Log the attempt to save the patient
                        Log.d(TAG, "Attempting to save patient: " + patientDto);

                        // Save the new patient
                        savePatient(patientDto);
                    } else {
                        savePatient(new PatientDto(name, lastName, birthDate));
                    }
                    // Dismiss the dialog after successful save
                    dialog.dismiss();
                } else {
                    Toast.makeText(PatientsActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
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

    private void showDeleteConfirmationDialog(PatientDto patientDto) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Do you confirm deletion?")
                .setPositiveButton("Confirm", ((dialog, which) -> deletePatient(patientDto)))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deletePatient(PatientDto patientDto) {
        PatientDto ptDto = new PatientDto(patientDto.getFirstName(), patientDto.getLastName());

        Call<String> call = patientsApi.deletePatient(ptDto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PatientsActivity.this, "Patient deleted successfully", Toast.LENGTH_SHORT).show();
                    loadPatients(); // Refresh the list
                } else {
                    Toast.makeText(PatientsActivity.this, "Failed to delete patient", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PatientsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePatient(PatientDto patientDto) {
        Call<String> call = patientsApi.savePatient(patientDto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Refresh the patient list after successfully adding a new patient
                    loadPatients();
                    Toast.makeText(PatientsActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PatientsActivity.this, "Failed to add patient", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PatientsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatePatients(List<PatientDto> patientDtos) {
        patientAdapter.setData(patientDtos);
    }
}