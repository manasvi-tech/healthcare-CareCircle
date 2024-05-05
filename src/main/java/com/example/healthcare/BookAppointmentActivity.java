package com.example.healthcare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;


    private Button dateButton, timeButton, btnBook, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.tvAppBookAppointment);
        ed1 = findViewById(R.id.etAppFullName);
        ed2 = findViewById(R.id.etAppAddress);
        ed3 = findViewById(R.id.editText);
        ed4 = findViewById(R.id.etAppFees);
        timeButton = findViewById(R.id.buttonAppTime);
        dateButton = findViewById(R.id.buttonAppDate);
        btnBook = findViewById(R.id.buttonAppBook);
        btnBack = findViewById(R.id.buttonAppBack);

        initDatePicker();
        initTimePicker();

        ed1.setKeyListener(null);  //not editable
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        //datePicker
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //timePicker
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookAppointmentActivity.this,DoctorDetailActivity.class));

            }
        });



        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullName = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);

        ed1.setText(fullName);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Cons fees "+ fees+"/-");

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(getApplicationContext(),"healthcare",null,1);
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();

                if(db.checkAppointment(username,title+" => "+fullName,address,timeButton.getText().toString())==1){
                    Toast.makeText(getApplicationContext(),"Appointment already booked",Toast.LENGTH_LONG).show();
                } else{
                    db.addOrder(username,title+" => "+fullName,address,contact,0,dateButton.getText().toString(),timeButton.getText().toString(),"appointment");
                    Toast.makeText(getApplicationContext(),"Your order is placed succesfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BookAppointmentActivity.this,HomeActivity.class));
                }
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