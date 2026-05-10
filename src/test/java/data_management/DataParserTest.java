package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.data_management.DataParser;
import com.data_management.PatientRecord;

class DataParserTest {

    @Test
    void testParseFileFormatString() {
        DataParser parser = new DataParser();
        String rawData1 = "Patient ID: 5, Timestamp: 1714376789050, Label: ECG, Data: 0.54";
        String rawData2 = "5,1714376789050,ECG,0.54"; // CSV format
        
        PatientRecord record1 = parser.parse(rawData1);
        PatientRecord record2 = parser.parse(rawData2);
        
        assertNotNull(record1);
        assertNotNull(record2);
        assertEquals(5, record1.getPatientId());
        assertEquals(5, record2.getPatientId());
        assertEquals("ECG", record1.getRecordType());
        assertEquals("ECG", record2.getRecordType());
        assertEquals(0.54, record1.getMeasurementValue());
        assertEquals(0.54, record2.getMeasurementValue());
        assertEquals(1714376789050L, record1.getTimestamp());
        assertEquals(1714376789050L, record2.getTimestamp());
    }
}