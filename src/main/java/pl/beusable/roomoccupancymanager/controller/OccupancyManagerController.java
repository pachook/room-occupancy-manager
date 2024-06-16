package pl.beusable.roomoccupancymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.beusable.roomoccupancymanager.domain.OccupancyRequestDto;
import pl.beusable.roomoccupancymanager.domain.OccupancyResponseDto;
import pl.beusable.roomoccupancymanager.service.OccupancyManagerService;

@RestController
@RequestMapping("/api/occupancy-manager")
public class OccupancyManagerController {
    private final OccupancyManagerService occupancyManagerService;

    @Autowired
    public OccupancyManagerController(OccupancyManagerService occupancyManagerService) {
        this.occupancyManagerService = occupancyManagerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OccupancyResponseDto determineRoomOccupancy(@RequestParam int freePremiumRooms, @RequestParam int freeEconomyRooms, @RequestBody OccupancyRequestDto request) {
        return occupancyManagerService.determineOccupancyDefaultPrice(freePremiumRooms, freeEconomyRooms, request.getGuests());
    }
}
