package Task23_1;

import java.util.Scanner;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input;
        do{
            input = sc.nextInt();
        }while(input<0 || input > pow(2,31));

        //bin
        String bin = "";
        int tmp = input;
        for(int i=0;i<32;i++){
            bin = (tmp & 0b1) + bin;
            tmp = tmp >> 1;
        }
        System.out.println("bin: " + bin);

        String quad = "";
        int tmp2 = input;
        for(int i=0;i<16;i++){
            quad = (tmp2 & 0b11) + quad;
            tmp2 = tmp2 >> 2;
        }
        System.out.println("quad: " + quad);

        String oct = "";
        int tmp3 = input;
        for(int i=0;i<32/3;i++){
            oct = (tmp3 & 0b111) + oct;
            tmp3 = tmp3 >> 3;
        }
        System.out.println("oct: " + oct);

        String hex  = "";
        int tmp4 = input;
        for(int i=0;i<8;i++){
            int pom = (tmp4 & 0b1111);
            hex = (char)(pom>9?('A'+ pom%10):pom + '0') + hex;
            tmp4 = tmp4 >> 4;
        }
        System.out.println("hex: " + hex);
    }
}
