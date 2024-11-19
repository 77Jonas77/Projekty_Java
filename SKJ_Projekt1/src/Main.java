import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    public static String recievedSumMD5;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);
        int port = sc.nextInt();

        Socket socket = new Socket("127.0.0.1", port);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        recievedSumMD5 = br.readLine().trim();
        String line = null;

        while (true) {
            if ((line = br.readLine()) != null && calculateSumMD5(line).equals(recievedSumMD5)) {
                out.println(line);
                break;
            }
        }
        sc.close();
        socket.close();
        br.close();
        out.close();
    }


    private static String calculateSumMD5(String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        String hex = "";
        byte[] byteArr;

        md = MessageDigest.getInstance("MD5");

        if (msg == null)
            return "";

        byteArr = md.digest(msg.getBytes(UTF_8));
        StringBuilder x = new StringBuilder();
        for (byte b : byteArr) {
            x.append(String.format("%02x", b));
        }
        hex = x.toString().toUpperCase();
        return hex;
    }
}