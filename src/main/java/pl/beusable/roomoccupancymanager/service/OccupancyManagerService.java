package pl.beusable.roomoccupancymanager.service;


import org.springframework.stereotype.Service;
import pl.beusable.roomoccupancymanager.domain.Guest;
import pl.beusable.roomoccupancymanager.domain.OccupancyResponseDto;
import pl.beusable.roomoccupancymanager.domain.RoomBookingResult;

import java.util.List;

@Service
public class OccupancyManagerService {
    private final static double DEFAULT_PRICE = 100.00;

    private static int findSplitIndex(Double[] arr, double target) {

        int start = 0, end = arr.length - 1;
        // Minimum size of the array should be 1
        if (end == 0) return -1;
        // If target lies beyond the max element, than the index of strictly smaller
        // value than target should be (end - 1)
        if (target > arr[end]) return end;

        int ans = -1;
        while (start <= end) {
            int mid = (start + end) / 2;

            // Move to the left side if the target is smaller
            if (arr[mid] >= target) {
                end = mid - 1;
            }

            // Move right side
            else {
                ans = mid;
                start = mid + 1;
            }
        }
        return ans;
    }

    public OccupancyResponseDto determineOccupancyDefaultPrice(int freePremiumRooms, int freeEconomyRooms, List<Guest> guests) {

        return determineOccupancy(freePremiumRooms, freeEconomyRooms, DEFAULT_PRICE, guests);
    }

    public OccupancyResponseDto determineOccupancy(int freePremiumRooms, int freeEconomyRooms, double limitPrice, List<Guest> guests) {
        Double[] guestArraySorted = guests.stream()
                .map(Guest::getDesiredPrice)
                .sorted().toArray(Double[]::new);

        int splitIndex = findSplitIndex(guestArraySorted, limitPrice);

        // Allocate guests to Premium and Economy rooms
        int premiumRoomsOccupied;
        double premiumRevenue = 0;
        int economyRoomsOccupied;
        double economyRevenue = 0;

        int lastIndex = guestArraySorted.length - 1;

        //check how many rooms are available
        premiumRoomsOccupied = Math.min(lastIndex - splitIndex, freePremiumRooms);
        economyRoomsOccupied = Math.min(splitIndex + 1, freeEconomyRooms);
        //special case - transfer guest to higher class
        if (freePremiumRooms + freeEconomyRooms < guestArraySorted.length && splitIndex + 1 > economyRoomsOccupied) {
            premiumRoomsOccupied = freePremiumRooms;
            economyRoomsOccupied = Math.min(lastIndex - freePremiumRooms, freeEconomyRooms);
        }

        //where economy guests end ?
        int economyEndIndex = Math.min(lastIndex - premiumRoomsOccupied, splitIndex);

        //calculate revenue
        for (int i = 0; i < Math.max(premiumRoomsOccupied,economyRoomsOccupied); i++) {
            if (i < premiumRoomsOccupied) {
                premiumRevenue += guestArraySorted[lastIndex - i];
            }
            if (i < economyRoomsOccupied) {
                economyRevenue += guestArraySorted[economyEndIndex - i];
            }

        }

        return OccupancyResponseDto.builder()
                .premiumRoomResult(RoomBookingResult.builder()
                        .roomsOccupied(premiumRoomsOccupied)
                        .revenue(premiumRevenue)
                        .build())
                .economyRoomResult(RoomBookingResult.builder()
                        .roomsOccupied(economyRoomsOccupied)
                        .revenue(economyRevenue)
                        .build())
                .build();
    }
}
