package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * interface that have to generate data for patient with a specific output strategy
 */
public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
