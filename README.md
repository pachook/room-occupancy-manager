# Room Occupancy Manager

## Description
This is a room occupancy optimization tool for hotel clients. It assigns guests to rooms based on their willingness to pay, optimizing the use of Premium and Economy rooms.

## Requirements
- Java 17
- Maven
  
## Running the Project
1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn spring-boot:run` to start the application.

## Testing
1. Run `mvn test` to execute the tests.

## API
### Optimize Room Occupancy
- URL: `/api/occupancy-manager`
- Method: `POST`
- Parameters:
  - `freePremiumRooms` (int)
  - `freeEconomyRooms` (int)
- Body: JSON array of guest willingness to pay
- Example request(sample postman collection at /src/main/resources/postman-collection/):
- POST localhost:8080/api/occupancy-manager?freePremiumRooms=3&freeEconomyRooms=3
  ```json
  {
    "guests" : [23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0]
  }

 - Example response:
  ```json
 {
    "premiumRoomResult": {
        "roomsOccupied": 3,
        "revenue": 738.0
    },
    "economyRoomResult": {
        "roomsOccupied": 3,
        "revenue": 167.99
    }
 }
```

## Technical notes
### Sorting and Allocation

The guests are sorted by their willingness to pay in order. This allows the algorithm to efficiently allocate guests to rooms by their payment capability.
### Binary Search for Splitting

The algorithm uses a binary search to find the split point between guests willing to pay EUR 100 or more and those who are willing to pay less. The findSplitIndex method finds the first guest willing to pay less than EUR 100. This binary search ensures that the algorithm runs efficiently even for larger datasets.
### Allocation Logic

- Premium Rooms Allocation:
        Guests willing to pay EUR 100 or more are allocated to Premium rooms first.
        Guests willing to pay less than EUR 100 are allocated to Premium rooms if there are no free Economy rooms left.

- Economy Rooms Allocation:
        Guests willing to pay less than EUR 100 are allocated to Economy rooms.
        If Economy rooms are full, these guests are then considered for Premium rooms.

### Project Structure

- src/main/java: Contains the main application code.
- src/test/java: Contains the test cases for the application.
- src/main/resources: Includes configuration files and sample data.
- pom.xml: Maven configuration file for managing dependencies and build lifecycle.

### Key Classes and Functions

- OccupancyManagerApplication: Main class for running the Spring Boot application.
- OccupancyManagerController: REST controller handling the API endpoints.
- OccupancyManagerService: Service layer containing business logic for room allocation.
- Guest: Model class representing a guest with their willingness to pay.    

### Build and Dependency Management

- Maven: Used for build automation and dependency management. Key dependencies include:
  - Spring Boot Starter Web: For creating RESTful web services.
  - Spring Boot Starter Test: For testing the application.
  - Lombok: For reducing boilerplate code (e.g., getters, setters, constructors).

### Configuration

- application.properties: Configuration file for setting application-specific properties, such as server port and logging levels.

### Deployment

- The application can be packaged into a JAR file using mvn clean package and deployed on any server supporting Java.

### Testing

- Unit tests are written using JUnit and Spring Boot Test framework.    


