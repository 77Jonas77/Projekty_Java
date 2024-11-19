import java.util.*;
import java.net.*;
import java.io.*;


public class Server {
    private static boolean RECIEVED = false;
    public static void main(String[] args) {
        try {

            Scanner sc = new Scanner(System.in);
            byte[] recieveData = sc.next().getBytes();
            byte[] whatever = new byte[1024];

            int port = Integer.parseInt(args[0]);

            //System.out.println(Arrays.toString(recieveData));

            DatagramSocket datagramSocket = new DatagramSocket(port);

            DatagramPacket receivePacket = new DatagramPacket(whatever,whatever.length);
            datagramSocket.receive(receivePacket);

//            System.out.println(receivePacket);
//            System.out.println(Arrays.toString(whatever));
//            InetSocketAddress outputAddr = new InetSocketAddress(socket.getPort());

            manageData(datagramSocket,receivePacket, recieveData);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void manageData(DatagramSocket datagramSocket, DatagramPacket receivePacket,  byte[] receiveData) throws IOException{
//        InetAddress clientAddress = datagramPacket.getAddress();
//        int clientport = datagramPacket.getPort();

        for(int i=0;i<10;i++){
            int remaining = receiveData.length;
            for(int j = 0; j <receiveData.length/10; j++) {

                byte[] bytes10ofData = new byte[10];
                int beginIndex = j*10;

                System.arraycopy(receiveData, beginIndex, bytes10ofData, 0, 10);

                byte[] preparedData = packData(j, bytes10ofData);
                sendData(datagramSocket, receivePacket.getAddress(), receivePacket.getPort(), preparedData);

                remaining-=10;
                if(remaining<10){
                    bytes10ofData = new byte[remaining];
                    beginIndex = beginIndex + 10;
                    System.arraycopy(receiveData, beginIndex, bytes10ofData, 0, remaining);

                    sendData(datagramSocket,receivePacket.getAddress(), receivePacket.getPort(),packData(j+1,bytes10ofData));
                }
            }
        }

    }
    private static void sendData(DatagramSocket ds,InetAddress addr, int port, byte[] byte12ToSend) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(byte12ToSend, byte12ToSend.length,addr,port); //,dp.getPort()
        ds.send(sendPacket);
    }
    private static byte[] packData(int packetNumber, byte[] bytes10ofData) {
        byte[] sendData = new byte[12];

        sendData[0] = (byte) packetNumber;
        sendData[1] = (byte) bytes10ofData.length;

        if (bytes10ofData.length > 0) System.arraycopy(bytes10ofData, 0, sendData, 2, bytes10ofData.length);

        return sendData;
    }

}
