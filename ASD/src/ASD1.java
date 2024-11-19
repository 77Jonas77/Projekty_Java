import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class ASD1 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File(""));

        int currNumber;
        int previousNumber = -1;
        int currLength = 0;
        int currSum = 0;
        int maxLength = 0;
        int maxSum = currSum;

        while (scanner.hasNext()) {
            currNumber = scanner.nextInt();

            if (currNumber >= previousNumber) {
                currLength++;
                currSum += currNumber;
                previousNumber = currNumber;
                if (currLength > maxLength) {
                    System.out.println(currNumber);
                    maxLength = currLength;
                    maxSum = currSum;
                }
            } else {
                currLength = 0;
                currSum = currNumber;
            }
        }
        System.out.println(maxLength + " " + maxSum);
    }
}