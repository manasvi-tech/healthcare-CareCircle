package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LabTestBookActivity extends AppCompatActivity {

    EditText edFullName,edAddress,edContact,edpinCode;
    Button buttonBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        edFullName = findViewById(R.id.editTextLTBFullName);
        edAddress = findViewById(R.id.editTextLTBAddress);
        edContact = findViewById(R.id.editTextLTBNumber);
        edpinCode = findViewById(R.id.editTextLTBPinCode);
        buttonBooking = findViewById(R.id.buttonLTBBook);

        Intent it = getIntent();
        String[] price = it.getStringExtra("price").toString().split(java.util.regex.Pattern.quote("/"));
        String date = it.getStringExtra("date");
        String time = it.getStringExtra("time");

        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();

                Database db = new Database(getApplicationContext(),"healthcare",null,1);
                db.addOrder(username,edFullName.getText().toString(),edAddress.getText().toString(),edContact.getText().toString(),Integer.parseInt(edpinCode.getText().toString()),date.toString(),time.toString(),"lab");
                db.removeCart(username,"lab");

                Toast.makeText(getApplicationContext(),"Your order has been succesuflly placed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
            }
        });





    }

}