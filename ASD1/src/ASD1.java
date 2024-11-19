import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ASD1 {
    public static void main(String[] args) {
        asd1(args[0]);
    }
    private static void asd1(String fileDir){
        int incr_sum = 0, incr_length = 1, max_length = 1, curr_in, prev_in, sum_forMax = 0, decr_length = 1, decr_sum = 0;

        try(Scanner sc = new Scanner(new File(fileDir))) {

            prev_in = sc.nextInt();
            sum_forMax = prev_in;

            incr_sum += prev_in;
            decr_sum += prev_in;

            while (sc.hasNext()) {
                curr_in = sc.nextInt();

                // ciag niemal
                if (curr_in >= prev_in) {
                    incr_length += 1;
                    incr_sum += curr_in;
                } else {
                    if (incr_length > max_length) {
                        max_length = incr_length;
                        sum_forMax = incr_sum;
                    }
                    incr_length = 1;
                    incr_sum = curr_in;
                }

                // ciag nieros
                if (curr_in <= prev_in) {
                    decr_length += 1;
                    decr_sum += curr_in;
                } else {
                    if (decr_length > max_length) { //ten ktory wczesniej
                        max_length = decr_length;
                        sum_forMax = decr_sum;
                    }
                    decr_length = 1;
                    decr_sum = curr_in;
                }
                prev_in = curr_in;
            }

            // spr dla ostat podciagu
            if (incr_length > max_length) {
                max_length = incr_length;
                sum_forMax = incr_sum;
            }
            if (decr_length > max_length) {
                max_length = decr_length;
                sum_forMax = decr_sum;
            }

            System.out.println(max_length + " " + sum_forMax);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

