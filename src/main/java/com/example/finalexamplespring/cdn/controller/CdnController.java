package com.example.finalexamplespring.cdn.controller;

import com.example.finalexamplespring.cdn.service.CdnService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cdn")
@RequiredArgsConstructor
@RestController
public class CdnController {
    private final CdnService cdnService;

    @ApiOperation(
            value = "{cdnInstanceNo} cdn상태 확인을 위한 번호",
            notes = "cdnInstanceNo를 통해 cdn 생성 상태를 확인한다."
    )
    @GetMapping("/info/{cdnInstanceNo}")
    public ResponseEntity<?> getCdn(@PathVariable String cdnInstanceNo) {
        return cdnService.getCdnPlusInstanceList(cdnInstanceNo);
    }
}
