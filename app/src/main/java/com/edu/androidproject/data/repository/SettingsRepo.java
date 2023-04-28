package com.edu.androidproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.ApptsSource;
import com.edu.androidproject.data.datasource.SettingsSource;
import com.edu.androidproject.data.model.Appointment;

import java.util.List;
import java.util.Map;

public class SettingsRepo {
    private MutableLiveData<List<String>> settings;
    private SettingsSource source;

    public SettingsRepo(Application application) {
        source = new SettingsSource(application);
        settings.setValue(source.getSettings());
    }

    public LiveData<List<String>> getSettings() { return settings; }

    public void add(String record) {
        source.add(record);
        settings.setValue(source.getSettings());
    }

    public void remove(String record) {
        source.remove(record);
        settings.setValue(source.getSettings());
    }

    public void init(List<String> lines) {
        source.init(lines);
        settings.setValue(source.getSettings());
    }
}
