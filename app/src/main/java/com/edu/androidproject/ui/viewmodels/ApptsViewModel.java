package com.edu.androidproject.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.edu.androidproject.data.model.Appointment;
import com.edu.androidproject.data.repository.ApptsRepo;

import java.util.List;

public class ApptsViewModel extends ViewModel {
    private LiveData<List<Appointment>> appts;
    private ApptsRepo repo;

    public ApptsViewModel() {
        repo = new ApptsRepo();
        appts = repo.getAppts();
    }

    public LiveData<List<Appointment>> getAppts() {
        return appts;
    }

    public void add(Appointment appt) {
        repo.add(appt);
    }

    public void remove(Appointment appt) {
        repo.remove(appt);
    }
}
