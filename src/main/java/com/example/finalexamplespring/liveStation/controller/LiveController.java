package com.example.finalexamplespring.liveStation.controller;

import com.example.finalexamplespring.liveStation.service.LiveStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/liveStation")
@RequiredArgsConstructor
public class LiveController {
    private final LiveStationService liveStationService;
    @GetMapping("/page")
    public String getPage() {
        return "/liveStation/page";
    }
}
