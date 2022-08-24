package com.redxlab.karnaadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Button signInBtn;
    private EditText email,username,password;
    private String name;
    private TextView getSignINPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signInBtn=findViewById(R.id.idSignUpBTN);
        email=findViewById(R.id.idEmailSignUpET);
        getSignINPage=findViewById(R.id.idSignUpPage);
        username=findViewById(R.id.idNameSignUpET);
        password=findViewById(R.id.idPasswordSignInET);

        signInBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this, MainActivity.class);
            if (username.getText().toString()==null){
                name = "Username";
            }else{
                name=username.getText().toString();
                saveData();
            }
            intent.putExtra("nameKey",name);
            startActivity(intent);
        });

        getSignINPage.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this,SignIn.class);
            startActivity(intent);
        });
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