package com.example.jt808;

import com.example.jt808.parser.Jt808Parser;
import com.example.jt808.parser.ParserFacade;
import com.example.jt808.parser.Protocol32960Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserFacadeTest {
    @Test
    void parseJt808() {
        ParserFacade facade = new ParserFacade(java.util.List.of(new Jt808Parser(), new Protocol32960Parser()));
        var t = facade.parse("7E0200002F01391234567800010000000000000000025B8D8006F4C10001F4006400012301010101017E");
        assertEquals("JT808", t.getProtocol());
    }
}
