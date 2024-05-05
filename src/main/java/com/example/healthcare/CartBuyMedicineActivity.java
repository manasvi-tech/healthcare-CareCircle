package com.example.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartBuyMedicineActivity extends AppCompatActivity {
    HashMap<String,String> item;
    ArrayList<HashMap<String,String>> list;
    SimpleAdapter sa;
    DatePickerDialog datePickerDialog;

    private Button dateButton,btnCheckout,btnBack;
    private String[][] packages = {};
    TextView tvTotal;
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart_buy_medicine);

        dateButton = findViewById(R.id.buttonBMCartDate);

        btnCheckout = findViewById(R.id.buttonBMCartCheckout);
        btnBack = findViewById(R.id.buttonBMCartBack);
        tvTotal = findViewById(R.id.BMCost);
        ls = findViewById(R.id.ListBMViewCart);

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });



        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username","").toString();

        Database db = new Database(getApplicationContext(),"healthcare",null,1);
        float totalAmount = 0;
        ArrayList<String> dbData = db.getCartData(userName,"medicine");
        Toast.makeText(this,dbData.toString(),Toast.LENGTH_SHORT).show();

        packages = new String[dbData.size()][];
        for(int i =0;i<packages.length;i++){
            packages[i] = new String[5];  // 5 columns in each row because we have 5 parameters in multi-line
        }

        for(int i=0;i<dbData.size();i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$")); //splitting the string that we get on basis of dollar symbol
            packages[i][0]=strData[0];
            packages[i][4]="Cost "+strData[1]+"/-";
            totalAmount = totalAmount+Float.parseFloat(strData[1]);
        }

        tvTotal.setText(+totalAmount+"/-");

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

        //mapping the data into the multi-line layout using adapter
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );

        ls.setAdapter(sa);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartBuyMedicineActivity.this,LabTestActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartBuyMedicineActivity.this,BuyMedicineBookActivity.class);
                it.putExtra("price",tvTotal.getText());
                it.putExtra("date",dateButton.getText());


                startActivity(it);
            }
        });

    }
    private void initDatePicker() {
        // Listener to handle date selection
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Note: monthOfYear is zero-based, so we add 1 to it
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                dateButton.setText(selectedDate);
            }
        };

        // Get current date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog with current date and set minimum date to tomorrow
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

        // Calculate minimum date (tomorrow)
        cal.add(Calendar.DAY_OF_MONTH, 1); // Add one day to current date
        long minDate = cal.getTimeInMillis();

        // Set minimum date in DatePickerDialog
        datePickerDialog.getDatePicker().setMinDate(minDate);
    }
}