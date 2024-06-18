package com.example.hospitalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.AdmissionsManagementActivity;
import com.example.hospitalapp.R;
import com.example.hospitalapp.dto.DepartmentDto;

import java.util.ArrayList;
import java.util.List;

public class AdmissionsManagementAdapter extends RecyclerView.Adapter<AdmissionsManagementAdapter.AdmissionsViewHolder> {
    private List<DepartmentDto> departmentDtoList;
    private OnItemClickListener listener;
    private Context context;

    public AdmissionsManagementAdapter(List<DepartmentDto> departmentDtoList) {
        this.departmentDtoList = departmentDtoList;
    }

    public AdmissionsManagementAdapter(Context context, List<DepartmentDto> departmentDtoList) {
        this.context = context;
        departmentDtoList = departmentDtoList;
    }

    public void setData(List<DepartmentDto> newData) {
        departmentDtoList = newData;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DepartmentDto departmentDto);
        void onAdmitClick(DepartmentDto departmentDto);
        void onMoveToClick(DepartmentDto departmentDto);
    }

    @NonNull
    @Override
    public AdmissionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admissions_item, parent, false);
        return new AdmissionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionsViewHolder holder, int position) {
        if (departmentDtoList != null && !departmentDtoList.isEmpty()) {
            DepartmentDto departmentDto = departmentDtoList.get(position);
            holder.departmentName.setText(departmentDto.getName());
            holder.departmentCode.setText(departmentDto.getCode());

            holder.admitButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAdmitClick(departmentDto);
                }
            });

            holder.moveToButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMoveToClick(departmentDto);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (departmentDtoList != null) ? departmentDtoList.size() : 0;
    }

    public static class AdmissionsViewHolder extends RecyclerView.ViewHolder {
        public TextView departmentName, departmentCode;
        public Button admitButton, moveToButton;

        public AdmissionsViewHolder(@NonNull View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.departmentItem_nameAdmission);
            departmentCode = itemView.findViewById(R.id.departmentItem_codeAdmission);
            admitButton = itemView.findViewById(R.id.admitButton);
            moveToButton = itemView.findViewById(R.id.moveToButton);
        }
    }
}
