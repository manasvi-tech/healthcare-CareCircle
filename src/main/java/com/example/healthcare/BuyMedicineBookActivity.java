package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class BuyMedicineBookActivity extends AppCompatActivity {
    EditText edFullName,edAddress,edContact,edpinCode;
    Button buttonBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edFullName = findViewById(R.id.editTextBMBFullName);
        edAddress = findViewById(R.id.editTextBMBAddress);
        edContact = findViewById(R.id.editTextBMBNumber);
        edpinCode = findViewById(R.id.editTextBMBPinCode);
        buttonBooking = findViewById(R.id.buttonBMBBook);


        Intent it = getIntent();
        String[] price = it.getStringExtra("price").toString().split(java.util.regex.Pattern.quote("/"));
        String date = it.getStringExtra("date");
        Toast.makeText(this,"price: "+price[0]+"date"+date,Toast.LENGTH_SHORT).show();

        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();

                Database db = new Database(getApplicationContext(),"healthcare",null,1);
                db.addOrder(username,edFullName.getText().toString(),edAddress.getText().toString(),edContact.getText().toString(),Integer.parseInt(edpinCode.getText().toString()),date.toString(),"","medicine");
                db.removeCart(username,"medicine");
//
                Toast.makeText(getApplicationContext(),"Your order has been succesuflly placed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
            }
        });

    }
}