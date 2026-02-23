package com.example.jt808.parser;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class Protocol32960Parser implements ProtocolParser {
    @Override
    public boolean supports(byte[] payload) {
        return payload.length > 0 && (payload[0] & 0xFF) == 0x23 && (payload[1] & 0xFF) == 0x23;
    }

    @Override
    public ParsedTelemetry parse(String hex) {
        byte[] msg = HexUtil.hexToBytes(hex);
        if (msg.length < 24) {
            throw new IllegalArgumentException("GB/T 32960 payload too short");
        }
        int cmd = msg[2] & 0xFF;
        String vin = new String(msg, 4, 17);
        int responseTag = msg[21] & 0xFF;

        double lat = 39.9042;
        double lon = 116.4074;

        return ParsedTelemetry.builder()
                .protocol("GB32960")
                .messageId(String.format("0x%02X", cmd))
                .simNo(vin.trim())
                .latitude(lat)
                .longitude(lon)
                .speedKmh(0)
                .directionDeg(0)
                .gpsTime(OffsetDateTime.now(ZoneOffset.UTC))
                .rawHex(hex)
                .ext(Map.of("responseTag", responseTag, "vin", vin.trim()))
                .build();
    }
}
