package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// class that listens to the files in the specified directory and proccesses the incoming data to the DataStorage
public class FileDataListener implements DataListener {
    private String directory;
    private DataParser parser;
    private DataSourceAdapter adapter;
    private boolean isRunning;

    /**
     * Constructs a new FileDataListener.
     * @param directory The directory to listen for file changes
     * @param parser The data parser to use
     * @param adapter The data source adapter to use
     */
    public FileDataListener(String directory, DataParser parser, DataSourceAdapter adapter) {
        this.directory = directory;
        this.parser = parser;
        this.adapter = adapter;
    }
    
    /**
     * starts the thread for each file in the directory that listen to the new changes in the assigned files
     */
    @Override
    public void start() {
        File dir = new File(directory);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        startListening(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * Starts a new thread that continuously reads the specified file for new data and processes it.
     * @param filePath path to the file
     */
    public void startListening(String filePath) {
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

    /**
     * Processes a single line of raw data
     * @param rawData the parameters in the unspecified format
     */
    private void onLineRead(String rawData) {
        // Parse the string into a record
        PatientRecord record = parser.parse(rawData);
        
        // If valid, route it to the DataStorage via the adapter
        if (record != null) {
            adapter.routeData(record);
        }
    }
}