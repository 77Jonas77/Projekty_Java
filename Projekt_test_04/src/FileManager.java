import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileManager {

    private static File file = new File("leaderboard.bin");
    public static void saveToFile(ArrayList<Player> playersList) {
        try {
            FileOutputStream fos = new FileOutputStream(file);

            Comparator<Player> scoreComparator = Comparator.comparingInt(Player::getScore).reversed();
            playersList.sort(scoreComparator);

            int numPlayersToSave = Math.min(playersList.size(), 10);

            for (int i = 0; i < numPlayersToSave; i++) {
                fos.write(playersList.get(i).getUsername().length());
                fos.write(playersList.get(i).getUsername().getBytes());
                for (int j = 0; j < 4; j++) {
                    fos.write(playersList.get(i).getScore() >> (8 * j));
                }
            }

            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Player> readFromFile() {
        ArrayList<Player> playersList = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            while (fis.available() > 0) {
                int nameLength = fis.read();
                byte[] nameBytes = new byte[nameLength];
                fis.read(nameBytes);
                String username = new String(nameBytes);

                int score = 0;
                for (int j = 0; j < 4; j++) {
                    score |= fis.read() << (8 * j);
                }

                Player player = new Player(username, score);
                playersList.add(player);
            }

            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        playersList.sort(Comparator.comparingInt(Player::getScore));

        if (playersList.size() > 10) {
            playersList = new ArrayList<>(playersList.subList(0, 10));
        }

        return playersList;
    }

}
