package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOTPActivity extends AppCompatActivity {
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private String verificationId;

    private ProgressBar progressBar;
    private Button buttonVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        progressBar = findViewById(R.id.verifyProgeressBar);
        buttonVerify = findViewById(R.id.buttonVerifyOTP);


        TextView textMobile = findViewById(R.id.TextMobileNumber);
        textMobile.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        verificationId = getIntent().getStringExtra("verificationId");

        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        setupOTPinputs();

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputCode1.getText().toString().trim().isEmpty() ||
                    inputCode2.getText().toString().trim().isEmpty() ||
                        inputCode3.getText().toString().trim().isEmpty() ||
                        inputCode4.getText().toString().trim().isEmpty() ||
                        inputCode5.getText().toString().trim().isEmpty() ||
                        inputCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please enter valid code",Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = inputCode1.getText().toString()+
                        inputCode2.getText().toString()+inputCode3.getText().toString()+inputCode4.getText().toString()+
                        inputCode5.getText().toString()+inputCode6.getText().toString();

                if(verificationId !=null){
                    progressBar.setVisibility(View.VISIBLE);
                    buttonVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential  = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );   //This creates a PhoneAuthCredential object using the verification ID (verificationId) and the OTP entered by the user (code).
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)  //FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential): This method attempts to sign in the user using the provided credentials (OTP).
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {   //This adds a listener to handle the completion of the sign-in process asynchronously.
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    buttonVerify.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()){   //This condition checks if the sign-in task was successful.
                                        Intent it = new Intent(getApplicationContext(),LoginActivity.class);
                                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  //FLAG_ACTIVITY_NEW_TASK: This flag indicates that the activity should be launched into a new task. If this flag is not set, the activity will be launched into the task of the activity that started it.
                                        //FLAG_ACTIVITY_CLEAR_TASK: This flag clears the task that the activity is being launched into. All activities on the stack above this one will be finished and removed from the stack.
                                        startActivity(it);
                                    } else{
                                        Toast.makeText(getApplicationContext(),"The verification code entered is not correct",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });


    }

    private void setupOTPinputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()){
                        inputCode2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        }
}