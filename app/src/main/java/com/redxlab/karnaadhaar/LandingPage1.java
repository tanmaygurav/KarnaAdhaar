package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LandingPage1 extends AppCompatActivity {
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page1);

        next=findViewById(R.id.idNextBTN);

        next.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),LandingPage2.class));
        });
    }
}