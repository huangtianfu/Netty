package handler;

import com.alibaba.fastjson.JSONObject;
import net.SocketConnection;
import net.SocketUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

/**
 * Created by Aaron Sheng on 5/26/17.
 * HandlerDispatcher is event dispatcher. It dispatches event to different handler.
 */
@Component
public class HandlerDispatcher {
    private static Logger logger = Logger.getLogger(HandlerDispatcher.class);
    @Autowired
    private UserService userService;

    /**
     * distribute method response for distribute message to correspond handler.
     * @param socketConnection      socket connection.
     * @param msg                   received message.
     * @return if succeed handle message, return true. Or return false.
     */
    public boolean distribute(SocketConnection socketConnection, String msg) {
        System.out.println(socketConnection.getUserName());
        System.out.println("Receive: " + msg);

        JSONObject returnJsonObject = new JSONObject();
        returnJsonObject.put("result", "success");
        SocketUtils.sendPck(socketConnection, returnJsonObject);

        return true;
    }
}
