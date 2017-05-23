import net.SocketConnection;
import org.apache.log4j.Logger;

public class HandlerDispatcher {
    private static Logger logger = Logger.getLogger(HandlerDispatcher.class);

    /**
     * distribute method response for distribute message to correspond handler.
     * @param socketConnection   is client connection.
     * @param msg             is received message.
     * @return if succeed handle message, return true. Or return false.
     */
    public static boolean distribute(SocketConnection socketConnection, String msg) {
        System.out.println(socketConnection.getUserName());
        System.out.println("Receive: " + msg);

        return true;
    }
}
