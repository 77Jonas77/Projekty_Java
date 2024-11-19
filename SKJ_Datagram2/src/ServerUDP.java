import java.util.*;
import java.net.*;
import java.io.*;

public class ServerUDP {
    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        DatagramSocket datagramSocket = new DatagramSocket(port);

        byte[] receivedData = new byte[1024];
        DatagramPacket packetToReceive = new DatagramPacket(receivedData, receivedData.length);

        while (true) {
            datagramSocket.receive(packetToReceive);

            int port_toSend = packetToReceive.getPort();
            int dataNr;
            int dataLength = receivedData[1];

            String data = new String(receivedData, 2, dataLength);

            if (data.equals("GREETINGS")) {
                DatagramPacket datagramPacket = new DatagramPacket(packetToReceive.getData(), packetToReceive.getLength(), InetAddress.getByName("127.0.0.1"), port_toSend);
                datagramSocket.send(datagramPacket);

                boolean isNumRec1 = false;
                boolean isNumRec2 = false;
                boolean isNumRec3 = false;
                TreeMap<Integer, String> storeDataMap = new TreeMap<>();

                while (!isNumRec1 || !isNumRec2 || !isNumRec3) {
                    datagramSocket.receive(packetToReceive);

                    int recDataLength = packetToReceive.getLength();
                    dataNr = receivedData[0];
                    dataLength = receivedData[1];
                    data = new String(receivedData, 2, dataLength);

                    if (recDataLength == dataLength + 2) {
                        switch (dataNr) {
                            case 15:
                                isNumRec1 = true;
                                storeDataMap.put(dataNr, data);
                                break;
                            case 16:
                                isNumRec2 = true;
                                storeDataMap.put(dataNr, data);
                                break;
                            case 22:
                                isNumRec3 = true;
                                storeDataMap.put(dataNr, data);
                                break;
                        }
                    }
                }
                String resendData = "";
                for (String elMsg : storeDataMap.values()) {
                    resendData += elMsg;
                }

                byte[] bytesToSend = new byte[resendData.getBytes().length + 2];

                bytesToSend[0] = (byte) 0;
                bytesToSend[1] = (byte) (resendData.getBytes().length);
                System.arraycopy(resendData.getBytes(), 0, bytesToSend, 2, resendData.getBytes().length);

                DatagramPacket datagramPacketToResend = new DatagramPacket(bytesToSend, bytesToSend.length, InetAddress.getByName("127.0.0.1"), port_toSend);
                datagramSocket.send(datagramPacketToResend);
            }
        }
    }
}