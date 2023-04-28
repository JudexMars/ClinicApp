package com.edu.androidproject.data.datasource.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserAccountDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserAccountEntity account);

    @Query("DELETE FROM account_table")
    void deleteAll();

    @Query("SELECT * FROM account_table")
    LiveData<List<UserAccountEntity>> getAll();

    @Query("DELETE FROM account_table WHERE email = :email")
    void deleteByEmail(String email);

    @Delete
    void delete(UserAccountEntity account);
}
