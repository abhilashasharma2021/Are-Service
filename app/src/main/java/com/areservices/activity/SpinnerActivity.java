package com.areservices.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.areservices.R;
import com.areservices.adapter.MyAdapter;
import com.areservices.model.StateVO;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity {
    private MultiSpinner spinner;
    private ArrayAdapter<String> adapter;

    ArrayList<String> arrayListServiceTypeId;
    ArrayList<String> arrayListServiceType;
    String stType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

       /* adapter.add("Item1");
        adapter.add("Item2");
        adapter.add("Item3");
        adapter.add("Item4");
        adapter.add("Item5");*/

        // get spinner and set adapter
        spinner = (MultiSpinner) findViewById(R.id.spinnerMulti);
        show_serviceType();
    }

    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        @Override
        public void onItemsSelected(boolean[] selected) {
            StringBuilder stringbuilder = new StringBuilder();
            for (int i = 0; i < adapter.getCount(); i++) {

                if (selected[i]) {

                    arrayListServiceTypeId.add(String.valueOf(adapter.getItem(i).toString())); //get the list of selected items

                    if (stringbuilder.length() == 0) {
                        stringbuilder.append(arrayListServiceTypeId.get(i));
                        Log.e("dsg", "jtgjytj: " +stringbuilder);
                    } else {
                stType= String.valueOf(stringbuilder.append(",").append(arrayListServiceTypeId.get(i)));

                        Log.e("dfgfdgbfd", "stType: " +stType);


                    }
                   /* textView.setText(String.valueOf(stringbuilder));*/
                } else {
                    arrayListServiceTypeId.remove(adapter.getItem(i).toString());
                }
            }

        }
    };


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


                                    arrayListServiceTypeId.add(jsonObject.getString("id"));
                                    arrayListServiceType.add(jsonObject.getString("title"));







                                }

                                adapter = new ArrayAdapter<>(SpinnerActivity.this, android.R.layout.simple_list_item_1, arrayListServiceType);
                                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                                spinner.setAdapter(adapter, false, onSelectedListener);


                                // set initial selection
                                boolean[] selectedItems = new boolean[adapter.getCount()];
                                // selectedItems[1] = true; // select second item

                                spinner.setSelected(selectedItems);

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
