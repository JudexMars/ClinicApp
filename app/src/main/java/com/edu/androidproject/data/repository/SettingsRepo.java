package com.edu.androidproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.SettingsSource;

import java.util.List;

public class SettingsRepo {
    private final MutableLiveData<List<String>> settings = new MutableLiveData<>();
    private final SettingsSource source;

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
