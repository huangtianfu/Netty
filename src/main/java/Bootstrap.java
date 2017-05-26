import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import protocol.Protocol;
import thread.HeartThread;

public class Bootstrap {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.initLog4j();
        bootstrap.initApplicationContext();

        bootstrap.startThread();
        bootstrap.startListen();
    }

    private void initLog4j() {
        PropertyConfigurator.configure(Bootstrap.class.getResourceAsStream("/log4j.properties"));
    }

    private void initApplicationContext() {
        applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
    }

    private void startThread() {
        HeartThread heartThread = applicationContext.getBean(HeartThread.class);
        heartThread.start();
    }

    private void startListen() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(applicationContext.getBean(SocketHandler.class));
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
