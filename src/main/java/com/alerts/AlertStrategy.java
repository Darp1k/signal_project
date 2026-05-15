package com.alerts;

import java.util.List;

import com.data_management.PatientRecord;

// threshold class for  rules that will check if certain condition is met
public interface AlertStrategy {
    public boolean checkAlert(List<PatientRecord> records);
    public Alert createAlert(String patientId, long timestamp);
} 