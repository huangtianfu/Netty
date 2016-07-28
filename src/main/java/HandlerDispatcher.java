import net.TcpConnection;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import protocol.Protocol;

public class HandlerDispatcher {
    private static Logger logger = Logger.getLogger(HandlerDispatcher.class);

    /**
     * distribute method response for distribute message to correspond handler.
     * @param tcpConnection   is client connection.
     * @param msg             is received message.
     * @return if succeed handle message, return true. Or return false.
     */
    public static boolean distribute(TcpConnection tcpConnection, String msg) {
        System.out.println(tcpConnection.getUserName());
        System.out.println("Receive: " + msg);

        return true;
    }
}
