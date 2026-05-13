package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class ECGAbnormalRule implements ThresholdRule {
    // because ecg is waveform data, it will always has peaks, meaning that calculating a threshold for mean
    // is not very useful since every heartbeat(spike) will be above average(~0). The unhealthy condition is
    // when the ecg peak (absolut) value is higher that 1.5. 
    private static final double THRESHOLD = 1.5; 
    private AlertFactory factory = new ECGAlertFactory();


    @Override
    public boolean isExceeded(List<PatientRecord> records) {
        List<PatientRecord> ecgRecords = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("ECG")) {
                ecgRecords.add(record);
            }
        }

        if (ecgRecords.isEmpty()) return false;

        // Check if any peak exceeds the average
        for (PatientRecord record : ecgRecords) {
            if (Math.abs(record.getMeasurementValue()) > THRESHOLD) {
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
        Alert alert = factory.createAlert(patientId, "Abnormal ECG Data Alert", timestamp);

        // Heart issues are always high priority
        alert = new PriorityAlertDecorator(alert, "HIGH");

        return alert;
    }
}