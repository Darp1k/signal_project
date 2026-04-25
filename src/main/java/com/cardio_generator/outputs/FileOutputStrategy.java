package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;


/**
 * output strategy to write medical outputs into designated directory
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // file names should start from a small letter

    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); // removed underline to follow the guidelines and made it private for encapsulation


    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        try { // moved directory creation here because there is no need to try to create the directory every time output is called
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
        }
    }


    /**
     * @param patientId The ID of the patient for whom to generate data.
     * @param timestamp The timestamp of the data generation event.
     * @param label Name of the data type being generated (e.g., "ECG", "HeartRate").
     * @param data The actual data value to be outputted.
     * overriden method that writes all parameters into the files
     * text files are automatically created based on baseDirectory 
     * for example: ECG data goes into <directory>/ECG.txt
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        // Set the FilePath variable
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // first letter must be small

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}