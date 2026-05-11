package com.data_management;

import java.io.File;
import java.io.IOException;

public class DataSourceAdapter implements DataReader{
    private DataStorage dataStorage;

    public DataSourceAdapter() {
        this.dataStorage = DataStorage.getInstance();
    }

    /**
     * Reads data from the specified FOLDER WITH FILES(not files itself) and routes it to the data storage.
     *
     * @param filePath the path to the folder containing the data files
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Override
    public void readData(String filePath) throws IOException {
        DataParser parser = new DataParser();
        File dir = new File(filePath); 
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    FileDataListener listener = new FileDataListener(file.getAbsolutePath(), parser, this);
                    listener.start(); // Starts tailing the file in the background
                }
            } else {
                System.err.println("No .txt files found in the directory: " + filePath);
            }
        }
    }

    // This method is responsible for routing the incoming patient record to the DataStorage for storage.
    public void routeData(PatientRecord record) {
        dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
    }
    
}
