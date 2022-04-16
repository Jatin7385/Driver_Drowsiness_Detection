package com.example.driver_drowsiness_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private Button sendOTP;
    private EditText number;
    private String no;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Boolean hasAccount = false;
    private TextView signInBtn;
    String ph = "null";
    String full_number;
    String Hnumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        try {

            signInBtn = findViewById(R.id.login_signin);

            mAuth = FirebaseAuth.getInstance();

            final String[] phnum = {null};

            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,Signup.class);
                    startActivity(intent);
                }
            });

            sendOTP = findViewById(R.id.send_otp_btn);
            number = findViewById(R.id.login_phnumber_edt);
            sendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phnum[0] = number.getText().toString().trim();
                    if (!phnum[0].isEmpty()) {
                        if (phnum[0].length() == 10) {
                            full_number = "+91-" + phnum[0];
                            Hnumber = phnum[0];

                            Intent otp = new Intent(LoginActivity.this, OTP.class);
                            System.out.println(full_number);
                            System.out.println(number);
                            System.out.println(full_number.substring(0, 3));
                            otp.putExtra("full number", full_number);
                            otp.putExtra("number", Hnumber);
                            otp.putExtra("account", hasAccount);
                            startActivity(otp);
                        } else {
                            Toast.makeText(LoginActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter a number", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
    }
}