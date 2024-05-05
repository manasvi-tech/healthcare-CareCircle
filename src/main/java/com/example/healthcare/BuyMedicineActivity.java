package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {
    String[][] packages = {
            {"Paracetamol", "", "", "", "5.99"},
            {"Ibuprofen", "", "", "", "7.49"},
            {"Aspirin", "", "", "", "3.99"},
            {"Amoxicillin", "", "", "", "12.75"},
            {"Loratadine", "", "", "", "9.25"},
            {"Omeprazole", "", "", "", "7.99"},
            {"Simvastatin", "", "", "", "11.50"},
            {"Losartan", "", "", "", "14.00"},
            {"Levothyroxine", "", "", "", "16.99"}
    };

    String[] detailsArray = {
            "Paracetamol is a common pain reliever and fever reducer. It is often used to treat mild to moderate pain.\n" +
                    "It is available over-the-counter and is generally considered safe when taken as directed.",

            "Ibuprofen is a nonsteroidal anti-inflammatory drug (NSAID) used to reduce pain, inflammation, and fever.\n" +
                    "It is commonly used to relieve headaches, muscle aches, menstrual cramps, and arthritis pain.\n" +
                    "Ibuprofen should be taken with food to reduce the risk of stomach upset.",

            "Aspirin is used to reduce pain, inflammation, and fever. It also has blood-thinning properties and is used to prevent heart attacks and strokes.\n" +
                    "Low-dose aspirin is often prescribed for cardiovascular protection in certain individuals.\n" +
                    "Aspirin should be used with caution in children and adolescents due to the risk of Reye's syndrome.",

            "Amoxicillin is an antibiotic used to treat bacterial infections, including respiratory infections, ear infections, and urinary tract infections.\n" +
                    "It is effective against a wide range of bacteria and is one of the most commonly prescribed antibiotics.\n" +
                    "Amoxicillin is generally well-tolerated but may cause side effects such as nausea or diarrhea.",

            "Loratadine is an antihistamine used to relieve allergy symptoms such as sneezing, runny nose, and itchy or watery eyes.\n" +
                    "It is non-sedating and can be taken once daily for seasonal or perennial allergies.\n" +
                    "Loratadine is available over-the-counter and is considered safe for most people.",

            "Omeprazole is a proton pump inhibitor (PPI) used to reduce stomach acid production and treat conditions like heartburn, acid reflux, and ulcers.\n" +
                    "It is often prescribed for gastroesophageal reflux disease (GERD) and related digestive disorders.\n" +
                    "Omeprazole is usually taken once daily before a meal.",

            "Simvastatin is a statin medication used to lower cholesterol levels and reduce the risk of heart disease.\n" +
                    "It works by inhibiting cholesterol production in the liver and is often prescribed along with lifestyle changes.\n" +
                    "Simvastatin may cause muscle pain or liver abnormalities in some individuals.",

            "Losartan is an angiotensin II receptor blocker (ARB) used to treat high blood pressure (hypertension) and prevent stroke.\n" +
                    "It helps relax blood vessels and lower blood pressure, reducing the risk of cardiovascular events.\n" +
                    "Losartan should be taken regularly as directed by a healthcare provider.",

            "Levothyroxine is a thyroid hormone replacement used to treat hypothyroidism (underactive thyroid) and other thyroid conditions.\n" +
                    "It helps restore thyroid hormone levels in the body and is taken once daily on an empty stomach.\n" +
                    "Regular monitoring of thyroid function is necessary while taking levothyroxine."
    };

    ArrayList<HashMap<String,String>> list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    ListView ls;
    Button btnBack,btnCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_medicine);

        ls = findViewById(R.id.ListViewBM);
        btnBack = findViewById(R.id.buttonBMBack);
        btnCart = findViewById(R.id.buttonBMCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });
        list = new ArrayList<>();
        for(int i=0;i<packages.length;i++){
            item = new HashMap<String,String>();
            item.put("line1",packages[i][0]);
            item.put("line2",packages[i][1]);
            item.put("line3",packages[i][2]);
            item.put("line4",packages[i][3]);
            item.put("line5","Cash Fees "+packages[i][4]+"/-");

            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        ListView ls = findViewById(R.id.ListViewBM);
        ls.setAdapter(sa);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent it = new Intent(BuyMedicineActivity.this,BuyMedicineDetailsActivity.class);
                it.putExtra("text1",packages[i][0]);
                it.putExtra("text2",detailsArray[i]);
                it.putExtra("text3",packages[i][4]);
                startActivity(it);
            }
        });

    }
}