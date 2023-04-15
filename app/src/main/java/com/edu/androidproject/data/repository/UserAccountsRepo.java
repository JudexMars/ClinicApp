package com.edu.androidproject.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.UserAccountsSource;
import com.edu.androidproject.data.model.UserAccount;

import java.util.List;

public class UserAccountsRepo {
    private final MutableLiveData<List<UserAccount>> accounts;

    public UserAccountsRepo() { accounts = UserAccountsSource.createList(); }

    public LiveData<List<UserAccount>> getAccounts() { return accounts; }

    public void add(UserAccount account) {
        accounts.getValue().add(account);
        accounts.setValue(accounts.getValue());
    }

    public void remove(UserAccount account) {
        accounts.getValue().remove(account);
        accounts.setValue(accounts.getValue());
    }
}
