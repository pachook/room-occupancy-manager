package pl.beusable.roomoccupancymanager.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OccupancyResponseDto {
    RoomBookingResult premiumRoomResult;
    RoomBookingResult economyRoomResult;
}
