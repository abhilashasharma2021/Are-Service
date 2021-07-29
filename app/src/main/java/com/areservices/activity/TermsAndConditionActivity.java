package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.databinding.ActivityForgotPasswordBinding;
import com.areservices.databinding.ActivityTermsAndConditionBinding;

public class TermsAndConditionActivity extends AppCompatActivity {
 ActivityTermsAndConditionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTermsAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TermsAndConditionActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}