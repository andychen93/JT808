package com.example.jt808.netty;

import com.example.jt808.disruptor.TelemetryPublisher;
import com.example.jt808.parser.ParserFacade;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServerRunner {
    @Value("${jt808.netty.port:7611}")
    private int port;

    private final ParserFacade parserFacade;
    private final TelemetryPublisher telemetryPublisher;

    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ExecutorService virtualExecutor;

    @PostConstruct
    public void start() {
        boss = new io.netty.channel.nio.NioEventLoopGroup(1);
        worker = new io.netty.channel.nio.NioEventLoopGroup();
        virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

        virtualExecutor.submit(() -> {
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boss, worker)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                                ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                                ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                                ch.pipeline().addLast(new NettyFrameHandler(parserFacade, telemetryPublisher));
                            }
                        });

                bootstrap.bind(port).sync();
                log.info("Netty server started at {}", port);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        if (boss != null) boss.shutdownGracefully();
        if (worker != null) worker.shutdownGracefully();
        if (virtualExecutor != null) virtualExecutor.shutdown();
    }
}
