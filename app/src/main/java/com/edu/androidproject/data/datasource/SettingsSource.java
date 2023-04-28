package com.edu.androidproject.data.datasource;

import android.app.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsSource {

    File file;

    public SettingsSource(Application application) {
        file = new File(application.getApplicationContext().getFilesDir(),
                "user_specific.txt");
    }

    public List<String> getSettings() {
        List<String> settings = new ArrayList<>();

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                settings.addAll(reader.lines().collect(Collectors.toList()));
            }
            catch (IOException e) {
                throw new RuntimeException("IOException in settings source class getSettings()");
            }
        }

        return settings;
    }

    public void add(String setting) {
        try (Writer writer = new FileWriter(file, file.exists())) {
            writer.write(setting);
            writer.write("\n");
        }
        catch (IOException e) {
            throw new RuntimeException("IOException in settings source class getSettings()");
        }
    }

    public void remove(String setting) {
        if (!file.exists()) return;

        List<String> temp = getSettings()
                .stream()
                .filter(s -> !s.equals(setting))
                .collect(Collectors.toList());

        init(temp);
    }


    public void init(List<String> lines) {
        try (Writer writer = new FileWriter(file)) {
            for (String line : lines) {
                writer.write(line);
                writer.write("\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Init failed in settings source class");
        }
    }
}
