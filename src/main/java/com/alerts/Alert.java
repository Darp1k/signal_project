package com.alerts;

// interface that all alerts will implement
public interface Alert {
    String getPatientId();
    long getTimestamp();
    String getCondition();
}

// Basic implementation of the Alert interface, other specific alert types will extend this
class BasicAlert implements Alert {
    private String patientId;
    private long timestamp;
    private String condition;

    public BasicAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.timestamp = timestamp;
        this.condition = condition;
    }

    @Override
    public String getPatientId() { return patientId; }

    @Override
    public long getTimestamp() { return timestamp; }

    @Override
    public String getCondition() { return condition; }
}

// class for blood pressure alerts
class BloodPressureAlert extends BasicAlert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}

// class for blood oxygen alerts
class BloodOxygenAlert extends BasicAlert {
    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}

// class for ECG alerts
class ECGAlert extends BasicAlert {
    public ECGAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}