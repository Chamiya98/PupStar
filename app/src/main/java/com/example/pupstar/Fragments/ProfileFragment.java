package com.example.pupstar.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pupstar.AddDogDetailsActivity;
import com.example.pupstar.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private Spinner genderType;
    private ImageView profileImage;

    private ArrayList<String> genderItemsArray = new ArrayList<String>();
    private ArrayAdapter<String> genderAdapter;

    private String selectedGenderType = "";

    private static final int PICK_IMAGE = 100;
    private Uri imageUri = Uri.EMPTY;
    private Bitmap bitmap = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        genderType = (Spinner) view.findViewById(R.id.genderType);

        profileImage = (ImageView) view.findViewById(R.id.profileImage);

        genderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedGenderType = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        genderItemsArray.add("Male");
        genderItemsArray.add("Female");

        genderAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_row, genderItemsArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genderType.setAdapter(genderAdapter);
        genderType.setSelection(0);

        return view;
    }

    private void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            try {
                bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);

//                try {
//                    uploadProfileImage();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

//    private void uploadProfileImage() throws JSONException {
//
//        if (bitmap != null) {
//
//            String URL = API.USER_API + "/upload_breed";
//
//            String image = getStringImage(bitmap);
//            HashMap<String,String> params =  new HashMap<>();
//            params.put("image", image);
////            params.put("user_id", Preferences.LOGGED_USER_ID);
//            JSONObject parameter =  new JSONObject(params);
//            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//
//                    try {
//
//                        String status = response.getString("status");
//                        String msg = response.getString("msg");
//
//                        Toast.makeText(AddDogDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
//
//                        bitmap = null;
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(AddDogDetailsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            RequestQueue queue = Volley.newRequestQueue(this);
//            queue.add(jsonObject);
//
//        } else {
//            Toast.makeText(AddDogDetailsActivity.this, "Select profile image.", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

}