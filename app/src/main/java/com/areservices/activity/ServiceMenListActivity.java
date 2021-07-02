package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.areservices.R;
import com.areservices.adapter.ServiceMenListAdapter;
import com.areservices.databinding.ActivityForgotPasswordBinding;
import com.areservices.databinding.ActivityServiceMenListBinding;
import com.areservices.model.ServiceMenModel;

import java.util.ArrayList;

public class ServiceMenListActivity extends AppCompatActivity {
ActivityServiceMenListBinding binding;

RecyclerView.LayoutManager layoutManager;
ArrayList<ServiceMenModel>serviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityServiceMenListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        serviceList=new ArrayList<>();
        binding.rvList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(ServiceMenListActivity.this,RecyclerView.VERTICAL,false);
        binding.rvList.setLayoutManager(layoutManager);
        ServiceMenListAdapter adapter=new ServiceMenListAdapter(ServiceMenListActivity.this,serviceList);
        binding.rvList.setAdapter(adapter);


        showList();
    }




    private void showList(){

        ServiceMenModel model=new ServiceMenModel("Rohit Sharma","Car Mechanic","MP Nagar Zone 2","Working");
        for (int i = 0; i <2 ; i++) {
            serviceList.add(model);
        }


    }
}