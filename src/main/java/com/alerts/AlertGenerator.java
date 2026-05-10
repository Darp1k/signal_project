package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private AlertManager alertManager;
    // private PatientIdentifier patientIdentifier; -- TODO
    private ArrayList<ThresholdRule> rules;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        alertManager = new AlertManager();
        rules = new ArrayList<>();
        this.addRule(new BloodPressureCriticalRule());
        this.addRule(new BloodPressureTrendRule());
        this.addRule(new BloodSaturationRule());
        this.addRule(new ECGAbnormalRule());
        this.addRule(new HypotensiveHypoxemiaRule());
        this.addRule(new ManualTriggerRule());
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> patientRecords = dataStorage.getRecords(patient.getId(), System.currentTimeMillis() - 600000, System.currentTimeMillis()); // get records from the last 10 minutes
        if (rules != null) {
            for (ThresholdRule rule : rules) {
                if (rule.isExceeded(patientRecords)) {
                    Alert alert = new Alert(patient.getId() + "", rule.getConditionName(), System.currentTimeMillis());
                    triggerAlert(alert);
                }
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        alertManager.dispatchAlert(alert);
    }

    public void addRule(ThresholdRule rule) {
        rules.add(rule);
    }
}
