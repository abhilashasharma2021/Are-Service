package com.areservices.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.areservices.MainActivity;
import com.areservices.R;
import com.areservices.activity.ForgotPasswordActivity;
import com.areservices.activity.SingletonRequestQueue;
import com.areservices.adapter.ChooseServiceAdapter;
import com.areservices.adapter.SliderAdapterExample;
import com.areservices.adapter.TopRatedServiceAdapter;
import com.areservices.databinding.FragmentHomeBinding;
import com.areservices.model.SliderModel;
import com.areservices.model.TopRatedServiceModel;
import com.areservices.others.API;
import com.areservices.others.ProgressBarCustom.CustomDialog;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFrag extends Fragment {
    FragmentHomeBinding binding;
    View view;
    RequestQueue queue;
    List<SliderModel> listOfSlider = new ArrayList<>();
    ArrayList<TopRatedServiceModel>serviceList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();

        queue = SingletonRequestQueue.getInstance(getActivity()).getRequestQueue();


        binding.imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider.setIndicatorSelectedColor(getResources().getColor(R.color.blue));
        binding.imageSlider.setIndicatorUnselectedColor(Color.WHITE);
        binding.imageSlider.setScrollTimeInSec(3);
        binding.imageSlider.startAutoCycle();



        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerlayout.openDrawer(GravityCompat.START);
            }
        });


        layoutManager=new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
        binding.rvService.setLayoutManager(layoutManager);
        TopRatedServiceAdapter adapter=new TopRatedServiceAdapter(getActivity(),serviceList);
        binding.rvService.setAdapter(adapter);

        topRated_Service();
        onBack(view);
        showSlider();
        return view;
    }

    private void showSlider() {
        CustomDialog dialog = new CustomDialog();
        dialog.showDialog(R.layout.progress_layout, getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, API.show_banner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("gfdghf", "response: " + response);
                dialog.hideDialog();
                listOfSlider = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("status")) {

                        String status = jsonObject.getString("status");

                        if (status.equals("true")) {
                            String data = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                SliderModel sliderModel = new SliderModel();
                                sliderModel.setImageBanner(object1.getString("image"));
                                sliderModel.setPath(object1.getString("path"));
                                /*  sliderModel.setPath(object1.getString("title"));*/
                                listOfSlider.add(sliderModel);

                            }
                            SliderAdapterExample sliderAdapter = new SliderAdapterExample(listOfSlider, getActivity());
                            binding.imageSlider.setSliderAdapter(sliderAdapter);
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


    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Do you want to exit?");
                    builder.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialogInterface, int i) {
                            getActivity().finishAffinity();
                        }
                    }).setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                    return true;
                }
                return false;
            }
        });
    }

    private void topRated_Service(){
    TopRatedServiceModel model=new TopRatedServiceModel("CAR",R.drawable.car);
        for (int i = 0; i <4 ; i++) {
            serviceList.add(model);
        }


    }
}