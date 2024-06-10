package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText edUserName, edPassword;
    Button btn;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        edUserName=findViewById(R.id.editTextLoginUsername);
        edPassword=findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonAppDate);
        tv = findViewById(R.id.textViewNewUser);
        Database db = new Database(getApplicationContext(),"healthcare",null,1);

        edUserName.requestFocus();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString();
                String Passwrod = edPassword.getText().toString();
                if(userName.length()==0 || Passwrod.length()==0){
                    Toast.makeText(getApplicationContext(),"Please fill the required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.login(userName,Passwrod)==1){ // int type function 1 if a result is found 0 if not
                        Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();

                        //saving login detail in local memory. if we want to display the username in home page or somewhere
                        // shared preference concept is used in android
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", userName);
                        // To save our data with key and value
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));  // we are calling an explicit intent. Explicit intent is used for user-defined activity
            }
        });
    }
}