package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DiseaseDetectionDashboardActivity extends AppCompatActivity {

    private Button btnDetect, btnPreRecords;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detection_dashboard);

        btnDetect = (Button) this.findViewById(R.id.btnDetect);
        btnPreRecords = (Button) this.findViewById(R.id.btnPreRecords);

        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiseaseDetectionDashboardActivity.this, DetectDiseaseActivity.class);
                startActivity(intent);
            }
        });

        btnPreRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiseaseDetectionDashboardActivity.this, PreviousDiseasesActivity.class);
                startActivity(intent);
            }
        });

    }
}