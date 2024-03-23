import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
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

        //Znalezienie max i min dla kazdej kolumny //zmiana: wylaczenie ostatniej kol
        double[] max = Arrays.copyOf(loadedData.getFirst().getData_num(), loadedData.getFirst().getData_num().length);
        double[] min = Arrays.copyOf(loadedData.getFirst().getData_num(), loadedData.getFirst().getData_num().length);

        for (int i = 1; i < loadedData.size(); i++) {
            for (int j = 0; j < max.length-1; j++) {
                if (max[j] < loadedData.get(i).getData_num()[j]) {
                    max[j] = loadedData.get(i).getData_num()[j];
                }

                if (min[j] > loadedData.get(i).getData_num()[j]) {
                    min[j] = loadedData.get(i).getData_num()[j];
                }
            }
        }

        //normalizacja wektorow danych (0-1)
        for (int i = 0; i < loadedData.size()-1; i++) {
            for (int j = 0; j < loadedData.get(i).getData_num().length; j++) {
                loadedData.get(i).getData_num()[j] = (loadedData.get(i).getData_num()[j]) / (max[j] + min[j]);
            }
        }

        scanner.close();
        return loadedData;
    }

    //metoda odpowiadajaca za parsowanie el tab String na double //zmiana: dodanie -1
    public RowData parseToRowData(String[] stringArray) {
        double[] doubleArray = new double[stringArray.length];
        RowData rowData = new RowData();
        String typ;
        for (int i = 0; i < stringArray.length; i++) {
            if (i != stringArray.length - 1) {
                String number = stringArray[i].replace(',', '.');
                doubleArray[i] = Double.parseDouble(number);
            } else {
                if(!Objects.equals(stringArray[i], "")) {
                    typ = stringArray[i].equals("Iris-setosa") ? "Iris-setosa" : "Iris-nie-setosa";
                    rowData.setDecision(typ);
                }else{

                }
                doubleArray[i]=-1;
            }
        }
        rowData.setData_num(doubleArray);
        return rowData;
    }
}

