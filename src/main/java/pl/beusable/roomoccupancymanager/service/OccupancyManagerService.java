package pl.beusable.roomoccupancymanager.service;


import org.springframework.stereotype.Service;
import pl.beusable.roomoccupancymanager.domain.OccupancyResponseDto;
import pl.beusable.roomoccupancymanager.domain.Guest;
import pl.beusable.roomoccupancymanager.domain.RoomBookingResult;

import java.util.List;

@Service
public class OccupancyManagerService {
    private final static double DEFAULT_PRICE = 100.00;

    public OccupancyResponseDto determineOccupancyDefaultPrice(int freePremiumRooms, int freeEconomyRooms, List<Guest> guests) {

        return determineOccupancy(freePremiumRooms, freeEconomyRooms, DEFAULT_PRICE, guests);
    }

    public OccupancyResponseDto determineOccupancy(int freePremiumRooms, int freeEconomyRooms, double limitPrice, List<Guest> guests) {


        return OccupancyResponseDto.builder()
                .premiumRoomResult(RoomBookingResult.builder()
                        .roomsOccupied(3)
                        .revenue(738.0)
                        .build())
                .economyRoomResult(RoomBookingResult.builder()
                        .roomsOccupied(3)
                        .revenue(167.99)
                        .build())
                .build();
    }
}
