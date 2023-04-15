package com.edu.androidproject.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.ApptsSource;
import com.edu.androidproject.data.model.Appointment;

import java.util.List;

public class ApptsRepo {
    private MutableLiveData<List<Appointment>> appts;

    public ApptsRepo() { appts = ApptsSource.createList(); }

    public LiveData<List<Appointment>> getAppts() { return appts; }

    public void add(Appointment appt) {
        appts.getValue().add(appt);
        appts.setValue(appts.getValue());
    }

    public void remove(Appointment appt) {
        appts.getValue().remove(appt);
        appts.setValue(appts.getValue());
    }
}
