package com.alerts;

import java.util.List;

import com.data_management.PatientRecord;

// threshold class for  rules that will check if certain condition is met
public interface ThresholdRule {
    public boolean isExceeded(List<PatientRecord> records);
    public Alert createAlert(String patientId, long timestamp);
} 