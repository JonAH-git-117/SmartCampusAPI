## SmartCampusAPI
This is a piece of coursework for a university module that covers 'client-server' architectures. 

## Table of Contents
- [Overview](#overview)
- [Resource Hierarchy](#resource-hierarchy)
- [How to Build and Run](#how-to-build-and-run)
- [Sample curl Commands](#sample-curl-commands)
- [Conceptual Report](#conceptual-report)

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

## Conceptual Report

### Part 1.1 — Project & Application Configuration
Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.

JAX-RS creates a new instance of a resource class for every incoming HTTP request, and this is commonly referred to as the per-request lifecycle. This means that any data stored as an instance variable inside a class such as RoomResource would be lost the moment that request finishes, making it impossible to use instance variables for a shared state.
To solve this, the project stores all data inside the MockDatabase class using static ArrayLists. This is because static fields belong to the class itself rather than any instance of said class and persist across all requests regardless of how many new instances JAX-RS creates. However, this does introduce a thread safety concern as if two requests arrive simultaneously and both attempts to write to the same static list, a race condition could occur and corrupt the data.

Jendrock, E., Cervera-Navarro, R., Evans, I., Haase, K. and Markito, W. (2014) The Java EE 7 Tutorial. Redwood City: Oracle Corporation. Available at: https://docs.oracle.com/javaee/7/tutorial/ (Accessed: 28 April 2026).

### Part 1.2 — The "Discovery" Endpoint
Question: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

HATEOAS makes an API self-describing by including links in responses that tell the client where it can go next, rather than requiring prior knowledge of every available endpoint. The DiscoveryResource class in this project implements this at GET /api/v1 by using UriInfo to dynamically construct links to /api/v1/rooms and /api/v1/sensors at runtime. This then means the links always reflect the correct base URL regardless of the environment the application is deployed in. Unlike static documentation, which can quickly become outdated, a HATEOAS-driven API updates its own links automatically if anything changes, keeping the client and server loosely coupled.

Fielding, R.T. (2000) Architectural Styles and the Design of Network-based Software Architectures. PhD thesis. University of California, Irvine. Available at: https://roy.gbiv.com/pubs/dissertation/top.htm (Accessed: 28 April 2026).

### Part 2.1 — Room Resource Implementation
Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client-side processing.

Returning only IDs keeps the payload lightweight but forces the client to make a separate GET request for every room it needs details on, which is known as the N+1 problem and adds significant latency if scaled. Returning full room objects, which is what the RoomResource class does in this project, gives the client the id, name, capacity and sensorIds fields in a single response. The payload is larger, but for a campus dashboard that needs to display room information immediately, avoiding those extra round trips is worth the trade-off.

Richardson, L. and Ruby, S. (2007) RESTful Web Services. Sebastopol: O'Reilly Media. Available at: https://www.oreilly.com/library/view/restful-web-services/9780596529260/ (Accessed: 28 April 2026).

### Part 2.2 — Room Deletion & Safety Logic
Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

Yes, the DELETE operation is idempotent in this implementation. The first time DELETE /api/v1/rooms/{roomId} is called on an empty room, RoomResource removes it from the MockDatabase static list and returns 204 No Content response. If the same request is sent again, RoomResource queries the MockDatabase, finds nothing, and returns 404 Not Found response. The status codes differ but the server state is the same both times, the room does not exist, which satisfies the definition of idempotency as per RFC 7231. Additionally, if a DELETE is attempted on a room that still has sensors in its sensorIds list, RoomResource throws a RoomNotEmptyException which the RoomNotEmptyExceptionMapper converts to a 409 Conflict, ensuring the MockDatabase is never left in an inconsistent state.

Fielding, R. and Reschke, J. (2014) RFC 7231: Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content. Fremont: Internet Engineering Task Force. Available at: https://datatracker.ietf.org/doc/html/rfc7231 (Accessed: 28 April 2026).

### Part 3.1 — Sensor Resource & Integrity
Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

The @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method in SensorResource tells the Jersey runtime to only accept requests where the Content-Type header is application/json. If a client sends text/plain or application/xml instead, Jersey detects the mismatch before the request reaches the method body and automatically returns a 415 Unsupported Media Type response. No application code executes at all, which means the Jackson deserialisation logic is never invoked and nothing invalid can reach the MockDatabase.

Hadley, M. and Sandoz, P. (2009) JSR 311: JAX-RS: The Java API for RESTful Web Services. Santa Clara: Sun Microsystems. Available at: https://jcp.org/en/jsr/detail?id=311 (Accessed: 28 April 2026).

### Part 3.2 — Filtered Retrieval & Search
Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

Embedding the type in the path like /api/v1/sensors/type/CO2 treats it as though it were a resource identifier, which is semantically wrong, it is a filter criterion and not a resource. It would also require a separate route in SensorResource and make it impossible to retrieve all sensors without a type of value. The @QueryParam approach used in this project keeps the filter optional, GET /api/v1/sensors returns everything from the MockDatabase, while GET /api/v1/sensors?type=Temperature filters the list dynamically. Moreover, it is also easier to extend, since additional filters like status or roomId could be added as further query parameters without changing the URL structure.

Richardson, L. and Ruby, S. (2007) RESTful Web Services. Sebastopol: O'Reilly Media. Available at: https://www.oreilly.com/library/view/restful-web-services/9780596529260/ (Accessed: 28 April 2026).

### Part 4.1 — The Sub-Resource Locator Pattern
Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?

Without the sub-resource locator pattern, all readings logic would have to sit inside SensorResource alongside all sensor management logic, which would make the class increasingly difficult to read and maintain as the API grows. Instead, SensorResource contains a locator method that delegates any request to /{sensorId}/readings across to a dedicated SensorReadingResource class at runtime. This means SensorReadingResource handles all reading history and POST logic in isolation, keeping each class focused on a single responsibility. In a large campus API managing many sensors and rooms, this separation makes the codebase far easier to test and modify without introducing unintended side effects.

Burke, B. (2013) RESTful Java with JAX-RS 2.0. 2nd edn. Sebastopol: O'Reilly Media. Available at: https://www.oreilly.com/library/view/restful-java-with/9781449361433/ (Accessed: 28 April 2026).

### Part 5.2 — Dependency Validation (422)
Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

A 404 response tells the client that the endpoint itself was not found, but when a client POSTs to /api/v1/sensors with a roomId like FAKE-999, the endpoint exists and the JSON is syntactically valid. The problem is that the roomId references a Room that does not exist in the MockDatabase, which is a semantic error rather than a missing resource. When SensorResource detects this, it throws a LinkedResourceNotFoundException which the LinkedResourceNotFoundExceptionMapper converts to a 422 Unprocessable Entity with a descriptive JSON error body. This gives the client a much more actionable response — it knows the issue is inside the payload, not with the URL itself.

Dusseault, L. (2007) RFC 4918: HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV). Fremont: Internet Engineering Task Force. Available at: https://datatracker.ietf.org/doc/html/rfc4918 (Accessed: 28 April 2026).

### Part 5.4 — The Global Safety Net (500)
Question: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

A raw stack trace exposes package names, class names, line numbers and third-party library versions like Jersey 2.32 and Jackson to anyone who sees it. An attacker could use that information to identify known vulnerabilities in those library versions via CVE databases and exploit them. The GlobalExceptionMapper in this project prevents this by implementing ExceptionMapper<Throwable>, which acts as a catch-all for any unexpected runtime errors such as NullPointerException or IndexOutOfBoundsException. Rather than letting a stack trace reach the client, it logs the real error internally using java.util.logging.Logger and returns only a safe generic 500 JSON response via the ErrorMessage model.

OWASP (2021) A05:2021 - Security Misconfiguration. Pensacola: Open Web Application Security Project. Available at: https://owasp.org/Top10/A05_2021-Security_Misconfiguration/ (Accessed: 28 April 2026).

### Part 5.5 — API Request & Response Logging Filters
Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?

Adding Logger calls manually to every method across RoomResource, SensorResource and SensorReadingResource would produce repeated boilerplate code throughout the entire codebase, making it harder to maintain and violating the DRY principle. If the logging format ever needed updating, every method would need changing individually. The LoggingFilter class solves this by implementing both ContainerRequestFilter and ContainerResponseFilter in one place, automatically logging the HTTP method and URI on every incoming request and the status code on every outgoing response. None of the resource classes need to be touched at all, keeping the business logic separate from the observability layer.

Hadley, M. and Sandoz, P. (2009) JSR 311: JAX-RS: The Java API for RESTful Web Services. Santa Clara: Sun Microsystems. Available at: https://jcp.org/en/jsr/detail?id=311 (Accessed: 28 April 2026).
