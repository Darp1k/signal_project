package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.*;

import java.util.List;

public class DataStorageTest {

    /*
     * Test method tests the functionality or adding and getting patient records.
     * File with data is predefined 
     */
    @Test
    public void testAddAndGetRecords() {
        DataStorage storage = DataStorage.getInstance();
        DataSourceAdapter adapter = new DataSourceAdapter();
        DataListener fileListener = new FileDataListener("src/test/java/data_management", new DataParser(), adapter);

        try{
            adapter.readData(fileListener);
            Thread.sleep(2000); // Wait for the listener to process the file
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }

        List<PatientRecord> records = storage.getRecords(46, 0, System.currentTimeMillis());
        assertEquals(46, records.get(0).getPatientId());
        assertEquals("DiastolicPressure", records.get(0).getRecordType());
        assertEquals(72.0, records.get(0).getMeasurementValue());

    }
}
