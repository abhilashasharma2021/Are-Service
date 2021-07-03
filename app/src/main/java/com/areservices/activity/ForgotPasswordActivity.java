package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.databinding.ActivityForgotPasswordBinding;
import com.areservices.others.API;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.areservices.others.ProgressBarCustom.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
ActivityForgotPasswordBinding binding;
    RequestQueue queue;
    String stEmail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stEmail = binding.etEmail.getText().toString().trim();
                if (validation()){
                    forGot();
                }
                else {
                    validation();
                }
            }
        });

    }



    private void forGot() {
        CustomDialog dialog = new CustomDialog();
        dialog.showDialog(R.layout.progress_layout, ForgotPasswordActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, API.forget_password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hideDialog();

                Log.e("gfdghf", "response: " +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.has("result")) {

                        String result = jsonObject.getString("result");

                        if (result.equals("An email has been sent to you with instructions")) {


                            binding.etEmail.setText("");
                            Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        }


                    }


                } catch (JSONException e) {
                    Log.e("dfgfd", "e: " + e);
                    dialog.hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hideDialog();
                Log.e("dfgfd", "error: " + error);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", stEmail);
                return map;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(request);


    }

    private Boolean validation(){
        Boolean boolen = false;

        if (binding.etEmail.getText().toString().isEmpty()){
            binding.etEmail.setError("Registered email address  Must Required");
        }
        else {
            boolen = true;

        }
        return boolen;
    }



}