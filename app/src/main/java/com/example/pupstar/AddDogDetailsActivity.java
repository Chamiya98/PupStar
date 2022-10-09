package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.prefs.Preferences;

public class AddDogDetailsActivity extends AppCompatActivity {

    private Button btnNext, btnResetImage, btnSave;
    private LinearLayout detailView, detectionView, selectDetectionImage;
    private ImageView selectedImage, petImage, btnBack;
    private Spinner petType, genderType;
    private TextView detectedTitle;
    private EditText dob;

    private ArrayList<String> petsItemsArray = new ArrayList<String>();
    private ArrayAdapter<String> petsAdapter;

    private ArrayList<String> genderItemsArray = new ArrayList<String>();
    private ArrayAdapter<String> genderAdapter;

    private String selectedPetType = "", selectedGenderType = "", selectedDob = "";

    private static final int PICK_IMAGE = 100;
    private Uri imageUri = Uri.EMPTY;
    private Bitmap bitmap = null;

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog_details);

        btnResetImage = (Button) findViewById(R.id.btnResetImage);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSave = (Button) findViewById(R.id.btnSave);

        detectedTitle = (TextView) findViewById(R.id.detectedTitle);

        dob = (EditText) findViewById(R.id.dob);

        detailView = (LinearLayout) findViewById(R.id.detailView);
        detectionView = (LinearLayout) findViewById(R.id.detectionView);
        selectDetectionImage = (LinearLayout) findViewById(R.id.selectDetectionImage);

        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        petImage = (ImageView) findViewById(R.id.petImage);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        petType = (Spinner) findViewById(R.id.petType);
        genderType = (Spinner) findViewById(R.id.genderType);

        detectionView.setVisibility(View.VISIBLE);
        detailView.setVisibility(View.GONE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        Show calender
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

//        Set some option to text layouts
        dob.setEnabled(true);
        dob.setTextIsSelectable(true);
        dob.setFocusable(false);
        dob.setFocusableInTouchMode(false);

//        Onclick for show date picker
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddDogDetailsActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detectionView.setVisibility(View.GONE);
                detailView.setVisibility(View.VISIBLE);

            }
        });

        petType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedPetType = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        selectDetectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }

        });

        petsItemsArray.add("Choose your pet type");
        petsItemsArray.add("Dog");

        petsAdapter = new ArrayAdapter<String>(AddDogDetailsActivity.this,
                R.layout.spinner_row, petsItemsArray);
        petsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        petType.setAdapter(petsAdapter);
        petType.setSelection(0);

        genderItemsArray.add("Male");
        genderItemsArray.add("Female");

        genderAdapter = new ArrayAdapter<String>(AddDogDetailsActivity.this,
                R.layout.spinner_row, genderItemsArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genderType.setAdapter(genderAdapter);
        genderType.setSelection(0);

    }

    //    Method for show date on text box
    private void updateDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(calendar.getTime()));
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
                selectedImage.setImageBitmap(bitmap);
                petImage.setImageBitmap(bitmap);

                try {
                    uploadProfileImage();
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

            String URL = API.BASE_URL + "/breedmain";

            String image = getStringImage(bitmap);
            //String image = selectedImage;
            HashMap<String, String> params = new HashMap<>();
            params.put("file", image);
            //params.put("user_id", Preferences.LOGGED_USER_ID);
            JSONObject parameter = new JSONObject(params);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        //String status = response.getString("status");
                        String breed = response.getString("message");

                        Toast.makeText(AddDogDetailsActivity.this, breed, Toast.LENGTH_SHORT).show();
                        System.out.println("Breed is :" +breed);
                        detectedTitle.setText(breed);
                        bitmap = null;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddDogDetailsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });


            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObject);

        } else {
            Toast.makeText(AddDogDetailsActivity.this, "Select profile image.", Toast.LENGTH_SHORT).show();
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

}