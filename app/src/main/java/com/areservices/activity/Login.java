package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.areservices.R;
import com.areservices.databinding.ActivityLogin2Binding;

public class Login extends AppCompatActivity {
ActivityLogin2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txNEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterActivity.class));
            }
        });
    }
}