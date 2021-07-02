package com.areservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areservices.activity.CustomerCarDetailsActivity;
import com.areservices.activity.ProfileActivity;
import com.areservices.databinding.RowservicelistlayoutBinding;
import com.areservices.model.ServiceMenModel;

import java.util.ArrayList;

public class ServiceMenListAdapter extends RecyclerView.Adapter<ServiceMenListAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<ServiceMenModel>serviceList;

    public ServiceMenListAdapter(Context mContext, ArrayList<ServiceMenModel> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowservicelistlayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServiceMenModel modelObject = serviceList.get(position);
        holder.rowservicelistlayoutBinding.txName.setText(modelObject.getName());
        holder.rowservicelistlayoutBinding.txAddress.setText(modelObject.getAddress());
        holder.rowservicelistlayoutBinding.txStream.setText(modelObject.getStream());
        holder.rowservicelistlayoutBinding.txStatus.setText(modelObject.getWorkingStatus());


        holder.rowservicelistlayoutBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext. startActivity(new Intent(mContext, ProfileActivity.class));
            }
        });




    }

    @Override
    public int getItemCount() {
        return serviceList == null ? 0 : serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowservicelistlayoutBinding rowservicelistlayoutBinding;
        public MyViewHolder(RowservicelistlayoutBinding rowservicelistlayoutBinding) {
            super(rowservicelistlayoutBinding.getRoot());
            this.rowservicelistlayoutBinding = rowservicelistlayoutBinding;
        }

    }
}
