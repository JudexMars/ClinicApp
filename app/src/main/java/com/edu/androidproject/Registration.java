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

import com.edu.androidproject.databinding.ActivityRegistrationBinding;

public class Registration extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.emailTextField.setText(getIntent().getExtras().getString("email"));

        Button signup_button = binding.registerBtn;
        signup_button.setOnClickListener((View v) -> {
            Log.i("Registration", "Была нажата кнопка регистрации");
            Intent input = getIntent();
            Intent res = new Intent();
            String email = input.getExtras().getString("email");

            Bundle resBundle = new Bundle();
            resBundle.putString("email", binding.emailTextField.getText().toString());
            resBundle.putString("password", binding.passwordTextField.getText().toString());

            res.putExtras(resBundle);

            setResult(RESULT_OK, res);
            finish();
        });
    }
}