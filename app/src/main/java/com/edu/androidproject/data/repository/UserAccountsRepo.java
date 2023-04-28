package com.edu.androidproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.androidproject.data.datasource.Room.UserAccountDAO;
import com.edu.androidproject.data.datasource.Room.UserAccountEntity;
import com.edu.androidproject.data.datasource.Room.UserAccountRoomDatabase;
import com.edu.androidproject.data.model.UserAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserAccountsRepo {
    private final UserAccountDAO userAccountDAO;
    private final MutableLiveData<List<UserAccount>> accounts = new MutableLiveData<>();

    public UserAccountsRepo(Application application) {
        UserAccountRoomDatabase db = UserAccountRoomDatabase.getDatabase(application);
        userAccountDAO = db.userAccountDAO();
        LiveData<List<UserAccountEntity>> accountsEntities = userAccountDAO.getAll();

        accountsEntities.observeForever((x) -> {
            if (x != null)
                accounts.setValue(Objects.requireNonNull(x
                        .stream().map(this::modelEntity).collect(Collectors.toList())));
        });
    }

    private UserAccount modelEntity(UserAccountEntity entity) {
        UserAccount account;
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        try {
            Date birthdate = fmt.parse(entity.getBirthdate());
            account = new UserAccount(entity.getName(), birthdate,
                    entity.getSex(), entity.getEmail(), entity.getPassword());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    private UserAccountEntity dbEntity(UserAccount account) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        String birthdate = fmt.format(account.getBirthdate());
        return new UserAccountEntity(account.getName(), birthdate,
                account.getSex(), account.getEmail(), account.getPassword());
    }

    public LiveData<List<UserAccount>> getAccounts() { return accounts; }

    public void insert(UserAccount account) {
        UserAccountRoomDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.insert(dbEntity(account));
        });
    }

    public void delete(UserAccount account) {
        UserAccountRoomDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.delete(dbEntity(account));
        });
    }

    public void deleteByEmail(String email) {
        UserAccountRoomDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.deleteByEmail(email);
        });
    }

    public void deleteAll() {
        UserAccountRoomDatabase.databaseWriteExecutor.execute(userAccountDAO::deleteAll);
    }
}
