package com.edu.androidproject.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.edu.androidproject.data.model.UserAccount;
import com.edu.androidproject.data.repository.UserAccountsRepo;

import java.util.List;

public class UserAccountsViewModel extends ViewModel {
    private final LiveData<List<UserAccount>> accounts;
    private final UserAccountsRepo repo;

    public UserAccountsViewModel() {
        repo = new UserAccountsRepo();
        accounts = repo.getAccounts();
    }

    public LiveData<List<UserAccount>> getAccounts() {
        return accounts;
    }

    public void add(UserAccount account) {
        repo.add(account);
    }

    public void remove(UserAccount account) {
        repo.remove(account);
    }
}
