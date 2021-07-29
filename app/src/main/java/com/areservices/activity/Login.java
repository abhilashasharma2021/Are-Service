package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.adapter.ChooseServiceAdapter;
import com.areservices.databinding.ActivityLogin2Binding;
import com.areservices.others.API;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.areservices.others.ProgressBarCustom.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    ActivityLogin2Binding binding;
    String stMobile = "", stPassword = "";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = SingletonRequestQueue.getInstance(this).getRequestQueue();

        binding.txNEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterActivity.class));
                finish();
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stMobile = binding.etMobile.getText().toString().trim();
                stPassword = binding.etPassword.getText().toString().trim();


                if (validation()) {
                    login();
                } else {
                    validation();
                }


            }
        });


        binding.txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }

    private Boolean validation() {

        Boolean boolen = false;

        if (binding.etMobile.getText().toString().isEmpty()) {
            binding.etMobile.setError("Registered Mobile Number Must Required");
        } else if (stMobile.length() < 10) {

            binding.etMobile.setError("Please enter atleast 10 digit mobile number");
        } else if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError("Registered password  Must Required");
        } else {
            boolen = true;

        }
        return boolen;

    }


    private void login() {
        CustomDialog dialog = new CustomDialog();
        dialog.showDialog(R.layout.progress_layout, Login.this);

        StringRequest request = new StringRequest(Request.Method.POST, API.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hideDialog();

                Log.e("Login", "response: " +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.has("result")) {

                        String result = jsonObject.getString("result");

                        if (result.equals("login successfully")) {

                            String data = jsonObject.getString("data");

                            JSONObject object = new JSONObject(data);

                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERID, object.getString("id"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERNAME, object.getString("name"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USEREMAIL, object.getString("email"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERMOBILE, object.getString("phone_number"));
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USERPASSWORD, object.getString("password"));

                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();

                        }


                    }


                } catch (JSONException e) {
                    Log.e("Login", "e: " + e);
                    dialog.hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hideDialog();
                Log.e("Login", "error: " + error);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", stMobile);
                map.put("password", stPassword);
                return map;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(request);


    }


}