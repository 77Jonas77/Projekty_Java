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
    public void chooseFileToLoad(InputDataType type) throws FileNotFoundException {
        switch(type){
            case TRAINING -> loadData(training_fn);
            case TEST -> loadData(test_fn);
        }
    }
    //metoda odpowiadajaca za wczytywanie danych z pliku
    private void loadData(String filename) throws FileNotFoundException {
        ArrayList<double[]> loadedData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            //usuniecie bialych znakow od konca i poczatku
            String line = scanner.nextLine().trim();

            //usuniecie bialych znakow miedzy danymi
            String[] parts = line.split("\\s+");

            //parsowanie danych
            double[] data = parseStringArrToDoubleArr(parts);
            loadedData.add(data);
        }
//        for(double[] el : loadedData){
//            for(int i =0; i<el.length;i++){
//                System.out.print(el[i] + " ");
//            }
//            System.out.println();
//        }
        scanner.close();
    }

    //metoda odpowiadajaca za parsowanie el tab String na double
    private double[] parseStringArrToDoubleArr(String[] stringArray) {
        double[] doubleArray = new double[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            if (i != stringArray.length - 1) {
                String number = stringArray[i].replace(',', '.');
                doubleArray[i] = Double.parseDouble(number);
            } else {
                if (!stringArray[i].isEmpty()) {
                    switch (stringArray[i]) {
                        case "Iris-virginica" -> doubleArray[i] = 1;
                        case "Iris-versicolor" -> doubleArray[i] = 2;
                        case "Iris-setosa" -> doubleArray[i] = 3;
                    }
                }
            }
        }
        return doubleArray;
    }
}

