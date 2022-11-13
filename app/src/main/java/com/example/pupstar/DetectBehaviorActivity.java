package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class DetectBehaviorActivity extends AppCompatActivity {

    private Button btnAnalyse, btnResetImage;
    private LinearLayout selectDetectionImage;
    private ImageView petImage, btnBack;
    private TextView detected_behavior_Title;

    private static final int PICK_IMAGE = 100;
    private Uri imageUri = Uri.EMPTY;
    private Bitmap bitmap = null;
    private ArrayList<String> dogsArray = new ArrayList<String>();
    private ArrayAdapter<String> dogsAdapter;
    private Spinner dogs;
    String SelectedDogId = "";
    String DetectedBehavior = "";
    String imgpre = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_behavior);

        btnResetImage = (Button) findViewById(R.id.btnResetImage);
        btnAnalyse = (Button) findViewById(R.id.btnAnalyse);

        detected_behavior_Title = (TextView) findViewById(R.id.detected_behavior_Title);

        selectDetectionImage = (LinearLayout) findViewById(R.id.selectDetectionImage);

        petImage = (ImageView) findViewById(R.id.petImage);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        //Spinner spinner = (Spinner) findViewById(R.id.Dogs_Spinner);
        dogs = (Spinner) findViewById(R.id.spinner_dogs);





        String URL = API.BASE_URL + "/getDoglist";
        //String URL_2 = API.BASE_URL + "/clinic_images";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        dogsArray.add("Select An Item");
                        //String[] glarr = {};
                        //System.out.println("Testing");
                        //String dogsArray1[] = new String[]
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arr = jsonObject.getJSONArray("message");
                            //String data = jsonObject.getString("message");
                            System.out.println(arr);

                            for (int i = 0; i < arr.length(); i++) {
                                String dogName = arr.getJSONArray(i).getString(1);
                                dogsArray.add(dogName);
                                //dogsArray1[0] = dogName;

                            }

                            dogsAdapter = new ArrayAdapter<String>(DetectBehaviorActivity.this,
                                    R.layout.spinner_row, dogsArray);
                            dogsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            dogs.setAdapter(dogsAdapter);
                            dogs.setSelection(0);

                            //dogsArray.add("Choose your pet type");
                            //dogsArray.add("Dog");




                            //dogsArray.add("test1");
                            //dogsAdapter = new ArrayAdapter<String>(DetectBehaviorActivity.this,
                                    //R.layout.spinner_row, dogsArray);
                            //dogsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            //dogs.setAdapter(dogsAdapter);
                            //dogs.setSelection(0);
                            //spinner.setAdapter(dogsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetectBehaviorActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


        };
        RequestQueue queue = Volley.newRequestQueue(DetectBehaviorActivity.this);
        queue.add(stringRequest);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        selectDetectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btnAnalyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetectBehaviorActivity.this, ViewBehaviorDetectedResultActivity.class);
                intent.putExtra("BitmapImage", bitmap);
                intent.putExtra("randomImage", imgpre);
                startActivity(intent);

                if (SelectedDogId == "Select An Item"){
                    System.out.println("Please Select the dog Fisrt !");
                }
                else {
                    try {
                        insertPastData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        dogs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SelectedDogId = (String) parent.getItemAtPosition(position);
                System.out.println("The Selected Dog is: " + SelectedDogId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            try {
                bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                petImage.setImageBitmap(bitmap);

                try {
                    uploadProfileImage();
                    //dogs.getSelectedItem()
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void uploadProfileImage() throws JSONException {

        if (bitmap != null) {

            String URL = API.BASE_URL + "/behaviormain";

            String image = getStringImage(bitmap);
            HashMap<String, String> params = new HashMap<>();
            params.put("file", image);
            //params.put("user_id", Preferences.LOGGED_USER_ID);
            JSONObject parameter = new JSONObject(params);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        //String status = response.getString("status");
                        String behaviour = response.getString("message");
                        String behaviortitle = response.getString("mood");
                        imgpre = response.getString("randomid");
                        System.out.println("behavior: " +behaviour);
                        System.out.println("behavior title: " +behaviortitle);
                        Toast.makeText(DetectBehaviorActivity.this, behaviour, Toast.LENGTH_SHORT).show();
                        detected_behavior_Title.setText(behaviour);
                        DetectedBehavior = behaviortitle;
                        System.out.println("Behavior is: "+ DetectedBehavior);
                        bitmap = null;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetectBehaviorActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });


            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObject);

        } else {
            Toast.makeText(DetectBehaviorActivity.this, "Select profile image.", Toast.LENGTH_SHORT).show();
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void insertPastData() throws JSONException {

            System.out.println(SelectedDogId);
            System.out.println(DetectedBehavior);

            String URL = API.BASE_URL + "/insertbehaviorPastData";

            //String image = getStringImage(bitmap);
            HashMap<String, String> params = new HashMap<>();
            params.put("dogname", SelectedDogId);
            params.put("behavior", DetectedBehavior);
            params.put("randomimgid", imgpre);
            //params.put("user_id", Preferences.LOGGED_USER_ID);
            JSONObject parameter = new JSONObject(params);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        //String status = response.getString("status");
                        String pastBehavior = response.getString("message");

                        Toast.makeText(DetectBehaviorActivity.this, pastBehavior, Toast.LENGTH_SHORT).show();
                        //detected_behavior_Title.setText(behaviour);
                        //bitmap = null;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetectBehaviorActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });


            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObject);

        }

    }

