import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            // Laczenie clienta z serwerem
            Socket socket = new Socket("localhost", 123);
            System.out.println("Polaczono z serwerem!");

            // Odbior i tworzenie pliku z przechwycona zawartoscia
            InputStream inputStream = socket.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("recievedFile.txt");

            int x;
            while ( (x = inputStream.read()) != -1) {
                fileOutputStream.write(x);
            }
            System.out.println("Plik odebrany!");

            fileOutputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
