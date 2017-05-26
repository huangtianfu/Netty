import net.SocketConnection;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class HandlerDispatcher {
    private static Logger logger = Logger.getLogger(HandlerDispatcher.class);
    @Autowired
    private UserService userService;

    /**
     * distribute method response for distribute message to correspond handler.
     * @param socketConnection   is socket connection.
     * @param msg             is received message.
     * @return if succeed handle message, return true. Or return false.
     */
    public boolean distribute(SocketConnection socketConnection, String msg) {
        System.out.println(socketConnection.getUserName());
        System.out.println("Receive: " + msg);

        return true;
    }
}
