package com.example.hospitalapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;

public class DepartmentHolder extends RecyclerView.ViewHolder {
    TextView name, code;
    Button editButton, deleteButton;
    public DepartmentHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.departmentItem_name);
        code = itemView.findViewById(R.id.departmentItem_code);
        editButton = itemView.findViewById(R.id.editButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}
