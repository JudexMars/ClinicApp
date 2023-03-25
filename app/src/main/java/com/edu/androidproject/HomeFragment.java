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
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Button apptButton = binding.newAppointmentBtn;
        apptButton.setOnClickListener((View v) ->
        {
            showNotification();
            Log.i(TAG, "Была нажата кнопка создания записи");
        });

        String userName = this.getArguments().getString("name");
        binding.welcomeTextView.setText("Добро пожаловать, " + userName);
    }

    private final String CHANNEL_ID = "AppointmentNotification";

    private void showNotification() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Разрешение уже есть");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),
                    CHANNEL_ID)
                    .setSmallIcon(R.drawable.caduceus)
                    .setContentTitle(getString(
                            R.string.notification_title))
                    .setContentText(getString(R.string.notification_text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getContext());
            notificationManager.notify(0, builder.build());
        } else requestPermissions();
    }

    private void requestPermissions() {
        MainActivity activity = (MainActivity) getActivity();
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.POST_NOTIFICATIONS},
                                activity.PERMISSION_REQUEST_CODE);
    }
}