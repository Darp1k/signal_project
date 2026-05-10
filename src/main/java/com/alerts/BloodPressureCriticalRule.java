package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;

public class BloodPressureCriticalRule implements ThresholdRule {

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
    public String getConditionName() {
        return "Critical Blood Pressure Alert";
    }
}