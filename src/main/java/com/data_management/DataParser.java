package com.data_management;

public class DataParser {
    
    /**
     * Parses a raw data string into a PatientRecord.
     * Handles both the File/Console output format and the TCP/WebSocket CSV format.
     * Format 1: "Patient ID: 1, Timestamp: 123456789, Label: ECG, Data: 0.5"
     * Format 2: "1, 123456789, ECG, 0.5"
     */
    public PatientRecord parse(String rawData) {
        try {
            int patientId;
            long timestamp;
            String label;
            String dataStr;

            // Check for the file/console output
            if (rawData.contains("Patient ID:")) {
                String[] parts = rawData.split(",");
                // Extract values after the colon
                patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                timestamp = Long.parseLong(parts[1].split(":")[1].trim());
                label = parts[2].split(":")[1].trim();
                dataStr = parts[3].split(":")[1].trim();
            } 
            // tcp/websocket output in csv format
            else {
                String[] parts = rawData.split(",");
                // Extract values directly
                patientId = Integer.parseInt(parts[0].trim());
                timestamp = Long.parseLong(parts[1].trim());
                label = parts[2].trim();
                dataStr = parts[3].trim();
            }

            // removing "%" in case with blood sturation
            if (dataStr.endsWith("%")) {
                dataStr = dataStr.replace("%", "");
            }
            
            if (label.equals("Alert")) {
                if (dataStr.equals("triggered")) {
                    dataStr = "1.0"; // Alert triggered
                } else {
                    dataStr = "0.0"; // Alert not triggered
                }
            }
            
            // Convert the cleaned data string to a double
            double measurementValue = Double.parseDouble(dataStr);
            
            return new PatientRecord(patientId, measurementValue, label, timestamp);
            
        } catch (Exception e) {
            System.err.println("Failed to parse line: " + rawData);
            return null;
        }
    }
}