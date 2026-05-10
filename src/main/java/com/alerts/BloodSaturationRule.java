package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodSaturationRule implements ThresholdRule {

    private String triggeredCondition = "Blood Saturation Alert";

    @Override
    public boolean isExceeded(List<PatientRecord> records) {
        List<PatientRecord> saturationRecords = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodSaturation")) {
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

    @Override
    public String getConditionName() {
        return triggeredCondition;
    }
}