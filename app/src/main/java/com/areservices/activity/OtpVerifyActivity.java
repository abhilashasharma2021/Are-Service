package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.areservices.R;
import com.areservices.databinding.ActivityOtpVerifyBinding;
import com.areservices.databinding.ActivityRegisterBinding;

public class OtpVerifyActivity extends AppCompatActivity {
ActivityOtpVerifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}