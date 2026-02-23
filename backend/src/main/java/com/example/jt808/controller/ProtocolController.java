package com.example.jt808.controller;

import com.example.jt808.disruptor.TelemetryPublisher;
import com.example.jt808.parser.ParserFacade;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/protocol")
public class ProtocolController {
    private final ParserFacade parserFacade;
    private final TelemetryPublisher telemetryPublisher;

    public ProtocolController(ParserFacade parserFacade, TelemetryPublisher telemetryPublisher) {
        this.parserFacade = parserFacade;
        this.telemetryPublisher = telemetryPublisher;
    }

    @PostMapping("/parse")
    public Object parse(@RequestBody ParseRequest request, @RequestParam(defaultValue = "false") boolean publish) {
        var result = parserFacade.parse(request.getHex());
        if (publish) {
            telemetryPublisher.publish(result);
        }
        return result;
    }

    @Data
    public static class ParseRequest {
        @NotBlank
        private String hex;
    }
}
