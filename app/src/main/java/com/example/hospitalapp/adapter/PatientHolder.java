package com.example.hospitalapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;

public class PatientHolder extends RecyclerView.ViewHolder {
    TextView firstName, lastName, birthDate;
    Button editButton, deleteButton, nextButton;
    public PatientHolder(@NonNull View itemView) {
        super(itemView);
        firstName = itemView.findViewById(R.id.textViewFirstName);
        lastName = itemView.findViewById(R.id.textViewLastName);
        birthDate = itemView.findViewById(R.id.textViewBirthday);
        editButton = itemView.findViewById(R.id.editButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}
