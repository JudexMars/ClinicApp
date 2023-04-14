package com.edu.androidproject.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.edu.androidproject.databinding.FragmentRegistrationBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
                res.putString("name", binding.nameTextField.getText().toString());

                int checkedRadioId = binding.genderRadioBtn.getCheckedRadioButtonId();
                String gender = ((RadioButton) view.findViewById(checkedRadioId)).getText().toString();

                res.putString("sex", gender);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
                try {
                    Date birthdate = sdf.parse(binding.birthDateTextField.getText().toString());
                    res.putSerializable("birthdate", birthdate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            getParentFragmentManager().setFragmentResult("userData", res);
                getParentFragmentManager().popBackStack();
        });
    }
}