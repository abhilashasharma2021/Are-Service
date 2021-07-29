package com.areservices.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
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
import com.areservices.activity.SingletonRequestQueue;
import com.areservices.databinding.FragmentProfileListBinding;
import com.areservices.others.API;
import com.areservices.others.AppConstats.AppConstats;
import com.areservices.others.AppConstats.SharedHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ProfileListFrag extends Fragment {

FragmentProfileListBinding binding;
View view;
    private static final String IMAGE_DIRECTORY = "/directory";
    private int GALLERY = 1, CAMERA = 2;
    File f;
    String strDob="",StrFinalStatus="";
    RequestQueue queue;
    DatePickerDialog datePickerDialog;
    View radioButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProfileListBinding.inflate(getLayoutInflater(), container, false);
        view=binding.getRoot();

        queue= SingletonRequestQueue.getInstance(getActivity()).getRequestQueue();
        binding.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioButton = binding.radioGroup.findViewById(checkedId);
                int index = binding.radioGroup.indexOfChild(radioButton);

                Log.e("wedgdsgdf", index+"" );
                switch (index) {
                    case 0:
                        StrFinalStatus = "Male";
                        break;
                    case 1:
                        StrFinalStatus = "Female";
                        break;
                }
            }
        });


        binding.rlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                binding.txDob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                                strDob = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });



        show_Profile();



        return view;
    }

    public void showPictureDialog() {

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery();
                        break;

                    case 1:
                        captureFromCamera();
                        break;
                }

            }
        });

        builder.show();
    }


    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }

    public void captureFromCamera() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

                    Log.e("ProfileActivity", "path: " + path);
                    Log.e("ProfileActivity", "bitmap: " + bitmap);
                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    binding.profileImg.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            binding.profileImg.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");

            Log.e("ProfileActivity", "f: " + f);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("ProfileActivity", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void show_Profile(){
        String   userId = SharedHelper.getKey(getActivity(), AppConstats.USERID);
        StringRequest request=new StringRequest(Request.Method.POST, API.show_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.has("result")){
                        String result=jsonObject.getString("result");

                        if (result.equals("true")){
                            binding.etName.setText(jsonObject.getString("name"));
                            binding.txMobile.setText(jsonObject.getString("phone_number"));

                            if (jsonObject.getString("gender").equals("Male")){
                                binding.radioGroup.check(R.id.radioMale);
                            }else {
                                binding.radioGroup.check(R.id.radioFemale);
                            }

                            binding.txDob.setText(jsonObject.getString("DOB"));

                            if (!jsonObject.getString("image").isEmpty()){

                                try {
                                    Glide.with(getActivity()).load( jsonObject.getString("path")+ jsonObject.getString("image")).placeholder(R.drawable.image1).into(binding.profileImg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                        else {
                            Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    Log.e("ProfileFrag", "e: " +e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ProfileFrag", "error: " +error.getMessage());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String>map=new HashMap<>();
                map.put("user_id",userId);
                return map;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(request);




    }


}