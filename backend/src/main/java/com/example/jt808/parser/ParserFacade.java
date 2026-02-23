package com.example.jt808.parser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParserFacade {
    private final List<ProtocolParser> parsers;

    public ParserFacade(List<ProtocolParser> parsers) {
        this.parsers = parsers;
    }

    public ParsedTelemetry parse(String hex) {
        byte[] payload = HexUtil.hexToBytes(hex);
        return parsers.stream()
                .filter(p -> p.supports(payload))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported protocol frame"))
                .parse(hex);
    }
}
