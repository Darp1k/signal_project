package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

// rule to check for blood saturation levels and trends
public class BloodSaturationStrategy implements AlertStrategy {

    private AlertFactory factory = new BloodOxygenAlertFactory();
    private String triggeredCondition = "Blood Saturation Alert";

    @Override
    public boolean checkAlert(List<PatientRecord> records) {
        List<PatientRecord> saturationRecords = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("Saturation")) {
                saturationRecords.add(record);
            }
        }

        if (saturationRecords.isEmpty()) return false;

        // check for low saturation (< 92%)
        for (PatientRecord record : saturationRecords) {
            if (record.getMeasurementValue() < 92.0) {
                triggeredCondition = "Low Blood Saturation Alert";
                return true;
            }
        }

        // check for a rapid drop
        for (int i = 0; i < saturationRecords.size(); i++) {
            for (int j = i + 1; j < saturationRecords.size(); j++) {
                double earlierValue = saturationRecords.get(i).getMeasurementValue();
                double laterValue = saturationRecords.get(j).getMeasurementValue();
                
                if (earlierValue - laterValue >= 5.0) {
                    triggeredCondition = "Rapid Saturation Drop Alert";
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * creates an alert using the specified alert factory and decorates it with High priority
     * and repetition once every minute for 3 minutes
     */
    @Override
    public Alert createAlert(String patientId, long timestamp) {
        Alert alert = factory.createAlert(patientId, triggeredCondition, timestamp);
        
        // Oxygen issues are highly critical
        alert = new PriorityAlertDecorator(alert, "HIGH");
        alert = new RepeatedAlertDecorator(alert, 3, 60000); 
        
        return alert;
    }
}