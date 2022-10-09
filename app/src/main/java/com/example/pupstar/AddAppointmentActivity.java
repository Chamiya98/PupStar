package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddAppointmentActivity extends AppCompatActivity {

    private Spinner pet;
    private ImageView petImage, btnBack;
    private EditText details;

    private ArrayList<Pet> petItemsArray = new ArrayList<Pet>();
    private ArrayList<String> stringItemsArray = new ArrayList<String>();
    private ArrayAdapter<String> petAdapter;

    private String selectedPet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        pet = (Spinner) this.findViewById(R.id.pet);
        petImage = (ImageView) this.findViewById(R.id.petImage);
        btnBack = (ImageView) this.findViewById(R.id.btnBack);
        details = (EditText) this.findViewById(R.id.details);

        pet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedPet = String.valueOf(petItemsArray.get(position).getId());
                String strImage = String.valueOf(petItemsArray.get(position).getImage());

                Uri imgUri = Uri.parse(strImage);
                Picasso.get().load(imgUri).into(petImage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        petItemsArray.add(new Pet("1", "Shadow", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        stringItemsArray.add("Shadow");

        petItemsArray.add(new Pet("2", "Puppy", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));
        stringItemsArray.add("Puppy");

        petAdapter = new ArrayAdapter<String>(AddAppointmentActivity.this,
                R.layout.spinner_row, stringItemsArray);
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        pet.setAdapter(petAdapter);
        pet.setSelection(0);

    }
}

class Pet
{

    String id, name, image;

    public Pet(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}