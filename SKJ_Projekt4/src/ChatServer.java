import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {
    private static final ArrayList<ClientHandler> clientsList= new ArrayList<>();

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientsList.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                String msg = reader.readLine();

                System.out.println(msg);

                if(clientsList.size() > 1)
                    broadcastMessage(msg);

                while (clientSocket.isConnected()) {
                    if ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                        if(clientsList.size() > 1)
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

        private void sendMsgDirectly(String msg){
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
// java.io.*;
//         import java.net.*;
//         import java.util.Map;
//         import java.util.concurrent.ConcurrentHashMap;
//
//public class ChatServer {
//    private static final Map<String, PrintWriter> clientsMap = new ConcurrentHashMap<>();
//
//    public static void main(String[] args) {
//        int port = Integer.parseInt(args[0]);
//
//        try (ServerSocket serverSocket = new ServerSocket(port)) {
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                ClientHandler clientHandler = new ClientHandler(clientSocket);
//                clientHandler.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class ClientHandler extends Thread {
//        private Socket clientSocket;
//        private BufferedReader reader;
//        private PrintWriter writer;
//        private String clientId;
//
//        public ClientHandler(Socket clientSocket) throws IOException {
//            this.clientSocket = clientSocket;
//            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            writer = new PrintWriter(clientSocket.getOutputStream(), true);
//        }
//
//        @Override
//        public void run() {
//            try {
//                String msg = reader.readLine();
//                String[] arr = msg.split(" ");
//                this.clientId = arr[0];
//
//                clientsMap.put(clientId, writer);
//
//                System.out.println(msg);
//                if(clientsMap.size() > 1)
//                    broadcastMessage(msg);
//
//                while (clientSocket.isConnected()) {
//                    if ((msg = reader.readLine()) != null) {
//                        System.out.println(msg);
//                        if(clientsMap.size() > 1)
//                            broadcastMessage(msg);
//                    }
//                }
//
//            } catch (Throwable e) {
//                handleClientDisconnection();
//            } finally {
//                handleClientDisconnection();
//            }
//        }
//
//        private void handleClientDisconnection() {
//            try {
//                reader.close();
//                writer.close();
//                clientSocket.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            clientsMap.remove(clientId);
//        }
//
//        private void broadcastMessage(String mess) {
//            for (Map.Entry<String, PrintWriter> e : clientsMap.entrySet()) {
//                if (!e.getKey().equals(this.clientId)) {
//                    e.getValue().println(mess);
//                    e.getValue().flush();
//                }
//            }
//        }
//    }
//}