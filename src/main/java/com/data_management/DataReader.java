package com.data_management;

import java.io.IOException;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @param filePath the path to the file containing the data
     * @throws IOException if there is an error reading the data
     */
    void readData(String filePath) throws IOException;
}
