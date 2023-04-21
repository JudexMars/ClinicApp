package com.edu.androidproject.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.edu.androidproject.data.model.UserAccount;
import com.edu.androidproject.data.repository.UserAccountsRepo;

import java.util.List;

public class UserAccountsViewModel extends AndroidViewModel {
    private final LiveData<List<UserAccount>> accounts;
    private final UserAccountsRepo repo;

    public UserAccountsViewModel(Application application) {
        super(application);
        repo = new UserAccountsRepo(application);
        accounts = repo.getAccounts();
    }

    public LiveData<List<UserAccount>> getAccounts() {
        return accounts;
    }

    public void insert(UserAccount account) {
        repo.insert(account);
    }

    public void delete(UserAccount account) {
        repo.delete(account);
    }

    public void deleteByEmail(String email) { repo.deleteByEmail(email); }

    public void deleteAll() { repo.deleteAll(); }
}
