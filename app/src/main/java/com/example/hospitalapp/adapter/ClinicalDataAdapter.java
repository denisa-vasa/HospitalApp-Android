package com.example.hospitalapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapp.R;
import com.example.hospitalapp.dto.ClinicalDataDto;
import com.example.hospitalapp.model.ClinicalData;

import java.util.List;

public class ClinicalDataAdapter extends RecyclerView.Adapter<ClinicalDataHolder> {
    private List<ClinicalDataDto> clinicalDataDtoList;
    private OnItemClickListener listener;
    public ClinicalDataAdapter(List<ClinicalDataDto> clinicalDataDtoList) {
        this.clinicalDataDtoList = clinicalDataDtoList;
    }

    public void setData(List<ClinicalDataDto> newData) {
        clinicalDataDtoList.clear();
        clinicalDataDtoList.addAll(newData);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ClinicalDataDto clinicalDataDto);
        void onEditClick(ClinicalDataDto clinicalDataDto);
        void onDeleteClick(ClinicalDataDto clinicalDataDto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ClinicalDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clinical_record_item, parent, false);
        return new ClinicalDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicalDataHolder holder, int position) {
        ClinicalDataDto clinicalDataDto = clinicalDataDtoList.get(position);
        holder.clinicalRecord.setText(clinicalDataDto.getClinicalRecord());

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(clinicalDataDto);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(clinicalDataDto);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(clinicalDataDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clinicalDataDtoList.size();
    }

    public static class ClinicalDataViewHolder extends RecyclerView.ViewHolder {
        public TextView clinicalRecord;
        public Button editButton, deleteButton;
        public ClinicalDataViewHolder(@NonNull View itemView) {
            super(itemView);
            clinicalRecord = itemView.findViewById(R.id.clinicalRecordItem);
            editButton = itemView.findViewById(R.id.editButton_clinicalRecord);
            deleteButton = itemView.findViewById(R.id.deleteButton_clinicalRecord);
        }
    }
}
