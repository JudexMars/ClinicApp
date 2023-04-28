package com.edu.androidproject.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.edu.androidproject.data.repository.LogsRepo;

import java.util.List;

public class LogsViewModel extends AndroidViewModel {
    private final LiveData<List<String>> logs;
    private final LogsRepo repo;

    public LogsViewModel(Application application) {
        super(application);
        repo = new LogsRepo(application);
        logs = repo.getLogs();
    }

    public LiveData<List<String>> getSettings() {
        return logs;
    }
    public void add(String record) { repo.add(record); }
    public void remove(String key) {
        repo.remove(key);
    }
    public void init(List<String> lines) { repo.init(lines); }
}
