import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int port = sc.nextInt();
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("127.1.1.0", port);
            sslSocket.startHandshake();
            InputStream in = sslSocket.getInputStream();
            byte[] recievedata = new byte[20];

            in.read(recievedata,0, 20);

            System.out.println(new String(recievedata));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
