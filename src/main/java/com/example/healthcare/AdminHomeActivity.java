package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class AdminHomeActivity extends AppCompatActivity {
    private String[][] appointment_details;
    ArrayList list;
    HashMap<String,String> item;
    SimpleAdapter sa;
    ListView ls;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnBack = findViewById(R.id.buttonAdminHomeBack);
        ls = findViewById(R.id.ListViewAppointmentDetails);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, GetStartedActivity.class));
            }
        });

        Database db = new Database(getApplicationContext(),"healthcare",null,1);
        ArrayList dbData = db.getAppointmentDetails();
        appointment_details = new String[dbData.size()][];

        for(int i=0;i<appointment_details.length;i++){
            appointment_details[i] = new String[5];
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            appointment_details[i][0] = strData[0];
            appointment_details[i][1] = strData[1];
            if(strData[6].compareTo("medicine")==0){
                appointment_details[i][3]="Del:"+strData[4];
            } else{
                appointment_details[i][3]="Del:"+strData[4]+" "+strData[5];
            }

            appointment_details[i][4]=strData[6];

        }

        list = new ArrayList<>();
        for(int i=0;i<appointment_details.length;i++){
            item = new HashMap<String,String>();
            item.put("line1",appointment_details[i][0]);
            item.put("line2",appointment_details[i][1]);
            item.put("line3",appointment_details[i][2]);
            item.put("line4",appointment_details[i][3]);
            item.put("line5",appointment_details[i][4]);

            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );




        ls.setAdapter(sa);
    }
}