package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView navigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigate=findViewById(R.id.idNavigate);

        navigate.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
        });
    }
}