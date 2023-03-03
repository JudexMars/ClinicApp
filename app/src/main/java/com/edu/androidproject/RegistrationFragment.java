package com.edu.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.edu.androidproject.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private final String TAG = "Registration screen";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {

        String email = this.getArguments().getString("email");
        if (email != null) {
            binding.emailTextField.setText(email);
        }

        Button signup_button = binding.registerBtn;
        signup_button.setOnClickListener((View v) -> {
                Log.i(TAG, "Была нажата кнопка регистрации");
                Bundle res = new Bundle();

                res.putString("email", binding.emailTextField.getText().toString());
                res.putString("password", binding.passwordTextField.getText().toString());

                getParentFragmentManager().setFragmentResult("userData", res);
                getParentFragmentManager().popBackStack();
        });
    }
}