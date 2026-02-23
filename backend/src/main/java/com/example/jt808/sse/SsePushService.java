package com.example.jt808.sse;

import com.example.jt808.parser.ParsedTelemetry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class SsePushService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter connect() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    public void push(ParsedTelemetry telemetry) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("telemetry").data(telemetry));
            } catch (IOException e) {
                log.debug("sse push failed", e);
                emitters.remove(emitter);
            }
        });
    }
}
