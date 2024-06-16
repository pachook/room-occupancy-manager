package pl.beusable.roomoccupancymanager.service;


import org.springframework.stereotype.Service;
import pl.beusable.roomoccupancymanager.domain.Guest;
import pl.beusable.roomoccupancymanager.domain.OccupancyResponseDto;
import pl.beusable.roomoccupancymanager.domain.Room;
import pl.beusable.roomoccupancymanager.domain.RoomBookingResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OccupancyManagerService {
    private final static double DEFAULT_PRICE = 100.00;

    private static void bookRooms(int freeTargetRooms, int freeBackupRooms, Map<Boolean, List<Double>> premiumGuestMap, List<Room> targetRooms, boolean isPremiumRoom, boolean isTransferable) {
        for (int i = 0; i < freeTargetRooms; i++) {
            List<Double> targetGuests = premiumGuestMap.get(isPremiumRoom);
            Room room = new Room();
            if (!targetGuests.isEmpty()) {
                room.setBookingPrice(targetGuests.get(0));
                room.setRoomAvailable(false);
                targetGuests.remove(0);
                targetRooms.add(i, room);
            } else {
                List<Double> backupGuests = premiumGuestMap.get(!isPremiumRoom);
                if (isTransferable && backupGuests.size() > freeBackupRooms) {
                    room.setBookingPrice(backupGuests.get(0));
                    room.setRoomAvailable(false);
                    backupGuests.remove(0);
                    targetRooms.add(i, room);
                }
            }
        }
    }

    public OccupancyResponseDto determineOccupancyDefaultPrice(int freePremiumRooms, int freeEconomyRooms, List<Guest> guests) {

        return determineOccupancy(freePremiumRooms, freeEconomyRooms, DEFAULT_PRICE, guests);
    }

    public OccupancyResponseDto determineOccupancy(int freePremiumRooms, int freeEconomyRooms, double limitPrice, List<Guest> guests) {
        List<Room> premiumRooms = new ArrayList<>(freePremiumRooms);
        List<Room> economyRooms = new ArrayList<>(freeEconomyRooms);

        Map<Boolean, List<Double>> premiumGuestMap = guests.stream()
                .map(Guest::getDesiredPrice)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.partitioningBy(price -> price >= limitPrice));

        bookRooms(freePremiumRooms, freeEconomyRooms, premiumGuestMap, premiumRooms, true, true);
        bookRooms(freeEconomyRooms, freePremiumRooms, premiumGuestMap, economyRooms, false, false);

        return OccupancyResponseDto.builder()
                .premiumRoomResult(RoomBookingResult.builder()
                        .revenue(premiumRooms.stream().mapToDouble(Room::getBookingPrice).sum())
                        .roomsOccupied(premiumRooms.stream().filter(room -> !room.isRoomAvailable()).toList().size())
                        .build())
                .economyRoomResult(RoomBookingResult.builder()
                        .revenue(economyRooms.stream().mapToDouble(Room::getBookingPrice).sum())
                        .roomsOccupied(economyRooms.stream().filter(room -> !room.isRoomAvailable()).toList().size())
                        .build())
                .build();
    }
}
