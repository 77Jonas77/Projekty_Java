import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {
    private BufferedWriter out;
    private BufferedReader in;
    private String username;
    private Socket clientSocket;

    private CP cp;

    public ClientHandler(Socket clientSocket, CP cp) {
        try {
            this.clientSocket = clientSocket;
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.cp = cp;
            this.username = in.readLine();
            broadcastMsgToGroupChat("SERVER: " + username + " has joined the chat!");
        } catch (IOException e) {
            closeClientHandler();
        }
    }

    private void closeClientHandler() {
        removeClientHandler();
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientHandler() {
        Server.clientHandlersList.remove(this);
        broadcastMsgToGroupChat("SERVER: " + username + " has left the chat!");
    }


    private void broadcastMsgToGroupChat(String msg) {
        try {
            for (ClientHandler c : Server.clientHandlersList) {
                c.out.write("GROUP_CHAT/" + username + ": " + msg);
                c.out.newLine();
                c.out.flush();
            }
        } catch (IOException e) {
            closeClientHandler();
        }
    }

    private void broadcastMsgToPerson(String reciverUsername, String msg) { //wysylamy na outputs, a z drugiej strony czytamy z inputS?
        boolean isFound = false;
        try {
            for (ClientHandler c : Server.clientHandlersList) {
                if (c.getUsername().equals(reciverUsername)) { //moze wyslac do samego siebie
                    c.out.write("PRIVATE/" + username + ": " + msg);
                    c.out.newLine();
                    c.out.flush();
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                this.out.write("SEVER: Can't find: " + reciverUsername + " username! Please check whether it's correct.");
                this.out.newLine();
                this.out.flush();
            }
        } catch (IOException e) {
            closeClientHandler();
        }
    }

    @Override
    public void run() {
        String command;
        String msgToSend;
        Pattern patternG = Pattern.compile("^/g .*");
        Pattern patternP = Pattern.compile("^/p .*");
        try {
            while (clientSocket.isConnected()) {
                command = in.readLine();
                Matcher matcherG = patternG.matcher(command);
                Matcher matcherP = patternP.matcher(command);

                msgToSend = command.substring(3);

                if (matcherG.find()) {
                    broadcastMsgToGroupChat(msgToSend);
                } else if (matcherP.find()) {
                    String reciverUsername;
                    reciverUsername = command.split("\\s+")[1];
                    msgToSend = msgToSend.substring(reciverUsername.length() + 1);
                    broadcastMsgToPerson(reciverUsername, msgToSend);
                } else {
                    this.out.write("\u001B[31m" + "SERVER: Incorrect command structure! Please provide correct one:\n1. GLOBAL_CHAT: '/g msg'\n2. PRIVATE_CHAT: '/p username msg'!"+"\u001B[0m"); //todo: czy to wyswietla sie dla wszystkich czy klienta i czy ladnie?
                    this.out.newLine();
                    this.out.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }
}