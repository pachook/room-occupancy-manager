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
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(3, 3, getGuests());
        assertEquals(3, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(738, result.getPremiumRoomResult().getRevenue());
        assertEquals(3, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(167.99, result.getEconomyRoomResult().getRevenue());
    }


    @Test
    void shouldBook6PremiumAnd4EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(7, 5, getGuests());
        assertEquals(6, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(1054, result.getPremiumRoomResult().getRevenue());
        assertEquals(4, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(189.99, result.getEconomyRoomResult().getRevenue());
    }

    @Test
    void shouldBook2PremiumAnd4EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(2, 7, getGuests());
        assertEquals(2, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(583, result.getPremiumRoomResult().getRevenue());
        assertEquals(4, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(189.99, result.getEconomyRoomResult().getRevenue());
    }

    @Test
    void shouldBook7PremiumAnd1EconomyRoom() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(7, 1, getGuests());
        assertEquals(7, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(1, result.getEconomyRoomResult().getRoomsOccupied());

        assertEquals(1153.99, result.getPremiumRoomResult().getRevenue());
        assertEquals(45.00, result.getEconomyRoomResult().getRevenue());
        //TBC - below values are not possible
        //assertEquals(1153, result.getPremiumRoomResult().getRevenue());
        //assertEquals(45.99, result.getEconomyRoomResult().getRevenue());
    }

    @Test
    void shouldBookPremiumOnly() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(7, 1,
                List.of(new Guest(101), new Guest(102)));
        assertEquals(2, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(203, result.getPremiumRoomResult().getRevenue());
        assertEquals(0, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(0.0, result.getEconomyRoomResult().getRevenue());
    }

    @Test
    void shouldBook1PremiumOnly() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(1, 1,
                List.of(new Guest(101), new Guest(102)));
        assertEquals(1, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(102, result.getPremiumRoomResult().getRevenue());
        assertEquals(0, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(0.0, result.getEconomyRoomResult().getRevenue());
    }

    @Test
    void shouldBookEconomyOnly() {
        OccupancyResponseDto result = occupancyManagerService.determineOccupancyDefaultPrice(3, 6,
                List.of(new Guest(80), new Guest(20)));
        assertEquals(0, result.getPremiumRoomResult().getRoomsOccupied());
        assertEquals(0, result.getPremiumRoomResult().getRevenue());
        assertEquals(2, result.getEconomyRoomResult().getRoomsOccupied());
        assertEquals(100.0, result.getEconomyRoomResult().getRevenue());
    }


    private List<Guest> getGuests() {
        return List.of(new Guest(23.0), new Guest(45.0), new Guest(155.0),
                new Guest(374.0), new Guest(22.0), new Guest(99.99),
                new Guest(100.0), new Guest(101.0), new Guest(115.0),
                new Guest(209.0));
    }

}