import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        DatagramSocket DGSocket = new DatagramSocket(port);

        byte[] buff = new byte[1024];
        DatagramPacket DPtoRecieve = new DatagramPacket(buff, buff.length);

        while (true) {
            DGSocket.receive(DPtoRecieve);

            int numberOfData;
            int data_length = buff[1];
            int port_toSend = DPtoRecieve.getPort();
            String data = new String(DPtoRecieve.getData(), 2, data_length);

            if (data.equals("HELLO")) {
                DatagramPacket datagramPacket = new DatagramPacket(DPtoRecieve.getData(), DPtoRecieve.getLength(), InetAddress.getByName("127.0.0.1"), port_toSend);
                DGSocket.send(datagramPacket);

                boolean numToRec1 = false;
                boolean numToRec2 = false;
                boolean numToRec3 = false;
                TreeMap<Integer, String> dataStorage = new TreeMap<>();

                while (!numToRec1 || !numToRec2 || !numToRec3) {

                    DGSocket.receive(DPtoRecieve);
                    numberOfData = buff[0];
                    data_length = buff[1];
                    data = new String(DPtoRecieve.getData(), 2, data_length);

                    switch (numberOfData) {
                        case 7:
                            numToRec1 = true;
                            dataStorage.put(numberOfData, data);
                            break;
                        case 16:
                            numToRec2 = true;
                            dataStorage.put(numberOfData, data);
                            break;
                        case 22:
                            numToRec3 = true;
                            dataStorage.put(numberOfData, data);
                            break;
                    }
                }
                String respondMsg = "";
                for(String dgMsg : dataStorage.values()){
                    respondMsg+=dgMsg;
            }
                byte[] bytesToSend = new byte[respondMsg.getBytes().length + 2];
                bytesToSend[0] = (byte) 0;
                bytesToSend[1] = (byte) (respondMsg.getBytes().length);
                System.arraycopy(respondMsg.getBytes(), 0, bytesToSend, 2, respondMsg.getBytes().length);

                DatagramPacket dpToResend = new DatagramPacket(bytesToSend,bytesToSend.length,InetAddress.getByName("127.0.0.1"),port_toSend);
                DGSocket.send(dpToResend);
        }
    }
}
}