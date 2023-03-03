package com.edu.androidproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.androidproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    // We're getting the results from signup activity here
    ActivityResultLauncher<Intent> mStartForRegData = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle res = intent.getExtras();
                        binding.usernameText.setText(res.getString("email"));
                        binding.passwordText.setText(res.getString("password"));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(this.getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Активность создана");

        Button signupButton = binding.signUpButton;
        signupButton.setText(R.string.signup_button_text);

        ImageView caduceus = binding.caduceusImage;
        caduceus.setImageResource(R.drawable.caduceus);

        Button loginButton = binding.loginButton;
        loginButton.setOnClickListener((View v) -> {
            Log.i(TAG, "Кнопка входа была нажата");
            if (binding.usernameText.getText().toString() == "admin")
            {
                if (binding.passwordText.getText().toString() == "admin") {
                    startActivity(new Intent(this, Home.class));
                }
            }
            else {
                Log.w(TAG, "Неудачная попытка входа");
            }
        });
    }

    public void signUp_onClick(View v ) {
        Log.i(TAG, "Кнопка регистрации была нажата");

        Intent intent = new Intent(this, Registration.class);
        intent.putExtra("email", binding.usernameText.getText().toString());
        mStartForRegData.launch(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this.getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Активность стартовала");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this.getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Активность перезапущена");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this.getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Активность приостановлена");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this.getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Активность возобновлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this.getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Активность остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this.getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.w(TAG, "Активность уничтожена");
    }
}