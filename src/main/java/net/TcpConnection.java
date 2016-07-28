package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import utils.time.MyTime;

public class TcpConnection {
    private ChannelHandlerContext mChannelHandlerContext;
    private String userName;
    private String timestamp;

    public TcpConnection(ChannelHandlerContext ctx) {
        mChannelHandlerContext = ctx;
        userName = null;
        timestamp = (new MyTime()).format() + ctx.name();
    }

    public void setUserName(String name) {
        if (!mChannelHandlerContext.isRemoved()) {
            userName = name;
        }
    }

    public String getUserName() {
        if (!mChannelHandlerContext.isRemoved()) {
            return userName;
        }
        return null;
    }

    public void write(byte[] bytes) {
        if (!mChannelHandlerContext.isRemoved()) {
            ByteBuf byteBuf = mChannelHandlerContext.alloc().buffer();
            byteBuf.writeBytes(bytes);
            // ChannelHandlerContext finish writing will free ByteBuf.
            mChannelHandlerContext.writeAndFlush(byteBuf);
        }
    }

    public void close() {
        if (!mChannelHandlerContext.isRemoved()) {
            mChannelHandlerContext.close();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TcpConnection) {
            TcpConnection tcpConnection = (TcpConnection) object;
            if (timestamp.equals(tcpConnection.timestamp)) {
                return true;
            }
        }
        return false;
    }
}
