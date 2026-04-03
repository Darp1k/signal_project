package com.cardio_generator.outputs;

/**
 * interface to specify the output strategy
 */
public interface OutputStrategy {
    /**
     * @param patientId
     * @param timestamp im milliseconds
     * @param label medical label
     * @param data medical data that was computed
     */
    void output(int patientId, long timestamp, String label, String data);
}
