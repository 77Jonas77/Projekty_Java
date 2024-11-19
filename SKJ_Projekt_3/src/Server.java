import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.List;
import java.nio.file.*;
import java.util.stream.Collectors;

public class Server {
//to jest tera 4 projekt
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try {
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket serverSocket = sslServerSocketFactory.createServerSocket(port);

            while (true) {
                SSLSocket client = (SSLSocket) serverSocket.accept();

                try (
                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter writer = new PrintWriter(client.getOutputStream(), false)
                ) {
                    handleCommands(reader, writer, client);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleCommands(BufferedReader reader, PrintWriter writer, SSLSocket client) throws IOException {
        client.startHandshake();
        String command;
        while ((command = reader.readLine()) != null) {

            String[] tokens = command.split("\\s+");
            if (tokens.length == 2 && tokens[0].equals("FILE") && tokens[1].equals("LIST")) {
                sendFileList(writer);
            } else if (tokens.length == 2 && tokens[0].equals("GET")) {
                sendFileContent(writer, tokens[1]);
            } else {
                writer.println("NO SUCH COMMAND");
                writer.flush();
            }
        }
    }

    private static void sendFileContent(PrintWriter writer, String fileName) {

        String filePath = "files/" + fileName;
        if (Files.exists(Paths.get(filePath))) {
            try {
                List<String> fileContent = Files.readAllLines(Paths.get(filePath));
                StringBuilder msgToSend = new StringBuilder();
                for (String content : fileContent) {
                    msgToSend.append(content).append(' ');
                }
                writer.println(msgToSend);
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            writer.println("NO SUCH FILE");
            writer.flush();
        }
    }

    private static void sendFileList(PrintWriter writer) {
        try {
            List<String> fileList = Files.list(Paths.get("files/"))
                    .filter(path -> path.toString().endsWith(".txt"))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            ;
            StringBuilder msgToSend = new StringBuilder();
            int index = 0;
            for (String fileName : fileList) {
                if (index + 1 < fileList.size()) {
                    msgToSend.append(fileName).append('\n');
                } else {
                    msgToSend.append(fileName);
                }
                index++;
            }
            writer.println(msgToSend);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}