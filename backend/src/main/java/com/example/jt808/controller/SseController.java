package com.example.jt808.controller;

import com.example.jt808.sse.SsePushService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
public class SseController {
    private final SsePushService ssePushService;

    public SseController(SsePushService ssePushService) {
        this.ssePushService = ssePushService;
    }

    @GetMapping("/telemetry")
    public SseEmitter telemetry() {
        return ssePushService.connect();
    }
}
