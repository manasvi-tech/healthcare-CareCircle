package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLoginActivity extends AppCompatActivity {
    EditText password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_login);
        password = findViewById(R.id.editTextAdminLoginPassword);
        btnLogin = findViewById(R.id.buttonAdminLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("0246") ){
                    Toast.makeText(getApplicationContext(),"Welcome Admin",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminLoginActivity.this,AdminHomeActivity.class));
                } else{
                    Toast.makeText(getApplicationContext(),"Incorrect password",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}