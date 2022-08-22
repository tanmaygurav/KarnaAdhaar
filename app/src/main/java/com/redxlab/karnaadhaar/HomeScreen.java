package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    private static final String TAG="Home Screen";
    private TextView homeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        homeScreen=findViewById(R.id.fallSimulationBTN);

        homeScreen.setOnClickListener(v->{
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),FallDetected.class));
                }
            }, 10000);

        });

    }
}