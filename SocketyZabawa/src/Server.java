import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        ServerSocket serverSocket;

        try {
            // Tworzenie serwera i oczekiwanie na polaczenie z klientem
            serverSocket = new ServerSocket(123);
            System.out.println("Oczekiwanie na polaczenie z klientem...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Klient polaczony");

            // Przygotowanie pliku do wysłania
            File fileToSend = new File("src/file.txt");
            FileInputStream fileInputStream = new FileInputStream(fileToSend);

            OutputStream outputStream = clientSocket.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead); // Wysyłanie danych do klienta
            }

            System.out.println("Plik został wysłany.");

            fileInputStream.close();
            outputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
