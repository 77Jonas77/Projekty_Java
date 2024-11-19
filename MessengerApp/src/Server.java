import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void startServer(){
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("SERVER: New client has connected!"); //tutaj mozna interfejs komunikacyjny zeby dodawalo ten prefix np.
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeServerSocket() {
        try {
            if(serverSocket!=null)
                serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        try {
            ServerSocket sSocket = new ServerSocket(123);
            Server server = new Server(sSocket);
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
