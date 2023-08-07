package com.example.finalexamplespring.liveStation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecordResponseDTO {
    @JsonProperty("content")
    private RecordContentDTO contentDTO;
    private int total;
}
