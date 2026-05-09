# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Project members
Makarov Petr - I6433588

## Uml models

1. AlerSystem
Path: uml_models/AlertSystem.png
Core of this system is AlertGenerator, it simulates reat time data, when the anomaly is detected
it creates an Alert and passes it to the AlertManager, which is passing it to the dedicated place
Threshold rule is an interface to provide multiple types of anomalies. This ensures that new
anomaly type is not going to break the AlertGenerator logic.

2. DataStorage
Path: uml_models/DataStorage.png
DataStorage class is the main data center that keeps all Patient data and manages the queries.
This model implements composition hierarchy: DataStorage keeps Patient data, and every Patient
has PatientRecords class. The access manager implemented to check whether the quiery from the 
staff is appropriate or not. Also old data is deleted by DataDeletionPolicy to not keep this 
logic inside of the DataStorage

3. PatientIdentifier
Path: uml_models/PatientIdentifier.png
Patient Identifier is relatively simple system to check whether generated patient matches the
real HospitalPatient. It uses IdentityManager to verify match. If the match is approved, then
query goes to the database, in other cases we log the mismatch via IdentityManager. 
HospitalDatabase is shown as an Interface by now to not guess the type of the real database

4. DataAccess
Path: uml_models/DataAccess.png
DataSourceAdapter implements the DataReader interface to store the data exactly in the DataStorage
We introduce the DataListener to provide extensibility in the way on how u want to retreive the
data. Three Listeners retreive the raw data(JSON or CSV), then standartise it via the DataParser and pass it 
straight to the DataSourceAdapter. With this model of accessing data we can be confident that
all information is going to be handled in the same secure way.


