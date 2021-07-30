package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.areservices.R;
import com.areservices.databinding.ActivityCustomerCarDetailsBinding;
import com.areservices.databinding.ActivityServiceMenListBinding;
import com.areservices.others.API;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerSendDetailsActivity extends AppCompatActivity {
ActivityCustomerCarDetailsBinding binding;
    ArrayList<String> arrayListServiceTypeId;
    ArrayList<String> arrayListServiceType;
    ArrayAdapter<String> adapterType;
    String stType="",stServiceId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCustomerCarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stServiceId = SharedHelper.getKey(getApplicationContext(), AppConstats.ServiceID);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stType = arrayListServiceTypeId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(CustomerCarDetailsActivity.this, ServiceMenListActivity.class));
            }
        });
    }



  /*  private void show_serviceType(){
        AndroidNetworking.post(API.BASEURL + API.show_color)
                    .addBodyParameter("product_id", strProdutId)
                    .addBodyParameter("model_id", Model)
                    .setTag("Choose")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.e("sdskfl", response.toString());

                            arrayListServiceType = new ArrayList<>();
                            arrayListServiceTypeId = new ArrayList<>();


                            try {

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    String id = jsonObject.getString("id");
                                    String product_id = jsonObject.getString("product_id");
                                    String color = jsonObject.getString("color");

                                    String color_info = jsonObject.getString("color_info");


                                    if (!color_info.equals("null")) {
                                        //  Log.e("ProductDetailsActivity", "Null nhi h: " +color_info);
                                        JSONObject object = new JSONObject(color_info);


                                        String id_new = object.getString("id");
                                        String color_new = object.getString("color");

                                        arrayListColorID.add(id_new);
                                        Log.e("dkjfkdj", id_new);
                                        arrayListColor.add(color_new);


                                    } else {
                                        //  Log.e("ProductDetailsActivity", "Null nhi h: " +color_info);
                                        arrayListColorID.add("");
                                        ;
                                        arrayListColor.add("Not Available");
                                    }


                                }

                                adaptercolor = new ArrayAdapter<>(ProductDetailsActivity.this, android.R.layout.simple_list_item_1, arrayListColor);
                                adaptercolor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                spin_color.setAdapter(adaptercolor);


                            } catch (JSONException e) {
                                Log.e("dsfkdsk", e.getMessage());
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("rtfrgf", anError.getMessage());
                        }
                    });


    }*/
}