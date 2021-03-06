package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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
import com.areservices.databinding.ActivityOtpVerifyBinding;
import com.areservices.databinding.ActivityRegisterBinding;
import com.areservices.others.API;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.areservices.others.ProgressBarCustom.CustomDialog;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpVerifyActivity extends AppCompatActivity {
    ActivityOtpVerifyBinding binding;
    String strPinView = "", getOtp = "", getMobile = "";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getOtp = SharedHelper.getKey(getApplicationContext(), AppConstats.OTP);
        getMobile = SharedHelper.getKey(getApplicationContext(), AppConstats.USERMOBILE);

        queue = SingletonRequestQueue.getInstance(this).getRequestQueue();


        binding.txResend.setText("RESEND OTP");

        binding.txResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend_otp();
            }
        });


        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPinView = binding.pinView.getText().toString().trim();
                if (TextUtils.isEmpty(strPinView)) {

                } else if (strPinView.length() < 6) {

                    Toast.makeText(OtpVerifyActivity.this, "Enter 6 dgit", Toast.LENGTH_SHORT).show();
                } else if (!strPinView.equals(getOtp)) {

                    Toast.makeText(OtpVerifyActivity.this, "You entered wrong otp", Toast.LENGTH_SHORT).show();

                } else {

                    verify_otp();
                }

            }
        });


    }

    private void verify_otp() {

        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,OtpVerifyActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, API.otp_verification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             dialog.hideDialog();
                Log.e("dfgvfdgb", "onResponse: " +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("result")) {
                        String result = jsonObject.getString("result");
                        if (result.equals("otp verify  successfully")) {
                            String data = jsonObject.getString("data");
                            JSONObject object=new JSONObject(data);

                            registration();
                        }

                    }


                } catch (JSONException e) {
                    Log.e("fgfdg", "e: " + e);
                    dialog.hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("fgfdg", "onErrorResponse: " + error);
                dialog.hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("mobile", getMobile);
                map.put("otp", strPinView);
                return map;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        queue.add(request);


    }

    private void resend_otp() {

        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,OtpVerifyActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, API.resend_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hideDialog();
                Log.e("dfgvfdgb", "onResponse: " +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("result")) {
                        String result = jsonObject.getString("result");
                        if (result.equals("Otp Sent Successfully")) {
                            timer();


                        }

                    }


                } catch (JSONException e) {
                    Log.e("fgfdg", "e: " + e);
                    dialog.hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("fgfdg", "onErrorResponse: " + error);
                dialog.hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("mobile", getMobile);
                return map;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        queue.add(request);


    }

    private void timer(){
        binding.txResend.setText("Otp Sent Successfully");

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {

                binding.txResend.setText("Resend Code : " + millisUntilFinished / 1000);
                binding.txResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OtpVerifyActivity.this, "wait for "+ millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            public void onFinish() {

                binding.txResend.setText("RESEND OTP");
                binding.txResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resend_otp();
                    }
                });
            }

        }.start();

    }











    private void registration() {


        String getName = SharedHelper.getKey(getApplicationContext(), AppConstats.USERNAME);
        String getEmail  = SharedHelper.getKey(getApplicationContext(), AppConstats.USEREMAIL);
        String getPassword  = SharedHelper.getKey(getApplicationContext(), AppConstats.USERPASSWORD);
        String getCPassword  = SharedHelper.getKey(getApplicationContext(), AppConstats.USERCPASSWORD);
        String getID = SharedHelper.getKey(getApplicationContext(), AppConstats.USERID);

        StringRequest request = new StringRequest(Request.Method.POST, API.register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ggfdg", "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("result")) {
                        String result = jsonObject.getString("result");

                        if (result.equals("Registered successfully")) {


                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERID, jsonObject.getString("id"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERNAME, jsonObject.getString("name"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USEREMAIL, jsonObject.getString("email"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERMOBILE, jsonObject.getString("phone_number"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERPASSWORD, jsonObject.getString("password"));


                            startActivity(new Intent(OtpVerifyActivity.this, MainActivity.class));
                            finish();
                        }
                        else {

                            new StyleableToast
                                    .Builder(OtpVerifyActivity.this)
                                    .text(jsonObject.getString("result"))
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.parseColor("#d50000"))
                                    .show();

                        }

                    }

                } catch (JSONException e) {
                    Log.e("ryhtrhy", "onResponse: " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ryhtrhy", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", getName);
                map.put("email", getEmail);
                map.put("password", getPassword);
                map.put("confirm_password", getCPassword);
                map.put("id", getID);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(request);


    }
}