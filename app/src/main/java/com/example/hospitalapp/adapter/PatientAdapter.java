package com.example.hospitalapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;
import com.example.hospitalapp.dto.DepartmentDto;
import com.example.hospitalapp.dto.PatientDto;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientHolder> {
    private List<PatientDto> patientDtoList;
    private OnItemClickListener listener;
    public PatientAdapter(List<PatientDto> patientDtoList) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = birthDate.format(formatter);
        holder.birthDate.setText(formattedDate);

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
        public Button editButton, deleteButton, nextButton;
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientsFirstName);
            patientLastName = itemView.findViewById(R.id.patientsLastName);
            patientBirthDate = itemView.findViewById(R.id.patientsBirthDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
