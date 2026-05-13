package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;

// rule to check for manual triggers from patients or nurses
public class ManualTriggerRule implements ThresholdRule {

    // method to check for any manual trigger(1.0 with type "Alert")
    @Override
    public boolean isExceeded(List<PatientRecord> records) {
        for (PatientRecord record : records) {
            // "triggered" is 1.0
            if (record.getRecordType().equals("Alert") && record.getMeasurementValue() == 1.0) {
                return true; 
            }
        }
        return false;
    }

    /**
     * creates an alert using the specified alert factory and decorates it with High priority
     */
    @Override
    public Alert createAlert(String patientId, long timestamp) {
        Alert alert = new BasicAlert(patientId, "Manual Patient/Nurse Triggered Alert", timestamp);
        
        // Manual triggers should always be high priority
        alert = new PriorityAlertDecorator(alert, "HIGH");
        
        return alert;
    }
}