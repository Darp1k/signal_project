package com.alerts;

// Base Factory class
public abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}

// Factory for Blood Pressure Alerts
class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodPressureAlert(patientId, condition, timestamp);
    }
}

// Factory for Blood Oxygen Alerts
class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, condition, timestamp);
    }
}

// Factory for ECG Alerts
class ECGAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, condition, timestamp);
    }
}
