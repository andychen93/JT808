package com.example.jt808.parser;

public interface ProtocolParser {
    boolean supports(byte[] payload);
    ParsedTelemetry parse(String hex);
}
