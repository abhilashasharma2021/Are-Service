package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


                            startActivity(new Intent(OtpVerifyActivity.this, MainActivity.class));

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