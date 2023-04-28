package com.edu.androidproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.LogsSource;

import java.util.List;

public class LogsRepo {
    private final MutableLiveData<List<String>> logs = new MutableLiveData<>();
    private final LogsSource source;

    public LogsRepo(Application application) {
        source = new LogsSource(application);
        logs.setValue(source.getLogs());
    }

    public LiveData<List<String>> getLogs() { return logs; }

    public void add(String record) {
        source.add(record);
        logs.setValue(source.getLogs());
    }

    public void remove(String record) {
        source.remove(record);
        logs.setValue(source.getLogs());
    }

    public void init(List<String> lines) {
        source.init(lines);
        logs.setValue(source.getLogs());
    }
}
