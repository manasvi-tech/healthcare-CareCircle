package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailActivity extends AppCompatActivity {
    private String[][] doctor_details1 = {
            {"Doctor Name : Ajit Saste", "Hospital Address : Gurgaon", "Exp : 5yrs", "Mobile No: 9898989898", "600"},
            {"Doctor Name : Prasad Pawar", "Hospital Address : Delhi", "Exp : 15yrs", "Mobile No: 7898989898", "900"},
            {"Doctor Name : Swapnil Kale", "Hospital Address : Bhiwadi", "Exp : 8yrs", "Mobile No: 8898989898", "300"},
            {"Doctor Name : Deepak DeshMukh", "Hospital Address : Sector- 47, Gurgaon", "Exp : 6yrs", "Mobile No: 989890000", "800"},
            {"Doctor Name : Ashok Pandey", "Hospital Address : Kapriwas", "Exp : 7yrs", "Mobile No: 7798989898", "800"}
    };


    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Neelam Patil", "Hospital Address : Saket, Delhi", "Exp : 5yrs”, Mobile No:9898989898", "600"},
                    {"Doctor Name : Swati Pawar", "Hospital Address : Gururgam", "Exp : 15yrs”, Mobile No:7898989898", "900"},
                    {"Doctor Name : Neeraja Kale", "Hospital Address : Kapriwas", "Exp : 8yrs”, Mobile No:8898989898", "300"},
                    {"Doctor Name : Mayuri DeshMukh", "Hospital Address : Chandni Chowk", "Exp : 6yrs”, Mobile No:989890000", "800"},
                    {"Doctor Name : Minakshi Pandey", "Hospital Address : ISBT Delhi", "Exp : 7yrs”, Mobile No:7798989898", "8 00"},
            };

    private String[][] doctor_details3 = {
            {"Doctor Name : Seema Patil", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No: 9898989898", "600"},
            {"Doctor Name : Pankaj Parab", "Hospital Address : Kapriwas", "Exp : 15yrs", "Mobile No: 7898989898", "900"},
            {"Doctor Name : Monish Jain", "Hospital Address : Bilaspur", "Exp : 8yrs", "Mobile No: 8898989898", "300"},
            {"Doctor Name : Visha DeshMukh", "Hospital Address : Dilli Haat, Delhi", "Exp : 6yrs", "Mobile No: 989890000", "800"},
            {"Doctor Name : Shrikant Pandey", "Hospital Address : Iffco Metro Station", "Exp : 7yrs", "Mobile No: 7798989898", "800"}
    };


    private String[][] doctor_details4 = {
            {"Doctor Name : Nilesh Borate", "Hospital Address : Huda City Center", "Exp : 5yrs", "Mobile No: 9898989898", "600"},
            {"Doctor Name : Prasad Pawar", "Hospital Address : Delhi", "Exp : 15yrs", "Mobile No: 7898989898", "900"},
            {"Doctor Name : Swapan Lele", "Hospital Address : Pune", "Exp : 8yrs", "Mobile No: 8898989898", "300"},
            {"Doctor Name : Deepak Kumar", "Hospital Address : Rohini, Delhi", "Exp : 6yrs", "Mobile No: 9898900000", "800"},
            {"Doctor Name : Ashok Singh", "Hospital Address : Pritampura", "Exp : 7yrs", "Mobile No: 7798989898", "800"}
    };
    private String[][] doctor_details5 = {
            {"Doctor Name : Anmol Gawade", "Hospital Address : Model Town, Gurugram", "Exp : 5yrs", "Mobile No: 9898989898", "600"},
            {"Doctor Name : Prasad Pawar", "Hospital Address : Bhiwadi", "Exp : 15yrs", "Mobile No: 7898989898", "900"},
            {"Doctor Name : Nilesh Kale", "Hospital Address : Gurugram", "Exp : 8yrs", "Mobile No: 8898989898", "300"},
            {"Doctor Name : Deepak DeshPande", "Hospital Address : Bilaspur", "Exp : 6yrs", "Mobile No: 989890000", "800"},
            {"Doctor Name : Atul Pandey", "Hospital Address : Katraj", "Exp : 7yrs", "Mobile No: 7798989898", "800"}
    };

    TextView tv;
    Button btn;

    String[][] doctor_details = {};
    ArrayList<HashMap<String,String>> list;
    SimpleAdapter sa;
    HashMap<String,String> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_detail);

        tv = findViewById(R.id.textViewLTloc);
        btn = findViewById(R.id.buttonLTBack);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Family Physician")==0){ //MATCH
            doctor_details=doctor_details1;

        }
        if(title.compareTo("DIETICIAN")==0){ //MATCH
            doctor_details=doctor_details2;

        }
        if(title.compareTo("Dentist")==0){ //MATCH
            doctor_details=doctor_details3;

        }
        if(title.compareTo("SURGEON")==0){ //MATCH
            doctor_details=doctor_details4;

        }
        else{ //MATCH with cardiologist
            doctor_details=doctor_details5;

        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailActivity.this,FindDoctor.class));

            }
        });

        list = new ArrayList<>();
        for(int i=0;i<doctor_details.length;i++){
            item = new HashMap<String,String>();
            item.put("line1",doctor_details[i][0]);
            item.put("line2",doctor_details[i][1]);
            item.put("line3",doctor_details[i][2]);
            item.put("line4",doctor_details[i][3]);
            item.put("line5","Cash Fees "+doctor_details[i][4]+"/-");

            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );

        ListView ls = findViewById(R.id.ListViewDD);
        ls.setAdapter(sa);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent it = new Intent(DoctorDetailActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1",title);
                it.putExtra("text2",doctor_details[i][0]);
                it.putExtra("text3",doctor_details[i][1]);
                it.putExtra("text4",doctor_details[i][3]);
                it.putExtra("text5",doctor_details[i][4]);
                startActivity(it);


            }
        });
    }
}