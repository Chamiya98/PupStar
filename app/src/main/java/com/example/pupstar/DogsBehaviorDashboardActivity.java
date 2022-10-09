package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DogsBehaviorDashboardActivity extends AppCompatActivity {

    private Button btnBehavior, btnPreRecords;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_behavior_dashboard);

        btnBehavior = (Button) this.findViewById(R.id.btnBehavior);
        btnPreRecords = (Button) this.findViewById(R.id.btnPreRecords);

        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBehavior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DogsBehaviorDashboardActivity.this, DetectBehaviorActivity.class);
                startActivity(intent);

            }
        });

        btnPreRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DogsBehaviorDashboardActivity.this, PreviousBehaviorsActivity.class);
                startActivity(intent);

            }
        });

    }
}