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


Example response:
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
