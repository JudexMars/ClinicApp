package com.edu.androidproject.data;

import java.util.Date;

public class Appointment {
    private UserAccount patient;
    private String dr;
    private Date day;
    private int hour;
    private int minute;

    public Appointment(UserAccount patient, String dr, Date day, int hour, int minute) {
        this.patient = patient;
        this.dr = dr;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public UserAccount getPatient() { return patient; }
    public String getDr() { return dr; }
    public Date getDay() { return day; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    public void setPatient(UserAccount patient) { this.patient = patient; }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
