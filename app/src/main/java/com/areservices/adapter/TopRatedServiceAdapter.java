package com.areservices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areservices.databinding.RowtopservicelayoutBinding;
import com.areservices.model.TopRatedServiceModel;

import java.util.ArrayList;

public class TopRatedServiceAdapter extends RecyclerView.Adapter<TopRatedServiceAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<TopRatedServiceModel>serviceList;

    public TopRatedServiceAdapter(Context mContext, ArrayList<TopRatedServiceModel> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowtopservicelayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TopRatedServiceModel modelObject = serviceList.get(position);

        if (!modelObject.equals("")){
            holder.rowtopservicelayoutBinding.txName.setText(modelObject.getService_Name());
            holder.rowtopservicelayoutBinding.ivMain.setImageResource(modelObject.getImage());
            holder.rowtopservicelayoutBinding.ratingStar.setRating(Float.parseFloat("3.4"));

           /* try {
                Glide.with(holder.itemView)
                        .load(modelObject.getPath()+modelObject.getImage())
                        .into(holder.rowchooseservicelayoutBinding.ivMain);
            } catch (Exception e) {
                e.printStackTrace();
            }*/


        }


    }

    @Override
    public int getItemCount() {
        return serviceList == null ? 0 : serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowtopservicelayoutBinding rowtopservicelayoutBinding;
        public MyViewHolder(RowtopservicelayoutBinding rowtopservicelayoutBinding) {
            super(rowtopservicelayoutBinding.getRoot());
            this.rowtopservicelayoutBinding = rowtopservicelayoutBinding;
        }

    }
}
