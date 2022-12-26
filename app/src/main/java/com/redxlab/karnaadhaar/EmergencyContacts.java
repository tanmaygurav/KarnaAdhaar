package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmergencyContacts extends AppCompatActivity {
    private EditText contactName,contactNumber;
    private TextView contactNameTV,contactNumberTV;
    private Button saveContactBTN;
    private String name,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        
        contactName=findViewById(R.id.idEmeContactName);
        contactNumber=findViewById(R.id.idEmeContactNumber);
        contactNameTV=findViewById(R.id.idEmeContactNameTV);
        contactNumberTV=findViewById(R.id.idEmeContactNumberTV);
        saveContactBTN=findViewById(R.id.idSaveContactBTN);

        loadData();
        saveContactBTN.setOnClickListener(v->{
            saveData();
        });
    }

    private void loadData() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            name=sharedPreferences.getString("Name", null);
            number=sharedPreferences.getString("Number", null);
            contactNumberTV.setText(number);
            contactNameTV.setText(name);
            // after loading data we are displaying a toast message.
            Toast.makeText(this, "Loaded Contact from Shared preferences. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error while loading : "+e, Toast.LENGTH_LONG).show();
        }
    }

    private void saveData() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Name", contactName.getText().toString());
            editor.putString("Number", contactNumber.getText().toString());
            editor.apply();
            // after saving data we are displaying a toast message.
            Toast.makeText(this, "Saved Contact to Shared preferences. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error while saving : "+e, Toast.LENGTH_LONG).show();
        }
    }
}