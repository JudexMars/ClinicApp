package com.edu.androidproject.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.edu.androidproject.data.model.Appointment;
import com.edu.androidproject.data.repository.ApptsRepo;
import com.edu.androidproject.data.repository.SettingsRepo;

import java.util.List;
import java.util.Map;

public class SettingsViewModel extends AndroidViewModel {
    private final LiveData<List<String>> settings;
    private final SettingsRepo repo;

    public SettingsViewModel(Application application) {
        super(application);
        repo = new SettingsRepo(application);
        settings = repo.getSettings();
    }

    public LiveData<List<String>> getSettings() {
        return settings;
    }
    public void add(String record) { repo.add(record); }
    public void remove(String key) {
        repo.remove(key);
    }
    public void init(List<String> lines) { repo.init(lines); }
}
