import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //obsulga wczytania danych treningowych i testowych
        ArrayList<RowData> tr_data;
        ArrayList<RowData> test_data;

        FileManager fileManager = new FileManager("/Users/jonaszsojka/IdeaProjects/PRI_projekt_og/iris_test.txt","/Users/jonaszsojka/IdeaProjects/PRI_projekt_og/iris_training.txt");
        tr_data = fileManager.chooseFileToLoad(InputDataType.TRAINING);
        test_data = fileManager.chooseFileToLoad(InputDataType.TEST);

        //wczytanie parametru k
        int k;
        System.out.println("Provide k value: ");
        Scanner sc = new Scanner(System.in);
        k = sc.nextInt();

        //statystyki
        AI ai = new AI(tr_data,test_data,k);
        ai.showStatistics();

        //reczne wpisywanie wektora atrybutow
        System.out.println("Manual input of data vector: ");

        boolean ifContinue = true;
        String decision;
        while(ifContinue){
            System.out.println("Do you want to input your own data? (Yes/No): ");
            decision = sc.next();

            //TAK -> klasyfikacja pojedynczego wektora
            if(decision.equalsIgnoreCase("yes")){
                String input_vector;
                input_vector = sc.next();
                input_vector += sc.nextLine();

                String[] parts = input_vector.split("\\s+");
                RowData data = fileManager.parseToRowData(parts);

                ai.categorizeWithResponse(data);
            }else if(decision.equalsIgnoreCase("no")){
                System.out.println("That's all for today. Hope you have a nice time!");
                ifContinue = false;
            }else{
                System.out.println("Invalid response! Please provide answer (YES/NO)");
            }
        }
        sc.close();
    }
}