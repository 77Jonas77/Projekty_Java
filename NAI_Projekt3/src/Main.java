import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//todo: okienko + klasyfikacja pojedynczego
public class Main {
    private static final String TRAINING_FILE = "/Users/jonaszsojka/Zadanka_Java/PRI_Projekt3/data-vectors/training-texts/training_vector_data.txt";
    private static final String TEST_FILE = "/Users/jonaszsojka/Zadanka_Java/PRI_Projekt3/data-vectors/test-texts/test_vector_data.txt";

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<RowData> tr_data;
        ArrayList<RowData> test_data;

        Scanner sc = new Scanner(System.in);

        FileManager fileManager = new FileManager();
        fileManager.trasformDataToVector();
        ArrayList<String> available_lang = fileManager.makeLanguagesList();
        tr_data = fileManager.chooseFileToLoad(InputDataType.TRAINING);
        test_data = fileManager.chooseFileToLoad(InputDataType.TEST);
        NeuralNetwork neuralNetwork = new NeuralNetwork(available_lang, tr_data, test_data);
        neuralNetwork.trainNeuralNetwork();
        neuralNetwork.showStatistics();

        //reczne wpisywanie wektora atrybutow
        System.out.println("Manual input of data vector: ");

        boolean ifContinue = true;
        String decision;
        while (ifContinue) {
            System.out.println("Do you want to input your own data? (Yes/No): ");
            decision = sc.next();

            //TAK -> klasyfikacja pojedynczego wektora
            if (decision.equalsIgnoreCase("yes")) {
                System.out.println("Input your text to classify language (press Enter twice to finish): ");
                TextEntryWindow textEntryWindow = new TextEntryWindow(fileManager, neuralNetwork);

            } else if (decision.equalsIgnoreCase("no")) {
                System.out.println("That's all for today. Hope you have a nice time!");
                ifContinue = false;
            } else {
                System.out.println("Invalid response! Please provide answer (YES/NO)");
            }
        }
        sc.close();

    }
}
