package pl.beusable.roomoccupancymanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OccupancyRequestDto {
    List<Guest> guests;
}
