import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {
    private static final ArrayList<ClientHandler> clientsList = new ArrayList<>();

    public static void main(String[] args) {
        int port = 123;
        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket serverSocket = null;
        try {
            serverSocket = sslServerSocketFactory.createServerSocket(port);
            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientsList.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static class ClientHandler extends Thread {
        private SSLSocket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(SSLSocket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                clientSocket.startHandshake();
                String msg = reader.readLine();

                System.out.println(msg);

                if (clientsList.size() > 1)
                    broadcastMessage(msg);

                while (clientSocket.isConnected()) {
                    if ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                        if (clientsList.size() > 1)
                            broadcastMessage(msg);
                    }
                }

            } catch (Throwable e) {
                handleClientDisconnection();
            } finally {
                handleClientDisconnection();
            }
        }

        private void handleClientDisconnection() {
            try {
                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void sendMsgDirectly(String msg) {
            writer.println(msg);
        }

        public Socket getClientSocket() {
            return clientSocket;
        }

        private void broadcastMessage(String mess) {
            for (ClientHandler s : clientsList) {
                if (!s.getClientSocket().equals(this.clientSocket)) {
                    s.sendMsgDirectly(mess);
                }
            }
        }
    }
}