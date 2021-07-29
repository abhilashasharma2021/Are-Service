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
import com.areservices.R;
import com.areservices.databinding.ActivityRegisterBinding;
import com.areservices.others.API;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.areservices.others.ProgressBarCustom.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    RequestQueue queue;
    String stName="",stEmail="",stMobile="",stPassword="",stConfirm="",stOTP="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        queue=SingletonRequestQueue.getInstance(this).getRequestQueue();
        binding.txtAlredy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, Login.class));
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stName=binding.etName.getText().toString().trim();
                stEmail=binding.etEmail.getText().toString().trim();
                stMobile=binding.etMobile.getText().toString().trim();
                stPassword=binding.etPassword.getText().toString().trim();
                stConfirm=binding.etCPass.getText().toString().trim();

                if (stName.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (stEmail.isEmpty()){

                    Toast.makeText(RegisterActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (stMobile.isEmpty()){

                    Toast.makeText(RegisterActivity.this, "Please Enter Your Valid Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if (stPassword.isEmpty()){

                    Toast.makeText(RegisterActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }

                else if (stConfirm.isEmpty()){

                    Toast.makeText(RegisterActivity.this, "Please Enter Your Confirmpassword", Toast.LENGTH_SHORT).show();
                }

                else if (!stConfirm.equals(stPassword)){

                    Toast.makeText(RegisterActivity.this, "Password Not Matched !Enter Correct Password", Toast.LENGTH_SHORT).show();
                }

                else {

                    SharedHelper.putKey(getApplicationContext(), AppConstats.USERMOBILE, stMobile);
                    SharedHelper.putKey(getApplicationContext(), AppConstats.USERNAME, stName);
                    SharedHelper.putKey(getApplicationContext(), AppConstats.USEREMAIL, stEmail);
                    SharedHelper.putKey(getApplicationContext(), AppConstats.USERPASSWORD, stPassword);
                    SharedHelper.putKey(getApplicationContext(), AppConstats.USERCPASSWORD, stConfirm);


                    send_Otp(stMobile);
                }

            }
        });
    }



    public void send_Otp(String stMobile){

        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,RegisterActivity.this);

        StringRequest request= new StringRequest(Request.Method.POST, API.genrate_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                   dialog.hideDialog();
                Log.e("RegisterActivity", "onResponse: " +response);


                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if (jsonObject.has("result")){

                        String result=jsonObject.getString("result");

                        if (result.equals("Otp Sent Successfully")){

                            SharedHelper.putKey(getApplicationContext(), AppConstats.OTP, jsonObject.getString("otp"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERID, jsonObject.getString("id"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERMOBILE, jsonObject.getString("phone_number"));
                            startActivity(new Intent(RegisterActivity.this, OtpVerifyActivity.class));
                            finish();
                        }

                    }

                } catch (JSONException e) {
                    Log.e("tyhrtyh", "onResponse: " +e);
                    dialog.hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tyhrtyh", "onErrorResponse: " +error);
                dialog.hideDialog();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String ,String>map=new HashMap<>();
                map.put("mobile",stMobile);
                return map;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(request);


    }


}