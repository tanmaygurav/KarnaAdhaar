package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class BatteryStats extends AppCompatActivity {
    private ImageView gif;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_stats);

        gif=findViewById(R.id.idBatteryAnimation);

        Glide.with(context).load(R.drawable.batteryanimation).into(new DrawableImageViewTarget(gif));
    }
}