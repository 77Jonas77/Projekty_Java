import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileManager fileManager = new FileManager("iris_test.txt","iris_training.txt");
        fileManager.chooseFileToLoad(InputDataType.TRAINING);

    }
}