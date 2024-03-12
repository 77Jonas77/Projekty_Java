import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileManager {
    private String test_fn;
    private String training_fn;

    public FileManager(String test_fn, String training_fn) {
        this.test_fn = test_fn;
        this.training_fn = training_fn;
    }

    //metoda odpowiadajaca za wybranie ktory plik wczytac
    public ArrayList<RowData> chooseFileToLoad(InputDataType type) throws FileNotFoundException {
        return switch (type) {
            case TRAINING -> loadData(training_fn);
            case TEST -> loadData(test_fn);
        };
    }
    //metoda odpowiadajaca za wczytywanie danych z pliku
    public ArrayList<RowData> loadData(String filename) throws FileNotFoundException {
        ArrayList<RowData> loadedData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            //usuniecie bialych znakow od konca i poczatku
            String line = scanner.nextLine().trim();

            //usuniecie bialych znakow miedzy danymi
            String[] parts = line.split("\\s+");

            //parsowanie danych
            loadedData.add(parseToRowData(parts));
        }
//        ®

        scanner.close();
        return loadedData;
    }

    //metoda odpowiadajaca za parsowanie el tab String na double
    public RowData parseToRowData(String[] stringArray) {
        double[] doubleArray = new double[stringArray.length-1];
        RowData rowData = new RowData();

        for (int i = 0; i < stringArray.length; i++) {
            if (i != stringArray.length - 1) {
                String number = stringArray[i].replace(',', '.');
                doubleArray[i] = Double.parseDouble(number);
            } else {
                rowData.setDecision(stringArray[i]);
            }
        }

        rowData.setData_num(doubleArray);
        return rowData;
    }
}

