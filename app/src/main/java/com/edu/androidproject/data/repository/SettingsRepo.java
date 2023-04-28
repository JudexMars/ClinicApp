package com.edu.androidproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.SettingsSource;

import java.util.List;
import java.util.Map;

public class SettingsRepo {
    private final MutableLiveData<Map<String, String>> settings = new MutableLiveData<>();
    private final SettingsSource source;

    public SettingsRepo(Application application) {
        source = new SettingsSource(application);
        settings.setValue(source.getSettings());
    }

    public LiveData<Map<String, String>> getSettings() { return settings; }

    public void set(String setting, String value) {
        source.set(setting, value);
        settings.setValue(source.getSettings());
    }

    public void remove(String record) {
        source.remove(record);
        settings.setValue(source.getSettings());
    }

    public void init(Map<String, String> lines) {
        source.init(lines);
        settings.setValue(source.getSettings());
    }
}
