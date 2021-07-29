package com.areservices.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areservices.activity.CustomerCarDetailsActivity;
import com.areservices.databinding.RowchooseservicelayoutBinding;
import com.areservices.model.ChooseServiceModel;
import com.bumptech.glide.Glide;

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

        if (!modelObject.equals("")){
            holder.rowchooseservicelayoutBinding.txName.setText(modelObject.getName());



            try {
                Glide.with(holder.itemView)
                        .load(modelObject.getPath()+modelObject.getImage())
                        .into(holder.rowchooseservicelayoutBinding.ivMain);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }



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
