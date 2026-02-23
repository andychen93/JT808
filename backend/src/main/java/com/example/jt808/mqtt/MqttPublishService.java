package com.example.jt808.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Slf4j
@Service
public class MqttPublishService {
    @Value("${mqtt.enabled:false}")
    private boolean enabled;

    @Value("${mqtt.broker:tcp://localhost:1883}")
    private String broker;

    private MqttClient client;

    @PostConstruct
    public void init() {
        if (!enabled) {
            return;
        }
        try {
            client = new MqttClient(broker, "jt808-platform-publisher");
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);
        } catch (Exception e) {
            log.warn("mqtt init failed", e);
        }
    }

    public void publish(String topic, String payload) {
        if (!enabled || client == null || !client.isConnected()) {
            return;
        }
        try {
            client.publish(topic, new MqttMessage(payload.getBytes()));
        } catch (Exception e) {
            log.warn("mqtt publish failed", e);
        }
    }
}
