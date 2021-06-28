package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.areservices.databinding.ActivityMobileVerifyBinding;


public class MobileVerifyActivity extends AppCompatActivity {
    ActivityMobileVerifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMobileVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileVerifyActivity.this, OtpVerifyActivity.class));
            }
        });


    }
}