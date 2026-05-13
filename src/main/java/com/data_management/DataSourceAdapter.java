package com.data_management;

import java.io.File;
import java.io.IOException;

import javax.xml.crypto.Data;

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
    public void readData(DataListener listener) throws IOException {
        listener.start();
    }

    // This method is responsible for routing the incoming patient record to the DataStorage for storage.
    public void routeData(PatientRecord record) {
        dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
    }
    
}
