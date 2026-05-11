package alerts;

import javax.xml.crypto.Data;

import org.junit.jupiter.api.Test;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

class AlertGeneratorTest {

    @Test
    void testEvaluateData_TriggersAlert() {
        DataStorage storage = DataStorage.getInstance();
        long currentTime = System.currentTimeMillis();

        storage.addPatientData(1, 185.0, "SystolicPressure", currentTime);
        storage.addPatientData(1, 55.0, "DiastolicPressure", currentTime);

        storage.addPatientData(2, 120.0, "SystolicPressure", currentTime);
        storage.addPatientData(2, 135.0, "SystolicPressure", currentTime + 1000);
        storage.addPatientData(2, 150.0, "SystolicPressure", currentTime + 2000);

        storage.addPatientData(3, 89.0, "Saturation", currentTime);

        storage.addPatientData(4, 99.0, "Saturation", currentTime);
        storage.addPatientData(4, 92.0, "Saturation", currentTime + 1000);

        storage.addPatientData(5, 0.5, "ECG", currentTime);
        storage.addPatientData(5, 2.3, "ECG", currentTime + 1000);

        storage.addPatientData(6, 85.0, "SystolicPressure", currentTime);
        storage.addPatientData(6, 88.0, "Saturation", currentTime);

        storage.addPatientData(7, 1.0, "Alert", currentTime);

        // Initialize the generator
        AlertGenerator generator = new AlertGenerator(storage);

        // Loop through all patients  and evaluate them
        for (Patient patient : storage.getAllPatients()) {
            generator.evaluateData(patient);
        }
    }

    @Test
    void testEvaluateData_NoAlert() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(2, 120.0, "SystolicPressure", System.currentTimeMillis());
        Patient patient = storage.getAllPatients().get(0);

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);
    }
}