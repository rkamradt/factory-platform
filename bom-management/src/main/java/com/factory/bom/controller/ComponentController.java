package com.factory.bom.controller;

import com.factory.bom.dto.ComponentSpecificationResponse;
import com.factory.bom.service.ComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/components")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    /**
     * Get component specifications and requirements
     * GET /components/{componentId}/specifications
     */
    @GetMapping("/{componentId}/specifications")
    public ResponseEntity<ComponentSpecificationResponse> getComponentSpecifications(
            @PathVariable String componentId) {
        ComponentSpecificationResponse response = componentService.getComponentSpecifications(componentId);
        return ResponseEntity.ok(response);
    }
}
