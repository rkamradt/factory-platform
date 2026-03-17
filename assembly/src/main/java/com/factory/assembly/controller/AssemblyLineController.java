package com.factory.assembly.controller;

import com.factory.assembly.dto.LineStatusRequest;
import com.factory.assembly.dto.LineStatusResponse;
import com.factory.assembly.service.AssemblyLineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lines")
public class AssemblyLineController {

    private final AssemblyLineService assemblyLineService;

    public AssemblyLineController(AssemblyLineService assemblyLineService) {
        this.assemblyLineService = assemblyLineService;
    }

    /**
     * Report line status, downtime, or bottlenecks
     * POST /lines/{lineId}/status
     */
    @PostMapping("/{lineId}/status")
    public ResponseEntity<LineStatusResponse> reportLineStatus(
            @PathVariable String lineId,
            @Valid @RequestBody LineStatusRequest request) {
        LineStatusResponse response = assemblyLineService.reportLineStatus(lineId, request);
        return ResponseEntity.ok(response);
    }
}
