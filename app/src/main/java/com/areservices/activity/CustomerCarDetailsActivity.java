package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.areservices.R;
import com.areservices.databinding.ActivityCustomerCarDetailsBinding;
import com.areservices.databinding.ActivityServiceMenListBinding;

public class CustomerCarDetailsActivity extends AppCompatActivity {
ActivityCustomerCarDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCustomerCarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerCarDetailsActivity.this, ServiceMenListActivity.class));
            }
        });
    }
}