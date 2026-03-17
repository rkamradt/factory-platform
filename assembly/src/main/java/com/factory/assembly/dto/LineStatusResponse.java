package com.factory.assembly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineStatusResponse {

    private String lineId;
    private String status;
    private String downtimeReason;
    private LocalDateTime reportedAt;
    private String message;
}
