package com.example.jt808;

import com.example.jt808.parser.Jt808Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Jt808ParserTest {

    @Test
    void shouldParseJt808LocationFrame() {
        Jt808Parser parser = new Jt808Parser();
        var telemetry = parser.parse("7E0200002F01391234567800010000000000000000025B8D8006F4C10001F4006400012301010101017E");

        assertEquals("JT808", telemetry.getProtocol());
        assertEquals("0x0200", telemetry.getMessageId());
        assertEquals("391234567800", telemetry.getSimNo());
        assertTrue(telemetry.getLatitude() > 0);
        assertTrue(telemetry.getLongitude() > 0);
        assertEquals(50, telemetry.getSpeedKmh());
        assertEquals(100, telemetry.getDirectionDeg());
        assertEquals("0x00000000", telemetry.getExt().get("alarm"));
        assertEquals("0x00000000", telemetry.getExt().get("status"));
    }
}
