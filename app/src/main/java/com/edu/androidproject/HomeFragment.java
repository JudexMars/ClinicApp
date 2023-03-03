package com.edu.androidproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.edu.androidproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final String TAG = "Home screen";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        Button loginButton = binding.newAppointmentBtn;
        loginButton.setOnClickListener((View v) -> Log.i(TAG, "Была нажата кнопка создания записи"));

        String userName = this.getArguments().getString("name");
        binding.welcomeTextView.setText("Добро пожаловать, " + userName);
    }
}