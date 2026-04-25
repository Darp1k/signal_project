package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * interface that have to generate data for patient with a specific output strategy
 */
public interface PatientDataGenerator {
    /**
     * Generates patient data for the specified patient ID using the given output strategy.
     *
     * @param patientId The ID of the patient for whom to generate data.
     * @param outputStrategy The strategy to use for outputting the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
