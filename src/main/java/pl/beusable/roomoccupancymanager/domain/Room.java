package pl.beusable.roomoccupancymanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Room {
    boolean roomAvailable;
    double bookingPrice;

}