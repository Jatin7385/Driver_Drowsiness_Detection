package com.example.driver_drowsiness_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DatabaseReference;

public class Splash_Screen extends AppCompatActivity {
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("hasAccount", 0);
        myEdit.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent i=new Intent(Splash_Screen.this,
//                        LoginActivity.class);

                Intent i = new Intent(Splash_Screen.this, LoginActivity.class);

                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, 3000);
    }
}