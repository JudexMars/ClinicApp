package com.edu.androidproject.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import com.edu.androidproject.R;
import com.edu.androidproject.data.model.UserAccount;
import com.edu.androidproject.databinding.FragmentLoginScreenBinding;
import com.edu.androidproject.ui.viewmodels.UserAccountsViewModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LoginScreenFragment extends Fragment implements LifecycleObserver {

    private static final String TAG = "Login screen";
    private FragmentLoginScreenBinding binding;
    //private final Set<UserAccount> users = new HashSet<>();
    UserAccountsViewModel userAccountsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userAccountsViewModel = new ViewModelProvider(this).get(UserAccountsViewModel.class);

        getParentFragmentManager().setFragmentResultListener("userData",
                this, (requestKey, result) -> {
                    String email = result.getString("email");
                    String password = result.getString("password");
                    String name = result.getString("name");
                    Date birthdate = (Date) result.get("birthdate");
                    String gender = result.getString("sex");
                    binding.usernameText.setText(email);
                    binding.passwordText.setText(password);

                    UserAccount user = new UserAccount(name, birthdate,
                            gender == "Муж" ? UserAccount.Sex.MALE : UserAccount.Sex.FEMALE, email,
                            password);

                    userAccountsViewModel.add(user);
                });
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

            Optional<UserAccount> user = userAccountsViewModel.getAccounts().getValue().stream()
                    .filter(u -> u.getEmail().equals(binding.usernameText.getText().toString()))
                    .findAny();

            if (user.isPresent())
            {
                if (binding.passwordText.getText().toString().equals(user.get().getPassword())) {

                    Fragment fragment = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("user", user.get());
                    fragment.setArguments(args);

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