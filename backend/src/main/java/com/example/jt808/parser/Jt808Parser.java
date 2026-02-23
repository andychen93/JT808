package com.example.jt808.parser;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

@Component
public class Jt808Parser implements ProtocolParser {
    @Override
    public boolean supports(byte[] payload) {
        return payload.length > 1 && (payload[0] & 0xFF) == 0x7E;
    }

    @Override
    public ParsedTelemetry parse(String hex) {
        byte[] msg = HexUtil.hexToBytes(hex);
        if (msg.length < 30) {
            throw new IllegalArgumentException("JT808 payload too short");
        }
        int messageId = HexUtil.u16(msg, 1);
        String simNo = String.format("%02X%02X%02X%02X%02X%02X", msg[5], msg[6], msg[7], msg[8], msg[9], msg[10]);

        long alarm = HexUtil.u32(msg, 13);
        long status = HexUtil.u32(msg, 17);
        double lat = HexUtil.u32(msg, 21) / 1_000_000.0;
        double lon = HexUtil.u32(msg, 25) / 1_000_000.0;
        int speed = HexUtil.u16(msg, 29) / 10;
        int direction = HexUtil.u16(msg, 31);

        return ParsedTelemetry.builder()
                .protocol("JT808")
                .messageId(String.format("0x%04X", messageId))
                .simNo(simNo)
                .latitude(lat)
                .longitude(lon)
                .speedKmh(speed)
                .directionDeg(direction)
                .gpsTime(OffsetDateTime.now(ZoneOffset.UTC))
                .rawHex(hex)
                .ext(new HashMap<>() {{
                    put("alarm", String.format("0x%08X", alarm));
                    put("status", String.format("0x%08X", status));
                }})
                .build();
    }
}
