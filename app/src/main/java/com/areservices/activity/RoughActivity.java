package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.areservices.R;
import com.areservices.adapter.MyAdapter;
import com.areservices.model.StateVO;
import com.areservices.others.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoughActivity extends AppCompatActivity {


    ArrayList<StateVO> arrayListServiceTypeId;
    ArrayList<StateVO> arrayListServiceType;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rough);

        spinner =findViewById(R.id.spinner);
        spinner.setSelection(0, true);

        show_serviceType();
    }


    private void show_serviceType() {
        AndroidNetworking.post("https://maestrosinfotech.org/are_services/api/process.php?action=show_service_type")
                .addBodyParameter("id", "2")
                .setTag("Choose")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        arrayListServiceTypeId=new ArrayList<>();
                        arrayListServiceType=new ArrayList<>();

                        try {
                            if (response.getString("status").equals("true")){

                                JSONArray jsonArray=new JSONArray(response.getString("data"));
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    StateVO stateVO = new StateVO();
                                    stateVO.setTitle(jsonObject.getString("title"));
                                    stateVO.setSelected(false);
                                    arrayListServiceType.add(stateVO);





                                    }



                                MyAdapter myAdapter = new MyAdapter(RoughActivity.this, 0,
                                        arrayListServiceType);
                                spinner.setAdapter(myAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}