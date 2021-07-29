package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.databinding.ActivityAboutUsBinding;
import com.areservices.databinding.ActivityTermsAndConditionBinding;

public class AboutUsActivity extends AppCompatActivity {
ActivityAboutUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}