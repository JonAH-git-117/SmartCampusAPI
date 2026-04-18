## SmartCampusAPI
This is a piece of coursework for a university module that covers 'client-server' architectures. 

## Overview
The Smart Campus API is a RESTful web service built using JAX-RS (Jersey 2.32) and Apache Tomcat 9. It provides a comprehensive interface for managing campus Rooms and Sensors, including historical reading tracking. The API follows REST principles including resource-based URIs, appropriate HTTP status codes, and HATEOAS-inspired discovery. All data is stored in-memory using static ArrayLists as per the coursework specification.

### Resource Hierarchy
- `/api/v1` — Is for the discovery endpoint
- `/api/v1/rooms` — Is for room management
- `/api/v1/sensors` — Is for sensor management
- `/api/v1/sensors/{sensorId}/readings` — Is for sensor reading history (sub-resource)

## How to Build and Run

### Prerequisites
- Apache NetBeans IDE 18
- Apache Tomcat 9.0.x
- JDK 8 or higher
- Maven (bundled with NetBeans)

### Steps
1. Clone the repository: `git clone https://github.com/JonAH-git-117/SmartCampusAPI.git`
2. Open NetBeans and select **File → Open Project**
3. Navigate to the cloned folder and open the project
4. Right-click the project → **Clean and Build**
5. Right-click the project → **Run**
6. The server will start on `http://localhost:8080/SmartCampusAPI`
7. Test the discovery endpoint at `http://localhost:8080/SmartCampusAPI/api/v1/`

## Sample curl Commands

### 1. Get all rooms
```bash
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/rooms
```
**Response (200 OK):**
```json
[
    {
        "id": "LIB-301",
        "name": "Library Quiet Study",
        "capacity": 50,
        "sensorIds": ["TEMP-001"]
    },
    {
        "id": "LAB-101",
        "name": "Computer Lab",
        "capacity": 30,
        "sensorIds": ["CO2-001"]
    }
]
```

### 2. Create a new room
```bash
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\": \"GYM-001\", \"name\": \"Campus Gym\", \"capacity\": 100}"
```

**Response (201 Created):**
```json
{
    "id": "GYM-001",
    "name": "Campus Gym",
    "capacity": 100,
    "sensorIds": []
}
```

### 3. Get sensors filtered by type
```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=Temperature"
```

**Response (200 OK):**
```json
[
    {
        "id": "TEMP-001",
        "type": "Temperature",
        "status": "ACTIVE",
        "currentValue": 22.5,
        "roomId": "LIB-301"
    }
]
```

### 4. Register a new sensor
```bash
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\": \"OCC-001\", \"type\": \"Occupancy\", \"status\": \"ACTIVE\", \"currentValue\": 0.0, \"roomId\": \"LIB-301\"}"
```

**Response (201 Created):**
```json
{
    "id": "OCC-001",
    "type": "Occupancy",
    "status": "ACTIVE",
    "currentValue": 0.0,
    "roomId": "LIB-301"
}
```

### 5. Post a reading to a sensor
```bash
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"value\": 23.5}"
```

**Response (201 Created):**
```json
{
    "id": "58829eeb-37ba-4f3e-ae1c-445a0cb464da",
    "timestamp": 1776516726528,
    "value": 23.5,
    "sensorId": "TEMP-001"
}
```
