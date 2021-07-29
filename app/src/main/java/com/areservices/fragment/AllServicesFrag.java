package com.areservices.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.activity.SingletonRequestQueue;
import com.areservices.adapter.ChooseServiceAdapter;
import com.areservices.databinding.FragmentAllServicesBinding;
import com.areservices.model.ChooseServiceModel;
import com.areservices.others.API;
import com.areservices.others.ProgressBarCustom.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllServicesFrag extends Fragment {

    FragmentAllServicesBinding binding;
    View view;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ChooseServiceModel>serviceList=new ArrayList<>();
    RequestQueue queue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAllServicesBinding.inflate(getLayoutInflater(),container, false);
        view = binding.getRoot();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        queue = SingletonRequestQueue.getInstance(getActivity()).getRequestQueue();



        chooseList();

        return view;

    }


    private void chooseList(){

        CustomDialog dialog = new CustomDialog();
        dialog.showDialog(R.layout.progress_layout, getActivity());

        StringRequest request=new StringRequest(Request.Method.POST, API.show_service, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("gfdghf", "response: " +response);
                dialog.hideDialog();
                serviceList=new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("status")) {

                        String status = jsonObject.getString("status");

                        if (status.equals("true")) {
                            String data = jsonObject.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                ChooseServiceModel model = new ChooseServiceModel();
                                model.setCatId(object1.getString("id"));
                                model.setImage(object1.getString("image"));
                                model.setName(object1.getString("title"));
                                model.setPath(object1.getString("path"));
                                serviceList.add(model);

                            }
                            layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                            binding.rvService.setLayoutManager(layoutManager);
                            ChooseServiceAdapter adapter=new ChooseServiceAdapter(getActivity(),serviceList);
                            binding.rvService.setAdapter(adapter);
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
                Log.e("dfgfd", "v: " + error);
                dialog.hideDialog();
            }
        }) {

        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(request);

    }
}