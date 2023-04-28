package com.edu.androidproject.data.datasource.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.edu.androidproject.data.model.UserAccount;

import java.util.Date;

@Entity(tableName = "account_table")
public class UserAccountEntity {
    private String name;
    private String birthdate;
    private UserAccount.Sex sex;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Email")
    private String email;
    private String password;

    public UserAccountEntity(@NonNull String name, @NonNull String birthdate,
                             @NonNull UserAccount.Sex sex, @NonNull String email,
                             @NonNull String password) {
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public UserAccount.Sex getSex() { return sex; }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
