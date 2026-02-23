package com.example.jt808.disruptor;

import com.example.jt808.parser.ParsedTelemetry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemetryEvent {
    private ParsedTelemetry telemetry;

    public void clear() {
        telemetry = null;
    }
}
