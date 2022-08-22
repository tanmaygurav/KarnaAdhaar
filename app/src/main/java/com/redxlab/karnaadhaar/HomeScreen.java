package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    private static final String TAG="Home Screen";
    private TextView homeScreen,counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        homeScreen=findViewById(R.id.fallSimulationBTN);
        counter=findViewById(R.id.idCounter);

        homeScreen.setOnClickListener(v-> {
            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                    counter.setText(String.valueOf(millisUntilFinished / 1000));

                }

                public void onFinish() {
                    startActivity(new Intent(getApplicationContext(), FallDetected.class));
                }

            }.start();
        });

    }
}