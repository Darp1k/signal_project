package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataListener implements DataListener {
    private String filePath;
    private DataParser parser;
    private DataSourceAdapter adapter;
    private boolean isRunning;

    public FileDataListener(String filePath, DataParser parser, DataSourceAdapter adapter) {
        this.filePath = filePath;
        this.parser = parser;
        this.adapter = adapter;
    }

    @Override
    public void start() {
        isRunning = true;
        // Start a new thread so the listener doesn't block the main program
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while (isRunning) {
                    line = reader.readLine();
                    if (line != null) {
                        onLineRead(line);
                    } else {
                        // Wait briefly for new data to be written to the file
                        Thread.sleep(500); 
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error reading file: " + filePath);
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    private void onLineRead(String rawData) {
        // 1. Parse the string into a record
        PatientRecord record = parser.parse(rawData);
        
        // 2. If valid, route it to the DataStorage via the adapter
        if (record != null) {
            adapter.routeData(record);
        }
    }
}