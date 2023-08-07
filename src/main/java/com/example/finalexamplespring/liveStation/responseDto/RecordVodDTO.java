package com.example.finalexamplespring.liveStation.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordVodDTO {
    String recordType;
    String channelId;
    String fileName;
    String uploadPath;
}
