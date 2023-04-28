package com.edu.androidproject.data.datasource;

import android.app.Application;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LogsSource {

    File file;

    public LogsSource(Application application) {
        file = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "ClinicExample.txt");
        Date currentDate = new Date();
        try (Writer writer = new FileWriter(file)) {
            writer.write("Last login:");
            writer.write(currentDate.toString());
        }
        catch (IOException e) {
            throw new RuntimeException("IOException when writing external file");
        }
    }

    public List<String> getLogs() {
        List<String> log = new ArrayList<>();

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                log.addAll(reader.lines().collect(Collectors.toList()));
            }
            catch (IOException e) {
                throw new RuntimeException("IOException in log source class getLogs()");
            }
        }
        return log;
    }

    public void add(String setting) {
        try (Writer writer = new FileWriter(file, file.exists())) {
            writer.write(setting);
            writer.write("\n");
        }
        catch (IOException e) {
            throw new RuntimeException("IOException in log source class add()");
        }
    }

    public void remove(String log) {
        if (!file.exists()) return;

        List<String> temp = getLogs()
                .stream()
                .filter(s -> !s.equals(log))
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
            throw new RuntimeException("Init failed in log source class");
        }
    }
}
