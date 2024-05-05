package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText edUserName, edEmail,edPassword,edConfirmPassword;
    Button btn;
    TextView tv;
    int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        edUserName=findViewById(R.id.editTextRegUsername);
        edEmail=findViewById(R.id.editTextAppAddress);
        edPassword=findViewById(R.id.editTextRegPassword);
        edConfirmPassword=findViewById(R.id.editTextAppFees);
        tv = findViewById(R.id.textViewNewUser);
        btn = findViewById(R.id.buttonAppVerifyEmail);
        Database db = new Database(getApplicationContext(),"healthcare",null,1);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }


        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirmPassword.getText().toString();

                if(userName.length()==0 || email.length()==0 || password.length()==0 || confirm.length()==0){
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.compareTo(confirm)==0){
                        if(isValid(password) ){

                            db.register(userName,email,password);
                            Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class)); //redirecting to the login page

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password must contain 8 digits, 1 special character and 1 digit", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"password and confirm password are not same",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public static boolean isValid(String passwordhere){ //if the password has a combination of one special character, a digit and one letter
        int f1=0,f2=0,f3=0;
        if(passwordhere.length()<8) return false;
        else{
            for(int i=0;i<passwordhere.length();i++){
                if(Character.isLetter(passwordhere.charAt(i))){
                    f1=1;
                }
            }
            for(int x =0;x<passwordhere.length();x++){
                if(Character.isDigit(passwordhere.charAt(x))){
                    f2=1;
                }
            }
            for(int r=0;r<passwordhere.length();r++){
                char c = passwordhere.charAt(r);
                if(c>=33 && c<=64 || c==64){
                    f3=1;
                }
            }
            if(f1==1 && f2==1 && f3==1){
                return true;
            }
            return false;
        }
    }
}