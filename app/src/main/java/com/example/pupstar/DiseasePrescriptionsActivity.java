package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiseasePrescriptionsActivity extends AppCompatActivity {

    private TextView desc;
    private CardView btnNext;
    private ImageView btnBack;

    private String details = "";
    ArrayList<String> medications_array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_prescriptions);

        Bundle extras= getIntent().getExtras();
        if(extras != null){
            medications_array = extras.getStringArrayList("medications");
        }

        desc = (TextView) this.findViewById(R.id.desc);
        btnNext = (CardView) this.findViewById(R.id.btnNext);

        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        for(int i=0; i<medications_array.size(); i++){
            details = details + medications_array.get(i) + "\n";
        }
        desc.setText(details);

    }
}