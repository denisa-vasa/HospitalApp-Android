package com.example.hospitalapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;

public class AdmissionsManagementHolder extends RecyclerView.ViewHolder {
    TextView name, code;
    Button admitButton, moveToButton;

    public AdmissionsManagementHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.departmentItem_nameAdmission);
        code = itemView.findViewById(R.id.departmentItem_codeAdmission);
        admitButton = itemView.findViewById(R.id.admitButton);
        moveToButton = itemView.findViewById(R.id.moveToButton);
    }
}
