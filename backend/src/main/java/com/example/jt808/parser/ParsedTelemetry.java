package com.example.jt808.parser;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Builder
public class ParsedTelemetry {
    private String protocol;
    private String messageId;
    private String simNo;
    private Double latitude;
    private Double longitude;
    private Integer speedKmh;
    private Integer directionDeg;
    private OffsetDateTime gpsTime;
    private String rawHex;
    private Map<String, Object> ext;
}
