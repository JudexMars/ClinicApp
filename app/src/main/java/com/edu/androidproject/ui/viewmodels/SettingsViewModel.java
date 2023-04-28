package com.edu.androidproject.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.edu.androidproject.data.repository.SettingsRepo;

import java.util.List;
import java.util.Map;

public class SettingsViewModel extends AndroidViewModel {
    private final LiveData<Map<String, String>> settings;
    private final SettingsRepo repo;

    public SettingsViewModel(Application application) {
        super(application);
        repo = new SettingsRepo(application);
        settings = repo.getSettings();
    }

    public LiveData<Map<String, String>> getSettings() {
        return settings;
    }
    public void set(String setting, String value) { repo.set(setting, value); }
    public void remove(String key) {
        repo.remove(key);
    }
    public void init(Map<String, String> lines) { repo.init(lines); }
}
