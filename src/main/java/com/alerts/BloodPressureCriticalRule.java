package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;

public class BloodPressureCriticalRule implements ThresholdRule {
    private AlertFactory factory = new BloodPressureAlertFactory();

    @Override
    public boolean isExceeded(List<PatientRecord> records) {
        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();

            if (type.equals("SystolicPressure") && (value > 180 || value < 90)) {
                return true;
            }
            if (type.equals("DiastolicPressure") && (value > 120 || value < 60)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Alert createAlert(String patientId, long timestamp) {
        Alert alert = factory.createAlert(patientId, "Critical Blood Pressure Alert", timestamp);
        
        // Highly critical, make it high priority and repeat it so it isn't missed
        alert = new PriorityAlertDecorator(alert, "CRITICAL");
        alert = new RepeatedAlertDecorator(alert, 5, 30000); // Repeat 5 times every 30 seconds
        
        return alert;
    }
}