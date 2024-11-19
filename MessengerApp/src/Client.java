import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//Lock klasa + blockingqueue
public class Client {

    private Socket socket;
    private String username;
    private BufferedWriter out;
    private BufferedReader in;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            closeEverything();
        }
    }

    private void closeEverything() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(){
        try{
            out.write(username);
            out.newLine();
            out.flush();

            Scanner sc = new Scanner(System.in);
            while(socket.isConnected()){
                String msgToSend = sc.nextLine();
                out.write(username + ": " + msgToSend);
                out.newLine();
                out.flush();
            }
        }catch (IOException e) {
            closeEverything();
        }

    }

    private void listenForMsg() {
        new Thread(() -> {
            String msgRecievedFromChat;

            while(socket.isConnected()){
                try {
                    msgRecievedFromChat = in.readLine();
                    System.out.println(msgRecievedFromChat);
                } catch (IOException e) {
                    closeEverything();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 123);
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your username for chatting");

        String username = sc.nextLine();
        Client client = new Client(socket, username);
        client.listenForMsg();
        client.sendMsg();
    }

}
