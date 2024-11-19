package zad1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input;
        input = sc.nextInt();

        String bin = "";
        int tmp = input;
        for(int i=0;i<32;i++){
            bin = (tmp & 0b1) + bin;
            tmp = tmp >> 1;
        }
        System.out.println(bin);

        String quad = "";
        int tmp2 = input;
        for(int i=0;i<16;i++){
            quad = (tmp2 & 0b11) + quad;
            tmp2 = tmp2 >> 2;
        }
        System.out.println(quad);

        String oct = "";
        int tmp3 = input;
        for(int i=0;i<32/3;i++){
            oct = (tmp3 & 0b111) + oct;
            tmp3 = tmp3 >> 3;
        }
        System.out.println(oct);

        String hex = "";
        int tmp4 = input;
        for(int i=0;i<32/4;i++){
            int res = (tmp4 & 0b1111);
            hex = (char)(res > 9?('A'+ res%10):'0' + res) + hex;
            tmp4 = tmp4 >> 4;
        }
        System.out.println(hex);
    }
}