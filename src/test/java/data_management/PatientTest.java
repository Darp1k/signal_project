package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public class PatientTest {

    @Test
    /**
     * tests the getrecords method from patient.java, ensuring that only the expectd time range will be returned
     */
    public void testGetRecords() {
        
        Patient patient = new Patient(1);
        patient.addRecord(100.0, "HeartRate", 1000L); // earlier than range
        patient.addRecord(105.0, "HeartRate", 2000L); // inside range
        patient.addRecord(110.0, "HeartRate", 3000L); // Inside range
        patient.addRecord(115.0, "HeartRate", 4000L); // Too later

        
        List<PatientRecord> records = patient.getRecords(1500L, 3500L);

        // verify the results
        assertEquals(2, records.size(), "Should only return the 2 records within the time range");
        assertEquals(105.0, records.get(0).getMeasurementValue());
        assertEquals(110.0, records.get(1).getMeasurementValue());
    }
}