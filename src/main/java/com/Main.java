package com;

import com.data_management.DataStorage;
import com.cardio_generator.HealthDataSimulator;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(java.util.Arrays.copyOfRange(args, 1, args.length)); // Pass remaining args to DataStorage
        } else if (args.length > 0 && args[0].equals("HealthDataSimulator")) {
            try{
                HealthDataSimulator.main(args);
            } catch (Exception e) {
                System.err.println("Error starting HealthDataSimulator: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
