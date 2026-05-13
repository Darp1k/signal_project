package com.data_management;

import java.io.IOException;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param listener the DataListener that will process the incoming data
     * @throws IOException if there is an error reading the data
     */
    void readData(DataListener listener) throws IOException;
}
