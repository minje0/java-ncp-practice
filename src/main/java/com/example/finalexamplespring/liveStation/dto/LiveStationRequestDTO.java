package com.example.finalexamplespring.liveStation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveStationRequestDTO {
     @JsonProperty("channelName")
     String channelName;

     @JsonProperty("cdn")
     CdnDTO cdn;

     @JsonProperty("qualitySetId")
     Integer qualitySetId;

     @JsonProperty("useDvr")
     Boolean useDvr;

     @JsonProperty("immediateOnAir")
     Boolean immediateOnAir;

     @JsonProperty("timemachineMin")
     Integer timemachineMin;

     @JsonProperty("record")
     RecordDTO record;

     @JsonProperty("isStreamFailOver")
     Boolean isStreamFailOver;

}

