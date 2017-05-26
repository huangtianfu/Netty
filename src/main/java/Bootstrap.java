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
import handler.SocketHandler;
import thread.HeartThread;

/**
 * Created by Aaron Sheng on 5/26/17.
 */
public class Bootstrap {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.initApplicationContext();
        bootstrap.initLog4j();

        bootstrap.startThread();
        bootstrap.startListen();
    }

    /**
     * applicationContext must be inited first. Because all object are managed by it.
     */
    private void initApplicationContext() {
        applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
    }

    /**
     * init log4j properties.
     */
    private void initLog4j() {
        PropertyConfigurator.configure(Bootstrap.class.getResourceAsStream("/log4j.properties"));
    }

    /**
     * start own thread.
     */
    private void startThread() {
        HeartThread heartThread = applicationContext.getBean(HeartThread.class);
        heartThread.start();
    }

    /**
     * start bind port and listen socket come in.
     * @throws Exception
     */
    private void startListen() throws Exception {
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
