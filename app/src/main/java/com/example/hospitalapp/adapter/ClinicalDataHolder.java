package com.example.hospitalapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;

public class ClinicalDataHolder extends RecyclerView.ViewHolder {
    TextView clinicalRecord;
    Button editButton, deleteButton;
    public ClinicalDataHolder(@NonNull View itemView) {
        super(itemView);
        clinicalRecord = itemView.findViewById(R.id.clinicalRecordItem);
        editButton = itemView.findViewById(R.id.editButton_clinicalRecord);
        deleteButton = itemView.findViewById(R.id.deleteButton_clinicalRecord);
    }
}
