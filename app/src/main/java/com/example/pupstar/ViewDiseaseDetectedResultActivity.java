package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewDiseaseDetectedResultActivity extends AppCompatActivity {

    private ImageView setImage, cardImage, btnBack;
    private CardView btnNext;
    private TextView disease, title, desc;

    private String diseaseName = "", diseaseDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_disease_detected_result);

        setImage = (ImageView) this.findViewById(R.id.setImage);
        cardImage = (ImageView) this.findViewById(R.id.cardImage);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnNext = (CardView) this.findViewById(R.id.btnNext);

        disease = (TextView) this.findViewById(R.id.disease);
        title = (TextView) this.findViewById(R.id.title);
        desc = (TextView) this.findViewById(R.id.desc);

        diseaseName = "Ringworm";
        diseaseDescription = "Description of the ringworm.";

        Uri imgUri = Uri.parse("https://a-z-animals.com/media/2022/05/ringworm-768x1024.jpg");
        Picasso.get().load(imgUri).into(setImage);

        Uri cardUri = Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1gOmMr39BaGnq-OWaO0zs1Q89IHFecN50KQ&usqp=CAU");
        Picasso.get().load(cardUri).into(cardImage);

        String tip = "oh! Your dog suffer with " + diseaseName;
        disease.setText(tip);
        title.setText(diseaseName);
        desc.setText(diseaseDescription);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDiseaseDetectedResultActivity.this, DiseasePrescriptionsActivity.class);
                startActivity(intent);
            }
        });

    }
}