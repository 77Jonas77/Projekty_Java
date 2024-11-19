import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CompareFiles {

    public static void main(String[] args) {
        String file1Path = "/Users/jonaszsojka/IdeaProjects/compareFile/src/out10.txt";
        String file2Path = "/Users/jonaszsojka/IdeaProjects/compareFile/src/myFile.txt";

        try {
            if (areFilesEqual(file1Path, file2Path)) {
                System.out.println("Pliki są identyczne.");
            } else {
                System.out.println("Pliki nie są identyczne.");
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas porównywania plików: " + e.getMessage());
        }
    }

    public static boolean areFilesEqual(String filePath1, String filePath2) throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

            String[] numbers1, numbers2;
            numbers1=reader1.readLine().split(" ");
            numbers2=reader2.readLine().split(" ");

            if (numbers1.length != numbers2.length) {
                // Różna liczba liczb w liniach, uznajemy za różne
                return false;
            }

            for (int i = 0; i < numbers1.length; i++) {
                if (!numbers1[i].equals(numbers2[i])) {
                    // Różna liczba w danej pozycji, uznajemy za różne
                    return false;
                }
            }
           return true;
        }
    }
}
