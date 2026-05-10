package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;
import java.util.stream.Collectors;

public class ECGAbnormalRule implements ThresholdRule {

    // had to assume that exceeding the average by 20% is considered an anomaly
    private static final double EXCEEDED_THRESHOLD = 1.20; 

    @Override
    public boolean isExceeded(List<PatientRecord> records) {
        List<PatientRecord> ecgRecords = records.stream()
                .filter(r -> r.getRecordType().equals("ECG"))
                .collect(Collectors.toList());

        if (ecgRecords.isEmpty()) return false;

        // Calculate the average
        double sum = 0;
        for (PatientRecord record : ecgRecords) {
            sum += Math.abs(record.getMeasurementValue()); // Using absolute value for waveforms
        }
        double average = sum / ecgRecords.size();

        // Check if any peak exceeds the average
        for (PatientRecord record : ecgRecords) {
            if (Math.abs(record.getMeasurementValue()) > average * EXCEEDED_THRESHOLD) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getConditionName() {
        return "Abnormal ECG Data Alert";
    }
}