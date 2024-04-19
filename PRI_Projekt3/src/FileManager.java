import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class FileManager {
    private final String TEST_FILE = "/Users/jonaszsojka/Zadanka_Java/PRI_Projekt3/data-vectors/test-texts/test_vector_data.txt";
    private final String TRAINING_FILE = "/Users/jonaszsojka/Zadanka_Java/PRI_Projekt3/data-vectors/training-texts/training_vector_data.txt";
    private final File CREATE_DATA_TRAINING_FN = new File("/Users/jonaszsojka/Zadanka_Java/PRI_Projekt3/data-texts/training-texts");
    private final File CREATE_DATA_TEST_FN = new File("/Users/jonaszsojka/Zadanka_Java/PRI_Projekt3/data-texts/test-texts");
    private String test_fn;
    private String training_fn;

    public FileManager(String test_fn, String training_fn) {
        this.test_fn = test_fn;
        this.training_fn = training_fn;
    }

    public FileManager() {
        //...
    }


    //Transforming data from indicated dir to data with vectors and returns list of languages to identify
    public void trasformDataToVector() {
        try {
            transformDataToVectorFile(Paths.get(CREATE_DATA_TRAINING_FN.toURI()), TRAINING_FILE);
            transformDataToVectorFile(Paths.get(CREATE_DATA_TEST_FN.toURI()), TEST_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.test_fn = TEST_FILE;
        this.training_fn = TRAINING_FILE;
    }

    public ArrayList<String> makeLanguagesList() {
        File folder = CREATE_DATA_TRAINING_FN;
        ArrayList<String> languages = new ArrayList<>();
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            languages.add(fileEntry.getName());
        }
        return languages;
    }

    public RowData parseToRowDataFromText(String[] text) {
        int[] count_alphabet = new int[26];
        for (String word : text) {
            for (int i = 0; i < word.length(); i++) {
                if (Character.isLetter(word.charAt(i)) && word.toLowerCase().charAt(i) >= 'a' && word.toLowerCase().charAt(i) <= 'z') {
                    char lowercaseChar = Character.toLowerCase(word.charAt(i));
                    count_alphabet[lowercaseChar - 'a']++;
                }
            }
        }
        RowData rowData = new RowData();
        double[] data_num = new double[27];

        //save data to RowData
        for (int i = 0; i < count_alphabet.length; i++) {
            data_num[i] = count_alphabet[i];
        }
        data_num[data_num.length - 1] = -1;
        rowData.setData_num(data_num);
        return rowData;
    }

    //Transforming data from indicated dir to data with vectors + decisions
    private void transformDataToVectorFile(Path folder, String FileToTransform) throws IOException {
        FileWriter fileWriter = new FileWriter(FileToTransform);
        Files.walkFileTree(folder, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    String decision = String.valueOf(file.getParent().getFileName());

                    Scanner sc = new Scanner(file);
                    int[] count_alphabet = new int[26];
                    while (sc.hasNext()) {
                        String word = sc.next();
                        for (int i = 0; i < word.length(); i++) {
                            if (Character.isLetter(word.charAt(i)) && word.toLowerCase().charAt(i) >= 'a' && word.toLowerCase().charAt(i) <= 'z') {
                                char lowercaseChar = Character.toLowerCase(word.charAt(i));
                                count_alphabet[lowercaseChar - 'a']++;
                            }
                        }
                    }
                    sc.close();
                    //save data to file
                    for (int i = 0; i < count_alphabet.length; i++) {
                        fileWriter.write(count_alphabet[i] + " ");
                    }
                    fileWriter.write(decision);
                    fileWriter.write('\n');
                }
                return FileVisitResult.CONTINUE;
            }
        });
        fileWriter.close();
    }

    //choosing which file to load -> idk why like that ;)
    public ArrayList<RowData> chooseFileToLoad(InputDataType type) throws FileNotFoundException {
        return switch (type) {
            case TRAINING -> loadData(training_fn);
            case TEST -> loadData(test_fn);
        };
    }

    //loading data from file
    public ArrayList<RowData> loadData(String filename) throws FileNotFoundException {
        ArrayList<RowData> loadedData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");
            loadedData.add(parseToRowData(parts));
        }

        double[] max = Arrays.copyOf(loadedData.getFirst().getData_num(), loadedData.getFirst().getData_num().length);
        double[] min = Arrays.copyOf(loadedData.getFirst().getData_num(), loadedData.getFirst().getData_num().length);

        for (int i = 1; i < loadedData.size(); i++) {
            for (int j = 0; j < max.length - 1; j++) {
                if (max[j] < loadedData.get(i).getData_num()[j]) {
                    max[j] = loadedData.get(i).getData_num()[j];
                }

                if (min[j] > loadedData.get(i).getData_num()[j]) {
                    min[j] = loadedData.get(i).getData_num()[j];
                }
            }
        }

        //Normalization for vector of data (0-1)
        for (int i = 0; i < loadedData.size() - 1; i++) {
            for (int j = 0; j < loadedData.get(i).getData_num().length; j++) {
                loadedData.get(i).getData_num()[j] = (loadedData.get(i).getData_num()[j]);
                //loadedData.get(i).getData_num()[j] = (loadedData.get(i).getData_num()[j]) / (max[j] + min[j]);
            }
        }

        scanner.close();
        return loadedData;
    }

    //Parsing data from files (String tables) to RowData type (double[] and String Decision)
    public RowData parseToRowData(String[] stringArray) {
        double[] doubleArray = new double[stringArray.length];
        RowData rowData = new RowData();
        for (int i = 0; i < stringArray.length; i++) {
            if (i != stringArray.length - 1) {
                //String number = stringArray[i].replace(',', '.');
                doubleArray[i] = Double.parseDouble(stringArray[i]);
            } else {
                if (!Objects.equals(stringArray[i], "")) {
                    rowData.setDecision(stringArray[i]);
                }
                doubleArray[i] = -1;
            }

        }
//        for (int x = 0; x < doubleArray.length; x++) {
//            System.out.print(doubleArray[x] + " x");
//        }
//        System.out.println();
//        System.out.println("============");
        rowData.setData_num(doubleArray);
        return rowData;
    }
}

