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

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentHolder> {

    private List<DepartmentDto> departmentDtoList;

    private OnItemClickListener listener;

    public DepartmentAdapter(List<DepartmentDto> departmentDtoList) {
        this.departmentDtoList = departmentDtoList;
    }

    public void setData(List<DepartmentDto> newData) {
        departmentDtoList.clear();
        departmentDtoList.addAll(newData);
        notifyDataSetChanged();
    }

    public void setOnEditClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DepartmentDto departmentDto);

        void onEditClick(DepartmentDto departmentDto);

        void onDeleteClick(DepartmentDto departmentDto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DepartmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_item, parent, false);
        return new DepartmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentHolder holder, int position) {
        DepartmentDto departmentDto = departmentDtoList.get(position);
        holder.name.setText(departmentDto.getName());
        holder.code.setText(departmentDto.getCode());

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(departmentDto);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(departmentDto);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(departmentDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return departmentDtoList.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        public TextView departmentName, departmentCode;
        public Button editButton, deleteButton;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.departmentItem_name);
            departmentCode = itemView.findViewById(R.id.departmentItem_code);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
