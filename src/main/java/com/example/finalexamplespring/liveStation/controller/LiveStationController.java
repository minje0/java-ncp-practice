package com.example.finalexamplespring.liveStation.controller;

import com.example.finalexamplespring.liveStation.service.LiveStationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/live")
@RequiredArgsConstructor
@RestController
public class LiveStationController {
    private final LiveStationService liveStationService;

    @ApiOperation(
            value = "{channelName} 생성할 채널 제목",
            notes = "채널 이름을 받아 LiveStation 채널을 생성한다."
    )
    @GetMapping("/create/{channelName}")
    public ResponseEntity<?> creatChannel(@PathVariable String channelName) {
        return liveStationService.createChannel(channelName);
    }

    @ApiOperation(
            value = "{channelId} 조회할 채널 ID",
            notes = "채널 아이디를 통해 채널 정보를 확인한다."
    )
    @GetMapping("/info/{channelId}")
    public ResponseEntity<?> infoChannel(@PathVariable String channelId) {
        return liveStationService.getChannelInfo(channelId);
    }

    @ApiOperation(
            value = "{channelId} 조회할 채널 ID, ",
            notes = "채널 아이디를 통해 url 정보를 확인한다."
    )
    @GetMapping("/infoUrl/{channelId}")
    public ResponseEntity<?> infoServiceUrl(@PathVariable String channelId) {
        return liveStationService.getServiceURL(channelId);
    }

    @DeleteMapping("/delete/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable String channelId) {
        return liveStationService.deleteChannel(channelId);
    }

}
