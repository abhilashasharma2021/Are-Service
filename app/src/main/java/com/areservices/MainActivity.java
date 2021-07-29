package com.areservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.areservices.activity.AboutUsActivity;
import com.areservices.activity.PrivacyPolicyActivity;
import com.areservices.activity.SettingActivity;
import com.areservices.activity.SplashActivity;
import com.areservices.activity.TermsAndConditionActivity;
import com.areservices.fragment.AllServicesFrag;
import com.areservices.fragment.HomeFrag;
import com.areservices.fragment.ProfileListFrag;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.areservices.others.InternetConnection.InternetConnectionInterface;
import com.areservices.others.InternetConnection.InternetConnectivity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , NavigationView.OnNavigationItemSelectedListener {
public  static DrawerLayout drawerlayout;
NavigationView nav_view;
BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerlayout = findViewById(R.id.drawerlayout);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        nav_view = findViewById(R.id.nav_view);

        View headerView = nav_view.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framContainer, new HomeFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });


        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_setting){
                  startActivity(new Intent(MainActivity.this, SettingActivity.class));
                   Animatoo.animateSwipeRight(MainActivity.this);
                    drawerlayout.closeDrawer(GravityCompat.START);
                }
               /* else if (item.getItemId()==R.id.nav_category){
                    getSupportFragmentManager().beginTransaction().replace(R.id.framContainer, new AllServicesFrag()).commit();
                    drawerlayout.closeDrawer(GravityCompat.START);

                }
                else if (item.getItemId()==R.id.nav_about){
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    Animatoo.animateSwipeLeft(MainActivity.this);
                    drawerlayout.closeDrawer(GravityCompat.START);

                }*/

               /* else if (item.getItemId()==R.id.nav_contact){
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    drawerlayout.closeDrawer(GravityCompat.START);

                }*/
              /*  else if (item.getItemId()==R.id.nav_term){
                    startActivity(new Intent(MainActivity.this, TermsAndConditionActivity.class));
                    Animatoo.animateSlideLeft(MainActivity.this);
                    drawerlayout.closeDrawer(GravityCompat.START);

                }

                else if (item.getItemId()==R.id.nav_privacy){
                    startActivity(new Intent(MainActivity.this,PrivacyPolicyActivity.class));
                    Animatoo.animateSwipeRight(MainActivity.this);
                    drawerlayout.closeDrawer(GravityCompat.START);

                }*/

                else if (item.getItemId()==R.id.nav_logout){
                    dialogLogout();
                    drawerlayout.closeDrawer(GravityCompat.START);

                }

                return false;
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(this);

        InternetConnectionInterface connectivity = new InternetConnectivity();
        if (connectivity.isConnected(getApplicationContext())) {
            if (savedInstanceState==null){
                getSupportFragmentManager().beginTransaction().replace(R.id.framContainer,new HomeFrag()).commit();
            }
        } else {


            Toast.makeText(this, "Not connected to internet!!!", Toast.LENGTH_SHORT).show();

        }


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.framContainer,new HomeFrag()).commit();
                break;

            case R.id.nav_service:
                getSupportFragmentManager().beginTransaction().replace(R.id.framContainer,new AllServicesFrag()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.framContainer,new ProfileListFrag()).commit();

        }

        return true;
    }

    private void dialogLogout() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_logout_layout);
        RelativeLayout btn_yes = dialog.findViewById(R.id.relYes);
        RelativeLayout btn_no = dialog.findViewById(R.id.relNo);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedHelper.putKey(MainActivity.this, AppConstats.USERID, "");
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                 finish();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }
}