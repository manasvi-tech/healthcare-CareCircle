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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CartLabActivity extends AppCompatActivity {
    HashMap<String,String> item;
    ArrayList<HashMap<String,String>> list;
    SimpleAdapter sa;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private Button dateButton,timeButton,btnCheckout,btnBack;
    private String[][] packages = {};
    TextView tvTotal;
    ListView ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        dateButton = findViewById(R.id.buttonCartDate);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonCartCheckout);
        btnBack = findViewById(R.id.buttonCartBack);
        tvTotal = findViewById(R.id.textView3);
        ls = findViewById(R.id.ListViewCartDetails);

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username","").toString();

        Database db = new Database(getApplicationContext(),"healthcare",null,1);
        float totalAmount = 0;
        ArrayList<String> dbData = db.getCartData(userName,"lab");


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
                startActivity(new Intent(CartLabActivity.this,LabTestActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartLabActivity.this,LabTestBookActivity.class);
                it.putExtra("price",tvTotal.getText());
                it.putExtra("date",dateButton.getText());
                it.putExtra("time",timeButton.getText());
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

    private void initTimePicker() {
        // Listener to handle time selection
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Format the selected time as HH:mm (24-hour format)
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                timeButton.setText(selectedTime);
            }
        };

        // Get current time
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY); // Use HOUR_OF_DAY for 24-hour format
        int minute = cal.get(Calendar.MINUTE);

        // Create TimePickerDialog with current time and set 24-hour format
        timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minute, true); // true for 24-hour format
    }
}