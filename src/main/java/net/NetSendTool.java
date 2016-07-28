package net;

import org.json.JSONObject;
import protocol.Protocol;
import utils.string.StringUtils;

public class NetSendTool {
    public static boolean sendPck(TcpConnection tcpConnection, JSONObject jsonObject) {
        String bodyString = jsonObject.toString();
        byte[] bodyBuffer = bodyString.getBytes();
        int bodyLen = bodyBuffer.length;

        String headString = StringUtils.int2FourByteString(bodyLen);
        byte[] headBuffer = headString.getBytes();
        int headLen = headBuffer.length;

        if (bodyLen >= Protocol.MAX_DATA_LEN) {
            System.out.println("###body over flow###" + bodyString);
            return false;
        }

        System.out.println("Send: " + bodyString);

        byte[] buffer = new byte[headLen + bodyLen];
        System.arraycopy(headBuffer, 0, buffer, 0, headLen);
        System.arraycopy(bodyBuffer, 0, buffer, headLen, bodyLen);

        tcpConnection.write(buffer);

        return true;
    }

    public static boolean sendPck(SocketClient client, JSONObject transferJsonObject) {
        String bodyString = transferJsonObject.toString();
        byte[] bodyBuffer = bodyString.getBytes();
        int bodyLen = bodyBuffer.length;

        String headString = StringUtils.int2FourByteString(bodyLen);
        byte[] headBuffer = headString.getBytes();
        int headLen = headBuffer.length;

        if (bodyLen >= Protocol.MAX_DATA_LEN) {
            System.out.println("###body over flow###" + bodyString);
            return false;
        }

        System.out.println("SendTransferPck: " + bodyString);

        byte[] buffer = new byte[headLen + bodyLen];
        System.arraycopy(headBuffer, 0, buffer, 0, headLen);
        System.arraycopy(bodyBuffer, 0, buffer, headLen, bodyLen);
        try {
            client.send(buffer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
