package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    private Button signInBtn;
    private EditText email;
    private String name;
    private TextView getSignupPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInBtn=findViewById(R.id.idSignInBTN);
        email=findViewById(R.id.idEmailSignInET);
        getSignupPage=findViewById(R.id.idSignInPage);

        signInBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            if (email.getText().toString()==null){
                name = "Default Name";
            }else{
                name=email.getText().toString();
            }
            intent.putExtra("name",name);
            saveData();
            startActivity(intent);
        });

        getSignupPage.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this,SignUp.class);
            startActivity(intent);
        });
    }

    private void loadData() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//            fallDetected=sharedPreferences.getString("fallDetected", null);
            // after loading data we are displaying a toast message.
            Toast.makeText(this, "Loaded fallDetected from Shared preferences. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error while loading : "+e, Toast.LENGTH_LONG).show();
        }
    }

    private void saveData() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.apply();
            // after saving data we are displaying a toast message.
            Toast.makeText(this, "Saved fallDetected to Shared preferences. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error while saving : "+e, Toast.LENGTH_LONG).show();
        }
    }
}