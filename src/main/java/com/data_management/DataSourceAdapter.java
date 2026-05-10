package com.data_management;

import java.io.File;
import java.io.IOException;

public class DataSourceAdapter implements DataReader{
    private DataStorage dataStorage;

    public DataSourceAdapter(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        DataParser parser = new DataParser();
        File dir = new File("/Users/petrmakarov/Java_Projects/SE_Project/signal_project/output"); 
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    FileDataListener listener = new FileDataListener(file.getAbsolutePath(), parser, this);
                    listener.start(); // Starts tailing the file in the background
                }
            }
        }
    }

    // This method is responsible for routing the incoming patient record to the DataStorage for storage.
    public void routeData(PatientRecord record) {
        dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
    }
    
}
