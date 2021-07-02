package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.areservices.R;
import com.areservices.adapter.ChooseServiceAdapter;
import com.areservices.adapter.ServiceMenListAdapter;
import com.areservices.databinding.ActivityChooserServiceBinding;
import com.areservices.databinding.ActivityServiceMenListBinding;
import com.areservices.model.ChooseServiceModel;
import com.areservices.model.ServiceMenModel;

import java.util.ArrayList;

public class ChooserServiceActivity extends AppCompatActivity {
ActivityChooserServiceBinding binding;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ChooseServiceModel>serviceList=new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChooserServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        layoutManager=new GridLayoutManager(ChooserServiceActivity.this,3,RecyclerView.VERTICAL,false);
        binding.rvService.setLayoutManager(layoutManager);
        ChooseServiceAdapter adapter=new ChooseServiceAdapter(ChooserServiceActivity.this,serviceList);
        binding.rvService.setAdapter(adapter);


        chooseList();
    }

    private void chooseList(){

        ChooseServiceModel model=new ChooseServiceModel("Light Vehicel",R.drawable.img);
        for (int i = 0; i <6 ; i++) {
            serviceList.add(model);
        }


    }
}