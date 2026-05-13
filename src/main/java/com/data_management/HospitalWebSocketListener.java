package com.data_management;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class HospitalWebSocketListener extends WebSocketClient implements DataListener {
    private DataParser dataParser;
    private DataStorage dataStorage;

    /**
     * Constructs a new WebSocketClient.
     * @param serverUri The URI of the WebSocket server to connect to
     */
    public HospitalWebSocketListener(URI serverUri) {
        super(serverUri);
        this.dataParser = new DataParser();
        this.dataStorage = DataStorage.getInstance();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("WebSocket Client: Successfully connected to the server.");
    }

    @Override
    public void onMessage(String message) {
        try {
            // Parse the incoming CSV-formatted string into a PatientRecord object
            PatientRecord record = dataParser.parse(message);
            
            // If the data was incorrect, parser returns null. 
            if (record != null) {
                // Utilize the existing DataStorage class to store the parsed information
                dataStorage.addPatientData(
                    record.getPatientId(), 
                    record.getMeasurementValue(), 
                    record.getRecordType(), 
                    record.getTimestamp()
                );
            } else {
                System.err.println("WebSocket Client: Received incorrect data that could not be parsed: " + message);
            }
        } catch (Exception e) {
            // Catching unexpected errors during data processing to prevent client from crashing
            System.err.println("WebSocket Client: Unexpected error processing message: " + message);
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket Client: Connection closed. Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket Client: An error occurred in the connection.");
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        try {
            // Example usage connecting to localhost on port 8080
            HospitalWebSocketListener client = new HospitalWebSocketListener(new URI("ws://localhost:8080"));
            client.start();
        } catch (URISyntaxException e) {
            System.err.println("Invalid WebSocket URI format.");
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        this.connect();
    }

    @Override
    public void stop() {
        this.close();
    }
}
