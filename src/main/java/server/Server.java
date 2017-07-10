package server;

import handler.SocketHandler;
import handler.SocketHandlerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import protocol.Protocol;

/**
 * Created by aaron on 7/10/17.
 */
@Component
public class Server {
    @Autowired
    private SocketHandlerFactory socketHandlerFactory;

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //Override
                        /**
                         * The method will be called when socket accepted.
                         * SocketHandler is added to channel's pipeline to handle event.
                         * SocketHandler must be prototype because it designed to be binded with socket.
                         */
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(socketHandlerFactory.getObject());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // bind and start to accept incoming connections.
            ChannelFuture channelFuture = serverBootstrap.bind(Protocol.DEFAULT_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
