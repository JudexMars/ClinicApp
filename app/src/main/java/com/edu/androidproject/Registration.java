package com.edu.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ((EditText)findViewById(R.id.emailTextField)).setText(getIntent().getExtras().getString("email"));

        Button signup_button = (Button) findViewById(R.id.registerBtn);
        signup_button.setOnClickListener((View v) -> {
            Log.i("Registration", "Была нажата кнопка регистрации");
            Intent input = getIntent();
            Intent res = new Intent();
            String email = input.getExtras().getString("email");

            Bundle resBundle = new Bundle();
            resBundle.putString("email", ((EditText)(findViewById(R.id.emailTextField))).getText().toString());
            resBundle.putString("password", ((EditText)(findViewById(R.id.passwordTextField))).getText().toString());

            res.putExtras(resBundle);

            setResult(RESULT_OK, res);
            finish();
        });
    }
}