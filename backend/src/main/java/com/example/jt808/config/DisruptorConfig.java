package com.example.jt808.config;

import com.example.jt808.disruptor.TelemetryEvent;
import com.example.jt808.disruptor.TelemetryEventFactory;
import com.example.jt808.disruptor.TelemetryEventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {
    @Bean(destroyMethod = "shutdown")
    public ExecutorService disruptorExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean(destroyMethod = "shutdown")
    public Disruptor<TelemetryEvent> disruptor(ExecutorService disruptorExecutor, TelemetryEventHandler telemetryEventHandler) {
        Disruptor<TelemetryEvent> disruptor = new Disruptor<>(
                new TelemetryEventFactory(),
                1024,
                disruptorExecutor,
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        disruptor.handleEventsWith(telemetryEventHandler);
        disruptor.start();
        return disruptor;
    }

    @Bean
    public RingBuffer<TelemetryEvent> ringBuffer(Disruptor<TelemetryEvent> disruptor) {
        return disruptor.getRingBuffer();
    }
}
