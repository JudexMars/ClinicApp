package com.edu.androidproject.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.edu.androidproject.R;
import com.edu.androidproject.databinding.ActivityMainBinding;
import com.edu.androidproject.ui.fragments.LoginScreenFragment;

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
        Log.d(TAG, "Активность стартовала");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Активность перезапущена");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Активность приостановлена");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Активность возобновлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Активность остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Активность уничтожена");
    }
}