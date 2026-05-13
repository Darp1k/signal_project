package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;

// rule to check for hypotensive hypoxemia condition
public class HypotensiveHypoxemiaRule implements ThresholdRule {
    private AlertFactory factory = new BloodOxygenAlertFactory();

    // method to check the combined critical condition
    @Override
    public boolean isExceeded(List<PatientRecord> records) {
        boolean hasLowSystolic = false;
        boolean hasLowSaturation = false;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicPressure") && record.getMeasurementValue() < 90.0) {
                hasLowSystolic = true;
            }
            if (record.getRecordType().equals("Saturation") && record.getMeasurementValue() < 92.0) {
                hasLowSaturation = true;
            }
        }

        return hasLowSystolic && hasLowSaturation;
    }

    /**
     * creates an alert with critical pririty and very fast repetition since this is a life-threatening condition
     */
    @Override
    public Alert createAlert(String patientId, long timestamp) {
        Alert alert = factory.createAlert(patientId, "Hypotensive Hypoxemia Alert", timestamp);
        
        // if both conditions are met it is definirely critical
        alert = new PriorityAlertDecorator(alert, "CRITICAL");
        alert = new RepeatedAlertDecorator(alert, 10, 15000); // very fast repeat
        
        return alert;
    }
}