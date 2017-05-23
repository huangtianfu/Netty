package net;

import org.json.JSONObject;
import protocol.Protocol;
import utils.ByteUtils;

public class SocketSendUtils {
    public static void sendPck(SocketConnection socketConnection, JSONObject jsonObject) {
        String bodyString = jsonObject.toString();
        byte[] bodyBuffer = bodyString.getBytes();
        int bodyLen = bodyBuffer.length;

        byte[] headBuffer = ByteUtils.int2FourBytes(bodyLen);
        int headLen = headBuffer.length;

        if (bodyLen >= Protocol.MAX_DATA_LEN) {
            throw new RuntimeException("SocketSendUtils sendPck package body over flow");
        }

        byte[] buffer = new byte[headLen + bodyLen];
        System.arraycopy(headBuffer, 0, buffer, 0, headLen);
        System.arraycopy(bodyBuffer, 0, buffer, headLen, bodyLen);

        socketConnection.write(buffer);
    }

    public static void sendPck(SocketClient client, JSONObject transferJsonObject) {
        String bodyString = transferJsonObject.toString();
        byte[] bodyBuffer = bodyString.getBytes();
        int bodyLen = bodyBuffer.length;

        byte[] headBuffer = ByteUtils.int2FourBytes(bodyLen);
        int headLen = headBuffer.length;

        if (bodyLen >= Protocol.MAX_DATA_LEN) {
            throw new RuntimeException("SocketSendUtils sendPck package body over flow");
        }

        byte[] buffer = new byte[headLen + bodyLen];
        System.arraycopy(headBuffer, 0, buffer, 0, headLen);
        System.arraycopy(bodyBuffer, 0, buffer, headLen, bodyLen);
        try {
            client.send(buffer);
        } catch (Exception e) {
           throw new RuntimeException("SocketSendUtils sendPck exception");
        }
    }
}
