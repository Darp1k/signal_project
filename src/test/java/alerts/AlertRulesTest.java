package alerts;

import com.alerts.*;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlertRulesTest {

    // critical blood pressure rule tests
    @Test
    void testBloodPressureCriticalRule() {
        BloodPressureCriticalStrategy rule = new BloodPressureCriticalStrategy();
        long t = System.currentTimeMillis();

        // Safe limits
        List<PatientRecord> normal = Arrays.asList(
                new PatientRecord(1, 120, "SystolicPressure", t),
                new PatientRecord(1, 80, "DiastolicPressure", t)
        );
        assertFalse(rule.checkAlert(normal), "Normal blood pressure should not trigger an alert.");

        // Critical High Systolic (> 180)
        assertTrue(rule.checkAlert(Collections.singletonList(
                new PatientRecord(1, 181, "SystolicPressure", t))), "Systolic > 180 should trigger alert.");

        // Critical Low Diastolic (< 60)
        assertTrue(rule.checkAlert(Collections.singletonList(
                new PatientRecord(1, 59, "DiastolicPressure", t))), "Diastolic < 60 should trigger alert.");
    }

    // blood pressure trend rule tests
    @Test
    void testBloodPressureTrendRule() {
        BloodPressureTrendStrategy rule = new BloodPressureTrendStrategy();
        long t = System.currentTimeMillis();

        // Increasing trend differences > 10
        List<PatientRecord> increasing = Arrays.asList(
                new PatientRecord(1, 120, "SystolicPressure", t),
                new PatientRecord(1, 131, "SystolicPressure", t + 1000),
                new PatientRecord(1, 142, "SystolicPressure", t + 2000)
        );
        assertTrue(rule.checkAlert(increasing), "3 consecutive increasing pressure values should trigger alert.");

        // Decreasing trend differences > 10
        List<PatientRecord> decreasing = Arrays.asList(
                new PatientRecord(1, 100, "DiastolicPressure", t),
                new PatientRecord(1, 89, "DiastolicPressure", t + 1000),
                new PatientRecord(1, 78, "DiastolicPressure", t + 2000)
        );
        assertTrue(rule.checkAlert(decreasing), "3 consecutive decreasing diastolic pressure values should trigger alert.");

        // stable values (differences <= 10)
        List<PatientRecord> stable = Arrays.asList(
                new PatientRecord(1, 120, "SystolicPressure", t),
                new PatientRecord(1, 125, "SystolicPressure", t + 1000),
                new PatientRecord(1, 128, "SystolicPressure", t + 2000)
        );
        assertFalse(rule.checkAlert(stable), "Stable blood pressure should not trigger alert.");
    }

    // blooad saturation rule tests
    @Test
    void testBloodSaturationRule() {
        BloodSaturationStrategy rule = new BloodSaturationStrategy();
        long t = System.currentTimeMillis();

        // Low saturation (< 92%)
        List<PatientRecord> lowSat = Collections.singletonList(
                new PatientRecord(1, 91, "Saturation", t)
        );
        assertTrue(rule.checkAlert(lowSat), "Saturation < 92% should trigger alert.");
        

        // Rapid drop (>= 5% drop across any two points)
        List<PatientRecord> dropSat = Arrays.asList(
                new PatientRecord(1, 98, "Saturation", t),
                new PatientRecord(1, 93, "Saturation", t + 1000)
        );
        assertTrue(rule.checkAlert(dropSat), "Saturation drop of 5% should trigger alert.");

        // Normal saturation
        List<PatientRecord> normalSat = Arrays.asList(
                new PatientRecord(1, 98, "Saturation", t),
                new PatientRecord(1, 97, "Saturation", t + 1000)
        );
        assertFalse(rule.checkAlert(normalSat), "Normal saturation should not trigger alert.");
    }

    // ECG anomaly tests
    @Test
    void testECGAbnormalRule() {
        ECGAbnormalStrategy rule = new ECGAbnormalStrategy();
        long t = System.currentTimeMillis();

        // Normal ECG (Peak stays within 20% of the average)
        List<PatientRecord> normalEcg = Arrays.asList(
                new PatientRecord(1, 1.0, "ECG", t),
                new PatientRecord(1, 1.1, "ECG", t + 1000),
                new PatientRecord(1, 0.9, "ECG", t + 2000)
        );
        assertFalse(rule.checkAlert(normalEcg), "Stable ECG should not trigger alert.");

        // Abnormal peak (Peak > 1.2 * average)
        List<PatientRecord> abnormalEcg = Arrays.asList(
                new PatientRecord(1, 1.0, "ECG", t),
                new PatientRecord(1, 1.0, "ECG", t + 1000),
                new PatientRecord(1, 2.0, "ECG", t + 2000)
        );
        assertTrue(rule.checkAlert(abnormalEcg), "Peak exceeding 20% of average should trigger alert.");
    }

    // mixed alert test
    @Test
    void testHypotensiveHypoxemiaRule() {
        HypotensiveHypoxemiaStrategy rule = new HypotensiveHypoxemiaStrategy();
        long t = System.currentTimeMillis();

        // Systolic < 90 AND Saturation < 92
        List<PatientRecord> triggered = Arrays.asList(
                new PatientRecord(1, 89, "SystolicPressure", t),
                new PatientRecord(1, 91, "Saturation", t)
        );
        assertTrue(rule.checkAlert(triggered), "Concurrent low systolic and saturation should trigger alert.");

        // Only one condition met
        List<PatientRecord> partial = Arrays.asList(
                new PatientRecord(1, 95, "SystolicPressure", t), // Normal
                new PatientRecord(1, 90, "Saturation", t)       // Low
        );
        assertFalse(rule.checkAlert(partial), "Only one condition met should NOT trigger alert.");
    }

    // manual trigger rule tests
    @Test
    void testManualTriggerRule() {
        ManualTriggerStrategy rule = new ManualTriggerStrategy();
        long t = System.currentTimeMillis();

        // Triggered (Value = 1.0)
        assertTrue(rule.checkAlert(Collections.singletonList(
                new PatientRecord(1, 1.0, "Alert", t))), "Alert value 1.0 should trigger alert.");

        // Resolved / Not Triggered (Value = 0.0)
        assertFalse(rule.checkAlert(Collections.singletonList(
                new PatientRecord(1, 0.0, "Alert", t))), "Alert value 0.0 should NOT trigger alert.");
    }
}