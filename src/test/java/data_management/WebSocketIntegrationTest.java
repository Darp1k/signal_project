package data_management;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.List;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.HospitalWebSocketListener;
import com.data_management.PatientRecord;

public class WebSocketIntegrationTest {

    private static WebSocketOutputStrategy serverStrategy;
    private static HospitalWebSocketListener clientListener;
    private static DataStorage storage;

    @BeforeAll
    static void setUp() throws Exception {
        storage = DataStorage.getInstance();

        serverStrategy = new WebSocketOutputStrategy(8081);
        Thread.sleep(1000);

        clientListener = new HospitalWebSocketListener(new URI("ws://localhost:8081"));
        clientListener.connectBlocking(); 
    }

    @Test
    void testWebSocket(){
        int patientId = 1010; 
        long timestamp = System.currentTimeMillis();
        
        serverStrategy.output(patientId, timestamp, "HeartRate", "75.0");

        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            System.out.println("Test interrupted while waiting for WebSocket communication.");
        }

        List<PatientRecord> records = storage.getRecords(patientId, timestamp - 5000, timestamp + 5000);
        
        assertFalse(records.isEmpty(), "The listener should have received the message and stored it in DataStorage.");
        assertEquals(75.0, records.get(0).getMeasurementValue());
        assertEquals("HeartRate", records.get(0).getRecordType());
        assertEquals(patientId, records.get(0).getPatientId());
    }

    @AfterAll
    static void stop() throws Exception {
        if (clientListener != null) {
            clientListener.closeBlocking();
        }
    }
}