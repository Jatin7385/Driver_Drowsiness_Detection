package com.example.driver_drowsiness_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    private Button loginBtn;
    private EditText phone,otp;
    private String verificationId,phnumber;
    private TextView resend;
    private FirebaseAuth mAuth;
    private Boolean hasAccount;
    private SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        getSupportActionBar().hide();

        sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        phone = findViewById(R.id.otp_phnumber_edt);
        otp = findViewById(R.id.otp_otp_edt);

        phnumber = getIntent().getStringExtra("number");
        hasAccount = getIntent().getBooleanExtra("hasAccount",false);

        phone.setText(phnumber);

        sendVerificationCode("+91" + phnumber);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(OTP.this, "Invalid Otp Entered", Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(OTP.this, "Invalid Otp Entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    verifyCode(otp.getText().toString());
                }
            }
        });

//        resend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(OTP.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                OTP.this, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.

                            System.out.println(sh.getInt("hasAccount",0));
                            if(sh.getInt("hasAccount",0) == 0)
                            {
                                Intent i = new Intent(OTP.this,Signup.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Intent i = new Intent(OTP.this, HomeActivity.class);
                                i.putExtra("code", "+91");
                                i.putExtra("number", phnumber);
                                startActivity(i);
                                finish();
                            }
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                otp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}