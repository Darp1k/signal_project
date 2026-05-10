package com.alerts;

import java.util.List;

import com.data_management.PatientRecord;

public interface ThresholdRule {
    public boolean isExceeded(List<PatientRecord> records);
    public String getConditionName();
} 