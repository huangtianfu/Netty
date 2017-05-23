import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.SocketConnection;
import org.apache.log4j.Logger;
import protocol.Protocol;
import utils.ByteUtils;

public class EventHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(EventHandler.class);
    private ByteBuf mByteBuf;
    private SocketConnection mSocketConnection;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        mByteBuf = ctx.alloc().buffer();
        mSocketConnection = new SocketConnection(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        mSocketConnection.close();
        mByteBuf.release();
        mByteBuf = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("exceptionCaught " + cause.toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        ByteBuf tmpBuf = (ByteBuf) msg;
        mByteBuf.writeBytes(tmpBuf);
        tmpBuf.release();

        while (mByteBuf.readableBytes() >= Protocol.MAX_HEAD_LEN) {
            // get header bytes from byte buffer.
            byte[] headBuf = new byte[Protocol.MAX_HEAD_LEN];
            mByteBuf.getBytes(0, headBuf);

            int bodyLen = ByteUtils.fourBytes2Int(headBuf);

            // header bytes present length wrong. break the loop.
            if (bodyLen <= 0 || bodyLen > Protocol.MAX_DATA_LEN) {
                mSocketConnection.close();
                break;
            }

            // not enough data. wait for next read.
            if (mByteBuf.readableBytes() < (bodyLen + Protocol.MAX_HEAD_LEN)) {
                break;
            }

            // get body bytes from byte buffer.
            byte[] bodyBuf = new byte[bodyLen];
            mByteBuf.getBytes(Protocol.MAX_HEAD_LEN, bodyBuf);

            boolean result = HandlerDispatcher.distribute(mSocketConnection, new String(bodyBuf));

            // get head and body data from buffer. and decrease readIndex and writeIndex.
            ByteBuf pckBuf = mByteBuf.readBytes(Protocol.MAX_HEAD_LEN + bodyLen);
            pckBuf.release();
            mByteBuf.discardReadBytes();

            if (!result) {
                mSocketConnection.close();
                break;
            }
        }
    }
}
