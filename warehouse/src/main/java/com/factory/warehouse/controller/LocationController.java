package com.factory.warehouse.controller;

import com.factory.warehouse.dto.LocationCapacityResponse;
import com.factory.warehouse.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Query storage location capacity and utilization
     * GET /locations/{locationId}/capacity
     */
    @GetMapping("/{locationId}/capacity")
    public ResponseEntity<LocationCapacityResponse> getLocationCapacity(@PathVariable String locationId) {
        LocationCapacityResponse response = locationService.getLocationCapacity(locationId);
        return ResponseEntity.ok(response);
    }
}
