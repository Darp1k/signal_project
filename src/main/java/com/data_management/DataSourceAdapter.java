package com.data_management;

import java.io.IOException;

public class DataSourceAdapter implements DataReader{
    private DataStorage dataStorage;

    public DataSourceAdapter(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readData'");
    }

    // This method is responsible for routing the incoming patient record to the DataStorage for storage.
    public void routeData(PatientRecord record) {
        dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
    }
    
}
