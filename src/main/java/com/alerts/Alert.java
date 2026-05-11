package com.alerts;


public interface Alert {
    String getPatientId();
    long getTimestamp();
    String getCondition();
}


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


class BloodPressureAlert extends BasicAlert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}


class BloodOxygenAlert extends BasicAlert {
    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}


class ECGAlert extends BasicAlert {
    public ECGAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}