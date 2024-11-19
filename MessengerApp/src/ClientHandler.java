import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlersList = new ArrayList<>();
    private BufferedWriter out;
    private BufferedReader in;
    private Socket socket;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = in.readLine();
            clientHandlersList.add(this);
            broadcastMsg("SERVER: " + username + " has entered the chat!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void broadcastMsg(String msg){
        try {
            for (ClientHandler clientHandler : clientHandlersList) {
                clientHandler.out.write(msg);
                clientHandler.out.newLine();
                clientHandler.out.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientHandler(){
        clientHandlersList.remove(this);
        broadcastMsg("SERVER: " + username + " has left the chat!");
    }

    private void closeEverything(){
        removeClientHandler();
        try{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String clientMsg;
        try {
            while (socket.isConnected()) {
                clientMsg = in.readLine();
                broadcastMsg(clientMsg);
            }
        }catch (IOException e) {
            closeEverything();
        }
    }
}
