package com.edu.androidproject.ui.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.Manifest;
import android.widget.Spinner;

import com.edu.androidproject.R;
import com.edu.androidproject.data.model.Appointment;
import com.edu.androidproject.data.model.UserAccount;
import com.edu.androidproject.ui.adapters.RecyclerViewAdapter;
import com.edu.androidproject.databinding.FragmentHomeBinding;
import com.edu.androidproject.ui.viewmodels.ApptsViewModel;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final String TAG = "Home screen";

    private UserAccount user;
    private RecyclerView apptList;
    private RecyclerViewAdapter apptListAdapter;
    //private ArrayList<Appointment> appointments = new ArrayList<>();

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton;
    private Button timeButton;

    private ApptsViewModel apptsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apptsViewModel = new ViewModelProvider(this).get(ApptsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    Dialog apptDialog;

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

        apptDialog = new Dialog(getContext());

        Button apptButton = binding.newAppointmentBtn;
        apptButton.setOnClickListener((View v) ->
        {
            showApptDialog();
            showNotification();
            Log.i(TAG, "Была нажата кнопка создания записи");
        });

        user = (UserAccount) this.getArguments().get("user");
        String userName = user.getName();
        binding.welcomeTextView.setText("Добро пожаловать, " + userName);

        apptList = view.findViewById(R.id.apptListView);
        apptListAdapter = new RecyclerViewAdapter(getContext(),
                apptsViewModel.getAppts().getValue());
        apptList.setAdapter(apptListAdapter);

        apptsViewModel.getAppts().observe(getViewLifecycleOwner(),
                appts -> apptListAdapter.notifyDataSetChanged());

        initDatePicker();
        initTimePicker();

        debugUser(user);
    }

    private void debugUser(UserAccount user) {
        Log.i(TAG, "Name:" + user.getName());
        Log.i(TAG, "Birthdate:" + user.getBirthdate().toString());
        Log.i(TAG, "Sex:" + user.getSex());
        Log.i(TAG, "Email:" + user.getEmail());
        Log.i(TAG, "Password:" + user.getPassword());

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener =
                (datePicker, year, month, day) ->
                {
                    dateButton.setText(MessageFormat.format("{0}.{1}", day, month + 1));
                    this.year = year;
                    this.month = month;
                    this.day = day;
                };
        Calendar cal = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(),
                com.google.android.material.R.style.MaterialAlertDialog_Material3, dateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    private int hour, minute, year, month, day;

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
        };

        timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
    }

    private void initSpinner() {
        Spinner dropdown = apptDialog.findViewById(R.id.drSpinner);
        String[] drs =
                new String[]{"Терапевт", "Офтальмолог", "Эндокринолог", "Невролог", "Психиатр"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(apptDialog.getContext(),
                android.R.layout.simple_spinner_dropdown_item, drs);
        dropdown.setAdapter(adapter);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.US);
        return dateFormat.format(cal.getTime());
    }

    private void showApptDialog() {
        apptDialog.setContentView(R.layout.appt_dialog);
        apptDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apptDialog.setCancelable(true);

        apptDialog.show();

        dateButton = apptDialog.findViewById(R.id.datePickerButton);
        dateButton.setOnClickListener(v -> datePickerDialog.show());
        dateButton.setText(getTodaysDate());

        timeButton = apptDialog.findViewById(R.id.timeButton);
        timeButton.setOnClickListener(v -> timePickerDialog.show());

        AppCompatSpinner spinner = apptDialog.findViewById(R.id.drSpinner);

        Button confirmButton = apptDialog.findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            apptsViewModel.add(new Appointment(user, (String)spinner.getSelectedItem(), cal.getTime(), hour, minute));
            binding.welcomeTextView.setVisibility(View.INVISIBLE);
            binding.imageView.setVisibility(View.INVISIBLE);
            binding.noAppointmentsTextView.setVisibility(View.INVISIBLE);
            apptDialog.dismiss();
        });

        initSpinner();
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
        } else requestPermissions();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{
                        Manifest.permission.POST_NOTIFICATIONS
                },
                PERMISSION_REQUEST_CODE);
    }
}