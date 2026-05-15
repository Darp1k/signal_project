package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

// rule to check for blood pressure trend
public class BloodPressureTrendStrategy implements AlertStrategy {
    private AlertFactory factory = new BloodPressureAlertFactory();

    @Override
    public boolean checkAlert(List<PatientRecord> records) {
        List<PatientRecord> systolic = filterRecords(records, "SystolicPressure");
        List<PatientRecord> diastolic = filterRecords(records, "DiastolicPressure");

        return checkTrend(systolic) || checkTrend(diastolic);
    }

    private List<PatientRecord> filterRecords(List<PatientRecord> records, String type) {
        List<PatientRecord> filtered = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals(type)) {
                filtered.add(record);
            }
        }
        return filtered;
    }

    /**
     * ckeacks whether tree continuous records show an increasing
     * or decreasing trend of more than 10 mmHg
     * @param records patient records to check for the trend
     * @return boolean value, true -- triggered, false -- not triggered
     */

    private boolean checkTrend(List<PatientRecord> records) {
        if (records.size() < 3) return false;

        // Check the last 3 consecutive readings for a trend
        for (int i = 0; i <= records.size() - 3; i++) {
            double v1 = records.get(i).getMeasurementValue();
            double v2 = records.get(i + 1).getMeasurementValue();
            double v3 = records.get(i + 2).getMeasurementValue();

            boolean increasing = (v2 - v1 > 10) && (v3 - v2 > 10);
            boolean decreasing = (v1 - v2 > 10) && (v2 - v3 > 10);

            if (increasing || decreasing) {
                return true;
            }
        }
        return false;
    }

    /**
     * creates an alert using the specified alert factory and decorates it with medium priority
     */
    @Override
    public Alert createAlert(String patientId, long timestamp) {
        Alert alert = factory.createAlert(patientId, "Blood Pressure Trend Alert", timestamp);
        
        // A trend is concerning but usually not an immediate emergency
        alert = new PriorityAlertDecorator(alert, "MEDIUM");
        
        return alert;
    }
}