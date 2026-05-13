package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;

// rule to check for critical blood pressure values
public class BloodPressureCriticalRule implements ThresholdRule {
    private AlertFactory factory = new BloodPressureAlertFactory();


    @Override
    /**
     * Checks if any of the patient's blood pressure 
     * reaches the critical threshold
     * Systolic > 180 or < 90
     * Diastolic > 120 or < 60
     */
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

    /**
     * creates an alert using the specified alert factory and decorates it with high priority and repeat decorators
     */
    @Override
    public Alert createAlert(String patientId, long timestamp) {
        Alert alert = factory.createAlert(patientId, "Critical Blood Pressure Alert", timestamp);
        
        // Highly critical, make it high priority and repeat it so it isn't missed
        alert = new PriorityAlertDecorator(alert, "CRITICAL");
        alert = new RepeatedAlertDecorator(alert, 5, 30000); // Repeat 5 times every 30 seconds
        
        return alert;
    }
}