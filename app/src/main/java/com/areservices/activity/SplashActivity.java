package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.areservices.others.InternetConnection.InternetConnectionInterface;
import com.areservices.others.InternetConnection.InternetConnectivity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    RelativeLayout relative;
    Button click;
    Handler handler = new Handler(Looper.myLooper());
    String userID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        relative = findViewById(R.id.relative);
        click = findViewById(R.id.click);
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                handler.postDelayed(() -> {

                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getApplicationContext())) {

                        userID = SharedHelper.getKey(getApplicationContext(), AppConstats.USERID);
                        if (userID.equals("")) {
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    } else {
                        relative.setVisibility(View.VISIBLE);

                        click.setOnClickListener(view -> {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            Animatoo.animateShrink(SplashActivity.this);
                            finish();
                        });

                        Toast.makeText(SplashActivity.this, "Not Connected to internet", Toast.LENGTH_SHORT).show();
                    }


                }, 1500);


            }


            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).check();
    }

}
