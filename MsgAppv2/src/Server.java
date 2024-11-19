import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<ClientHandler> clientHandlersList = new ArrayList<>();
    private ServerSocket serverSocket;
    private CP cp;

    public Server(ServerSocket serverSocket, CP cp) {
        this.serverSocket = serverSocket;
        this.cp = cp;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, cp);
                cp.serverNotifyMsg("New client has connected!"); //cp chyba nie jest jakis bardzo potrzebny wsm
                clientHandlersList.add(clientHandler);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            closeServer();
        }
    }

    private void closeServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(123);
        Server server = new Server(serverSocket, new CP());
        server.startServer();
    }
}
