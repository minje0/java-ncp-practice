package com.example.finalexamplespring.liveStation.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiveInfoDTO {
    private String channelId;
    private String channelName;

    private int cdnInstanceNo;
    private String cdnStatus;

    private String publishUrl;
    private String streamKey;
}
