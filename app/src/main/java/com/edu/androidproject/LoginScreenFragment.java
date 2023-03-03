package com.edu.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;

import com.edu.androidproject.databinding.FragmentLoginScreenBinding;

public class LoginScreenFragment extends Fragment implements LifecycleObserver {

    private static final String TAG = "Login screen";
    private FragmentLoginScreenBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("userData",
                this, (requestKey, result) -> {
                    String email = result.getString("email");
                    String password = result.getString("password");
                    binding.usernameText.setText(email);
                    binding.passwordText.setText(password);
                });

        Toast.makeText(getActivity(), "Login: CREATED", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Login: CREATED");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getActivity(), "Login: STARTED", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Login: STARTED");
    }

    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getActivity(), "Login: RESUMED", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Login: RESUMED");
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(), "Login: CREATED", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Login: CREATED");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getActivity(), "Login: DESTROYED", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Login: DESTROYED");
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        Button signupButton = binding.signUpButton;
        signupButton.setText(R.string.signup_button_text);

        ImageView caduceus = binding.caduceusImage;
        caduceus.setImageResource(R.drawable.caduceus);

        Button loginButton = binding.loginButton;
        loginButton.setOnClickListener((View v) -> {
            Log.i(TAG, "Кнопка входа была нажата");
            Log.i(TAG, "Username: " + binding.usernameText.getText().toString());
            Log.i(TAG, "Password: " + binding.passwordText.getText().toString());
            if (binding.usernameText.getText().toString().equals("admin"))
            {
                if (binding.passwordText.getText().toString().equals("admin")) {

                    Fragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
            else {
                Log.w(TAG, "Неудачная попытка входа");
            }
        });

        signupButton.setOnClickListener((View v) -> {
            Fragment fragment = new RegistrationFragment();

            Bundle email = new Bundle();
            email.putString("email", binding.usernameText.getText().toString());
            fragment.setArguments(email);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}