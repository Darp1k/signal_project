package alerts;

import org.junit.jupiter.api.Test;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

class AlertGeneratorTest {

    @Test
    void testEvaluateData_TriggersAlert() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 195.0, "SystolicPressure", System.currentTimeMillis()); // Critical high systolic pressure
        Patient patient = storage.getAllPatients().get(0);

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);
    }

    @Test
    void testEvaluateData_NoAlert() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(2, 120.0, "SystolicPressure", System.currentTimeMillis());
        Patient patient = storage.getAllPatients().get(0);

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);
    }
}