package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FindDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_doctor);

        CardView physician = findViewById(R.id.cardFDfamily);
        physician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctor.this,DoctorDetailActivity.class);
                // Passing extra information. giving title as family physician
                it.putExtra("title","Family Physician");
                startActivity(it);

            }
        });

        CardView dietician = findViewById(R.id.cardFDDietician);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctor.this,DoctorDetailActivity.class);
                it.putExtra("title","DIETICIAN");
                startActivity(it);
            }
        });

        CardView dentist = findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctor.this,DoctorDetailActivity.class);
                // Passing extra information. giving title as family physician
                it.putExtra("title","Dentist");
                startActivity(it);

            }
        });

        CardView surgeon = findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctor.this,DoctorDetailActivity.class);
                // Passing extra information. giving title as family physician
                it.putExtra("title","OPD Cosultant");
                startActivity(it);

            }
        });

        CardView cardiologist = findViewById(R.id.FDCardiologist);
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctor.this,DoctorDetailActivity.class);
                it.putExtra("title","CARDIOLOGIST");
                startActivity(it);
            }
        });

        CardView exit = findViewById(R.id.cardFDback);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDoctor.this,HomeActivity.class));
            }
        });

    }
}