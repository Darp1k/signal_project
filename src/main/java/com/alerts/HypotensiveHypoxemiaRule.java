package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;

public class HypotensiveHypoxemiaRule implements ThresholdRule {

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

    @Override
    public String getConditionName() {
        return "Hypotensive Hypoxemia Alert";
    }
}