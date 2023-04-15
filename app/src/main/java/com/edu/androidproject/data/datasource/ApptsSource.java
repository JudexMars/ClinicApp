package com.edu.androidproject.data.datasource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ApptsSource {
    public static MutableLiveData<List<Appointment>> createList() {
        MutableLiveData<List<Appointment>> list = new MutableLiveData<>();
        ArrayList<Appointment> appts = new ArrayList<>();
        list.setValue(appts);
        return list;
    }
}
