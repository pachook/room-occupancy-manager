package pl.beusable.roomoccupancymanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomBookingResult {
    private int roomsOccupied;
    private double revenue;

}
