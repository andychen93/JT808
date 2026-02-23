package com.example.jt808.netty;

import com.example.jt808.disruptor.TelemetryPublisher;
import com.example.jt808.parser.ParserFacade;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NettyFrameHandler extends SimpleChannelInboundHandler<String> {
    private final ParserFacade parserFacade;
    private final TelemetryPublisher telemetryPublisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            telemetryPublisher.publish(parserFacade.parse(msg));
            ctx.writeAndFlush("OK\n");
        } catch (Exception e) {
            log.warn("decode failed", e);
            ctx.writeAndFlush("ERR:" + e.getMessage() + "\n");
        }
    }
}
