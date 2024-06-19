package com.example.hospitalapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.AdmissionsManagementActivity;
import com.example.hospitalapp.ClinicalRecordsActivity;
import com.example.hospitalapp.R;
import com.example.hospitalapp.dto.PatientDto;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientHolder> {
    private List<PatientDto> patientDtoList;
    private OnItemClickListener listener;
    private Context context; // Add this line

    public PatientAdapter(Context context, List<PatientDto> patientDtoList) {
        this.context = context; // Initialize context
        this.patientDtoList = patientDtoList;
    }

    public void setData(List<PatientDto> newData) {
        patientDtoList.clear();
        patientDtoList.addAll(newData);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(PatientDto patientDto);
        void onEditClick(PatientDto patientDto);
        void onDeleteClick(PatientDto patientDto);
        void onDischargeClick(PatientDto patientDto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patients_item, parent, false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        PatientDto patientDto = patientDtoList.get(position);

        holder.firstName.setText(patientDto.getFirstName());
        holder.lastName.setText(patientDto.getLastName());
        LocalDate birthDate = patientDto.getBirthDate();
        if (birthDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = birthDate.format(formatter);
            holder.birthDate.setText(formattedDate);
        } else {
            holder.birthDate.setText(""); // or any placeholder text
        }

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(patientDto);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(patientDto);
            }
        });

        holder.clinicalRecordsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ClinicalRecordsActivity.class);
            intent.putExtra("PATIENT_ID", patientDto.getId());
            intent.putExtra("PATIENT_NAME", patientDto.getFirstName() + " " + patientDto.getLastName());
            //intent.putExtra("DEPARTMENT_ID", patientDto.getDepartmentId());  // Add this line
           // intent.putExtra("ADMISSION_STATE_ID", patientDto.getAdmissionStateId());  // Add this line
            context.startActivity(intent);
        });

        holder.dischargeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDischargeClick(patientDto);
            }
        });

        holder.nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdmissionsManagementActivity.class);
            intent.putExtra("PATIENT_ID", patientDto.getId());
            intent.putExtra("PATIENT_NAME", patientDto.getFirstName() + " " + patientDto.getLastName());
            context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(patientDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientDtoList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        public TextView patientName, patientLastName, patientBirthDate;
        public Button editButton, deleteButton, nextButton, dischargeButton, clinicalRecordsButton;
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.textViewFirstName);
            patientLastName = itemView.findViewById(R.id.patientsLastName);
            patientBirthDate = itemView.findViewById(R.id.patientsBirthDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            nextButton = itemView.findViewById(R.id.nextButton);
            dischargeButton = itemView.findViewById(R.id.dischargeButton);
            clinicalRecordsButton = itemView.findViewById(R.id.clinicalRecordsButton);
        }
    }
}
