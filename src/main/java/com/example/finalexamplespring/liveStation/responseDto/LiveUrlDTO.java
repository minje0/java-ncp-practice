package com.example.finalexamplespring.liveStation.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiveUrlDTO {
    private String channelId;
    private String name;
    private String url;
}
