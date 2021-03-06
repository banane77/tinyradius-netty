package org.tinyradius.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Test;
import org.tinyradius.dictionary.DefaultDictionary;
import org.tinyradius.packet.AccessRequest;
import org.tinyradius.packet.AccountingRequest;
import org.tinyradius.packet.BaseRadiusPacket;
import org.tinyradius.server.RequestCtx;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestHandlerTest {

    private final BaseRadiusPacket accountingRequest = new AccountingRequest(DefaultDictionary.INSTANCE, 1, null);

    @Test
    void rejectMsg() throws Exception {

        final boolean b = new RequestHandler() {
            @Override
            protected Class<? extends BaseRadiusPacket> acceptedPacketType() {
                return AccessRequest.class;
            }

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, RequestCtx msg) {
            }
        }.acceptInboundMessage(new RequestCtx(accountingRequest, null));
        assertFalse(b);
    }

    @Test
    void acceptMsg() throws Exception {

        final boolean b = new RequestHandler() {
            @Override
            protected Class<? extends BaseRadiusPacket> acceptedPacketType() {
                return AccountingRequest.class;
            }

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, RequestCtx msg) {
            }
        }.acceptInboundMessage(new RequestCtx(accountingRequest, null));
        assertTrue(b);
    }
}