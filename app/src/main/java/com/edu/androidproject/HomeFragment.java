package com.edu.androidproject;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.Manifest;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification",
                    "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Button apptButton = binding.newAppointmentBtn;
        apptButton.setOnClickListener((View v) ->
        {
            showNotification();
            Log.i(TAG, "Была нажата кнопка создания записи"); });

        String userName = this.getArguments().getString("name");
        binding.welcomeTextView.setText("Добро пожаловать, " + userName);
    }

    private final String CHANNEL_ID = "channel_id";
    private final int PERMISSION_REQUEST_CODE = 0;

    private void showNotification() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),
                    "My notification")
                    .setSmallIcon(R.drawable.caduceus)
                    .setContentTitle(getString(
                            R.string.notification_title))
                    .setContentText("Sample text")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getContext());
            notificationManager.notify(0, builder.build());
        }
        else requestPermissions();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[] {
                        Manifest.permission.POST_NOTIFICATIONS
                },
            PERMISSION_REQUEST_CODE);
    }

//    @Override
//    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}