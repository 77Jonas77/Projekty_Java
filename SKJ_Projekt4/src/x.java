import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class x {

    private static final Map<String, PrintWriter> clientsMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
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
        private String clientId;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(),true);
        }

        @Override
        public void run() {
            try {

                String msg = reader.readLine();
                String[] arr = msg.split(" ");
                this.clientId = arr[0];

                clientsMap.put(clientId, writer);

                if(clientsMap.size() > 1) {
                    broadcastMessage(msg);
                }
                System.out.println(msg);

                while (clientSocket.isConnected()) {
                    if((msg = reader.readLine())!=null) {
                        if(clientsMap.size() > 1)
                            broadcastMessage(msg);
                        System.out.println(msg);
                        broadcastMessage(msg);
                    }
                }

            } catch (Throwable e) {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                clientsMap.remove(clientId);

            }
        }

        private void broadcastMessage(String mess) {
            for (Map.Entry<String, PrintWriter> e : clientsMap.entrySet()) {
                if (!e.getKey().equals(this.clientId)) {
                    e.getValue().write(mess);
                }
            }
        }

    }
}
