package com.edu.androidproject.data.datasource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.model.UserAccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserAccountsSource {
    public static MutableLiveData<List<UserAccount>> createList() {
        MutableLiveData<List<UserAccount>> list = new MutableLiveData<>();
        ArrayList<UserAccount> accounts = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        UserAccount adminAccount =
                new UserAccount("Admin", cal.getTime(), UserAccount.Sex.MALE,
                        "admin@clinic.org", "admin");
        accounts.add(adminAccount);
        list.setValue(accounts);
        return list;
    }
}
