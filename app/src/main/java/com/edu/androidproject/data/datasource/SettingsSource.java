package com.edu.androidproject.data.datasource;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SettingsSource {
    SharedPreferences config;

    public SettingsSource(Application application) {
       config = application.getApplicationContext()
               .getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getSettings() {
        // конфиг предназначен исключительно для хранения строковых значений и все его методы
        // позволяют записывать и получать только строки, поэтому этот cast уместен
        return (Map<String, String>) config.getAll();
    }

    public void set(String setting, String value) {
        config.edit().putString(setting, value).apply();
    }

    public void remove(String setting) {
        config.edit().remove(setting).apply();
    }

    public String get(String setting) {
        return config.getString(setting, "");
    }
    public void init(Map<String, String> lines) {
        SharedPreferences.Editor editor = config.edit();
        lines.forEach(editor::putString);
        editor.apply();
    }
}
