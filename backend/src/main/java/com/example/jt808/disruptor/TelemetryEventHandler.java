package com.example.jt808.disruptor;

import com.example.jt808.domain.LocationRecord;
import com.example.jt808.domain.RawMessage;
import com.example.jt808.mqtt.MqttPublishService;
import com.example.jt808.repository.LocationRecordRepository;
import com.example.jt808.repository.RawMessageRepository;
import com.example.jt808.sse.SsePushService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelemetryEventHandler implements EventHandler<TelemetryEvent> {
    private final LocationRecordRepository locationRecordRepository;
    private final RawMessageRepository rawMessageRepository;
    private final MqttPublishService mqttPublishService;
    private final SsePushService ssePushService;
    private final ObjectMapper objectMapper;

    @Override
    public void onEvent(TelemetryEvent event, long sequence, boolean endOfBatch) throws Exception {
        var t = event.getTelemetry();
        if (t == null) {
            return;
        }

        log.info("telemetry-flow protocol={} sim={} lat={} lon={}", t.getProtocol(), t.getSimNo(), t.getLatitude(), t.getLongitude());

        LocationRecord location = new LocationRecord();
        location.setSimNo(t.getSimNo());
        location.setLatitude(t.getLatitude());
        location.setLongitude(t.getLongitude());
        location.setSpeedKmh(t.getSpeedKmh());
        location.setDirectionDeg(t.getDirectionDeg());
        location.setGpsTime(t.getGpsTime());
        location.setAlarmFlags(String.valueOf(t.getExt().getOrDefault("alarm", "")));
        location.setStatusFlags(String.valueOf(t.getExt().getOrDefault("status", "")));
        locationRecordRepository.save(location);

        RawMessage rawMessage = new RawMessage();
        rawMessage.setProtocol(t.getProtocol());
        rawMessage.setMessageId(t.getMessageId());
        rawMessage.setSimNo(t.getSimNo());
        rawMessage.setHexPayload(t.getRawHex());
        rawMessage.setJsonPayload(objectMapper.writeValueAsString(t));
        rawMessageRepository.save(rawMessage);

        mqttPublishService.publish("iot/vehicle/telemetry/" + t.getSimNo(), objectMapper.writeValueAsString(t));
        ssePushService.push(t);
        event.clear();
    }
}
