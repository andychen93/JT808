package com.example.jt808;

import com.example.jt808.parser.Protocol32960Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Protocol32960ParserTest {

    @Test
    void shouldParseGb32960Frame() {
        Protocol32960Parser parser = new Protocol32960Parser();
        var telemetry = parser.parse("232302014C4A313233343536373839303132333435010000");

        assertEquals("GB32960", telemetry.getProtocol());
        assertEquals("0x02", telemetry.getMessageId());
        assertEquals("LJ123456789012345", telemetry.getSimNo());
        assertEquals(39.9042, telemetry.getLatitude());
        assertEquals(116.4074, telemetry.getLongitude());
        assertEquals(1, telemetry.getExt().get("responseTag"));
        assertEquals("LJ123456789012345", telemetry.getExt().get("vin"));
    }
}
