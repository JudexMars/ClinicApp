package com.edu.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button loginButton = (Button) findViewById(R.id.newAppointmentBtn);
        loginButton.setOnClickListener((View v) -> {
            Log.i("Home Activity", "Была нажата кнопка создания записи");
        });
    }
}