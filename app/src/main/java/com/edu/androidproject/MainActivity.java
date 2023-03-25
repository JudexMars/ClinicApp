package com.edu.androidproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(this.getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Активность создана");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view,
                            LoginScreenFragment.class, null)
                    .commit();
        }
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

    public final int PERMISSION_REQUEST_CODE = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE &&
                grantResults.length == 1) {
            if (
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                Log.i(TAG, "Разрешение получено: " + requestCode);
            }
            else {
                Log.w(TAG, "Разрешение отказано: " + requestCode);
            }
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults
        );
    }
}