package com.example.jt808.disruptor;

import com.example.jt808.parser.ParsedTelemetry;
import com.lmax.disruptor.RingBuffer;
import org.springframework.stereotype.Component;

@Component
public class TelemetryPublisher {
    private final RingBuffer<TelemetryEvent> ringBuffer;

    public TelemetryPublisher(RingBuffer<TelemetryEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(ParsedTelemetry telemetry) {
        long sequence = ringBuffer.next();
        try {
            ringBuffer.get(sequence).setTelemetry(telemetry);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
