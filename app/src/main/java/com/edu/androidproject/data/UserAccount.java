package com.edu.androidproject.data;

import java.io.Serializable;
import java.util.Date;

public class UserAccount implements Serializable {
    private String name;
    private Date birthdate;
    private Sex sex;
    private String email;
    private String password;

    public UserAccount(String name, Date birthdate, Sex sex, String email, String password) {
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    public enum Sex {
        MALE,
        FEMALE
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
