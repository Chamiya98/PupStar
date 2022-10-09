package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout help1, help2;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        help1 = (LinearLayout) this.findViewById(R.id.help1);
        help2 = (LinearLayout) this.findViewById(R.id.help2);

        btnNext = (Button) this.findViewById(R.id.btnNext);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        help1.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                help1.setAnimation(animation1);
                help1.setVisibility(View.GONE);

                help2.setVisibility(View.VISIBLE);
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                help2.setAnimation(animation3);

            }
        },4000);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}