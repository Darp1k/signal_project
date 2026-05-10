package com.alerts;

public class AlertManager {
    public void dispatchAlert(Alert alert) {
        System.out.println("ALERT: Patient ID " + alert.getPatientId() + " - " + alert.getCondition() + " at " + alert.getTimestamp());
    }
}
