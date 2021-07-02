package com.areservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areservices.activity.ChooserServiceActivity;
import com.areservices.activity.CustomerCarDetailsActivity;
import com.areservices.databinding.RowchooseservicelayoutBinding;
import com.areservices.databinding.RowservicelistlayoutBinding;
import com.areservices.model.ChooseServiceModel;
import com.areservices.model.ServiceMenModel;

import java.util.ArrayList;

public class ChooseServiceAdapter extends RecyclerView.Adapter<ChooseServiceAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<ChooseServiceModel>serviceList;

    public ChooseServiceAdapter(Context mContext, ArrayList<ChooseServiceModel> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowchooseservicelayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChooseServiceModel modelObject = serviceList.get(position);
        holder.rowchooseservicelayoutBinding.txName.setText(modelObject.getName());



        holder.rowchooseservicelayoutBinding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mContext. startActivity(new Intent(mContext, CustomerCarDetailsActivity.class));
            }
        });



    }

    @Override
    public int getItemCount() {
        return serviceList == null ? 0 : serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowchooseservicelayoutBinding rowchooseservicelayoutBinding;
        public MyViewHolder(RowchooseservicelayoutBinding rowchooseservicelayoutBinding) {
            super(rowchooseservicelayoutBinding.getRoot());
            this.rowchooseservicelayoutBinding = rowchooseservicelayoutBinding;
        }

    }
}
