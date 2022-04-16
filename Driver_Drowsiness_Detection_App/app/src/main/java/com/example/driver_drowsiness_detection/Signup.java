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

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private Button createacc;
    private TextView signIn;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String fName,lName,pNum,emerNum,vehNum;
    private EditText firstName,lastName,phoneNumber,emergencyContact,vehicleNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        firstName = findViewById(R.id.first_name_edt);
        lastName = findViewById(R.id.last_name_edt);
        phoneNumber = findViewById(R.id.mobile_number_edt);
        emergencyContact = findViewById(R.id.emergency_contact_edt);
        vehicleNumber = findViewById(R.id.vehicle_number_edt);



        signIn = findViewById(R.id.signup_login_btn);

        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        createacc = findViewById(R.id.create_acc_btn);
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                pNum = phoneNumber.getText().toString();
                emerNum = emergencyContact.getText().toString();
                vehNum = vehicleNumber.getText().toString();

                String message = "Kindly enter the ";
                int flag = 0;
                int flag1 = 0;

                if(fName.isEmpty() && lName.isEmpty() && pNum.isEmpty() && emerNum.isEmpty() && vehNum.isEmpty())
                {
                    flag1 = 1;
                    message += "details asked";
//                    Toast.makeText(Signup.this,"Enter the details asked",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (fName.isEmpty()) {
                        if (flag == 0) {
                            flag = 1;
                            message += "first name";
                        } else {
                            message += ",first name";
                        }
//                        Toast.makeText(Signup.this, "Kindly enter the first name", Toast.LENGTH_SHORT).show();
                    }
                    if(!fName.isEmpty())
                    {
                        for(int i = 0; i < fName.length();i++)
                        {
                            if(Character.isDigit(fName.charAt(i)))
                            {
                                if (flag == 0) {
                                    flag = 1;
                                    message += "valid first name";
                                } else {
                                    message += ",valid first name";
                                }
                                break;
                            }
                        }
                    }
                    if (lName.isEmpty()) {
                        if (flag == 0) {
                            flag = 1;
                            message += "last name";
                        } else {
                            message += ",last name";
                        }
//                        Toast.makeText(Signup.this, "Kindly enter the last name", Toast.LENGTH_SHORT).show();
                    }
                    if(!lName.isEmpty())
                    {
                        for(int i = 0; i < lName.length();i++)
                        {
                            if(Character.isDigit(lName.charAt(i)))
                            {
                                if (flag == 0) {
                                    flag = 1;
                                    message += "valid last name";
                                } else {
                                    message += ",valid last name";
                                }
                                break;
                            }
                        }
                    }
                    if (pNum.isEmpty()) {
                        if (flag == 0) {
                            flag = 1;
                            message += "phone number";
                        } else {
                            message += ",phone number";
                        }
//                        Toast.makeText(Signup.this, "Kindly enter the phone number", Toast.LENGTH_SHORT).show();
                    }
                    if (pNum.length() != 10 && pNum.length()!=0) {
                        if (flag == 0) {
                            flag = 1;
                            message += "a valid phone number";
                        } else {
                            message += ",a valid phone number";
                        }
//                        Toast.makeText(Signup.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    }
                    if(pNum.length() == 10)
                    {
                        for(int i = 0; i < pNum.length();i++)
                        {
                            if(!Character.isDigit(pNum.charAt(i)))
                            {
                                if (flag == 0) {
                                    flag = 1;
                                    message += "valid phone number";
                                } else {
                                    message += ",valid phone number";
                                }
                                break;
                            }
                        }
                    }
                    if (emerNum.isEmpty()) {
                        if (flag == 0) {
                            flag = 1;
                            message += "emergency contact";
                        } else {
                            flag = 1;
                            message += ",emergency contact";
                        }
//                        Toast.makeText(Signup.this, "Kindly enter the emergency contact", Toast.LENGTH_SHORT).show();
                    }
                    if (emerNum.length() != 10&& emerNum.length()!=0) {
                        if (flag == 0) {
                            flag = 1;
                            message += "a valid emergency contact number";
                        } else {
                            message += ",a valid emergency contact number";
                        }
//                        Toast.makeText(Signup.this, "Enter a valid emergency contact", Toast.LENGTH_SHORT).show();
                    }
                    if(emerNum.length() == 10)
                    {
                        for(int i = 0; i < emerNum.length();i++)
                        {
                            if(!Character.isDigit(emerNum.charAt(i)))
                            {
                                if (flag == 0) {
                                    flag = 1;
                                    message += "valid emergency contact number";
                                } else {
                                    message += ",valid emergency contact number";
                                }
                                break;
                            }
                        }
                    }
                    if (vehNum.isEmpty()) {
                        if (flag == 0) {
                            flag = 1;
                            message += "vehicle number";
                        } else {
                            message += ",vehicle number";
                        }
//                        Toast.makeText(Signup.this, "Kindly enter the vehicle number", Toast.LENGTH_SHORT).show();
                    }
                }

                if(flag == 1 || flag1 == 1)
                {
                    Toast.makeText(Signup.this,message,Toast.LENGTH_SHORT).show();
                }
                else {
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("Users");
//                    databaseReference.setValue("First data storage");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String id = user.getUid();
                    Toast.makeText(Signup.this,id,Toast.LENGTH_LONG).show();
                    UserClass user1 = new UserClass(firstName.getText().toString(),lastName.getText().toString(),phoneNumber.getText().toString(),emergencyContact.getText().toString(), vehicleNumber.getText().toString(),id);
                    databaseReference.child(id).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Signup.this, "Profile Created", Toast.LENGTH_SHORT).show();
                            myEdit.putString("id",id);
                            myEdit.putInt("hasAccount", 1);
                            myEdit.commit();
                            startActivity(new Intent(Signup.this, LoginActivity.class));
                        }
                    });
                }
            }
        });
    }
}