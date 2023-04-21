package com.edu.androidproject.ui.fragments;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import com.edu.androidproject.R;
import com.edu.androidproject.data.model.UserAccount;
import com.edu.androidproject.databinding.FragmentLoginScreenBinding;
import com.edu.androidproject.ui.viewmodels.UserAccountsViewModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.security.Permissions;
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

    boolean firstLaunch = true;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        if (!checkPermission()) requestPermission();

        if (firstLaunch) {
            File rememberMeFile = new File(getContext().getFilesDir(), "user_specific");
            try (BufferedReader reader = new BufferedReader(new FileReader(rememberMeFile))) {
                String rememberedUsername = reader.readLine();
                String rememberedPassword = reader.readLine();

                binding.usernameText.setText(rememberedUsername);
                binding.passwordText.setText(rememberedPassword);

                firstLaunch = false;
            }
            catch (IOException e) {
                Log.w(TAG, "user_specific file not found");
            }
        }


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

                    CheckBox rememberPasswordCheckbox = binding.rememberPasswordCheckbox;
                    if (rememberPasswordCheckbox.isChecked()) {
                        String rememberedUserName = binding.usernameText.getText().toString();
                        String rememberedPassword = binding.passwordText.getText().toString();

                        File file = new File(getContext().getFilesDir(), "user_specific");
                        try (Writer writer = new FileWriter(file)) {
                            writer.write(rememberedUserName);
                            writer.write("\n");
                            writer.write(rememberedPassword);
                        }
                        catch (IOException e) {
                            throw new RuntimeException();
                        }
                    }

                    if (checkPermission()) {
                        File file = new File(Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                                "ClinicExample.txt");
                        Date currentDate = new Date();
                        try (Writer writer = new FileWriter(file)) {
                            writer.write("Last login:");
                            writer.write(currentDate.toString());
                        }
                        catch (IOException e) {
                            Log.w(TAG, "IOException when writing external file");
                        }
                    }
                    else {
                        Log.w(TAG, "No external write permission");
                        requestPermission();
                    }

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

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
                intent.setData(uri);
                requireContext().startActivity(intent);
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }
}