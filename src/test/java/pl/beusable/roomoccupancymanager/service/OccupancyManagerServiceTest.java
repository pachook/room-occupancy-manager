package pl.beusable.roomoccupancymanager.service;

import org.junit.jupiter.api.Test;
import pl.beusable.roomoccupancymanager.domain.Guest;
import pl.beusable.roomoccupancymanager.domain.OccupancyResponseDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OccupancyManagerServiceTest {


    private final OccupancyManagerService occupancyManagerService = new OccupancyManagerService();

    @Test
    void shouldBook3PremiumAnd3EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancy(3, 3, getGuests());
        assertEquals(3, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(738, result.getPremiumRoomResult().getRevenue());
        assertEquals(3, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(167.99, result.getEconomyRoomResult().getRevenue());
    }


//    @Test
    void shouldBook6PremiumAnd4EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancy(7, 5, getGuests());
        assertEquals(6, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(1054, result.getPremiumRoomResult().getRevenue());
        assertEquals(4, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(189.99, result.getEconomyRoomResult().getRevenue());
    }

//    @Test
    void shouldBook2PremiumAnd4EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancy(2, 7, getGuests());
        assertEquals(2, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(583, result.getPremiumRoomResult().getRevenue());
        assertEquals(4, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(189.99, result.getEconomyRoomResult().getRevenue());
    }

//    @Test
    void shouldBook7PremiumAnd1EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancy(7, 1, getGuests());
        assertEquals(7, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(1153, result.getPremiumRoomResult().getRevenue());
        assertEquals(1, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(45.99, result.getEconomyRoomResult().getRevenue());
    }

    private List<Guest> getGuests() {
        return List.of(new Guest(23.0), new Guest(45.0), new Guest(155.0),
                new Guest(374.0), new Guest(22.0), new Guest(99.99),
                new Guest(100.0), new Guest(101.0), new Guest(115.0),
                new Guest(209.0));
    }

}