package com.example.finalexamplespring.liveStation.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RecordContentDTO {
    private Map<String, RecordInfoDTO> recordList;
}
