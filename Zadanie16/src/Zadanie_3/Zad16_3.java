package Zadanie_3;

import java.util.Scanner;

import static java.lang.Math.pow;

public class Zad16_3 {
    public static boolean isArmstrongNumber(int num){
        int pom = num, pom2 = num, sum = 0, pot = 0;

        while(pom>0){
            pot++;
            pom/=10;
        }

        while(pom2>0){
            sum+=pow(pom2%10,pot);
            pom2/=10;
        }

        if(sum==num)
            return true;
        return false;

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number =  sc.nextInt();

        System.out.println(
                isArmstrongNumber(number)
        );
    }
}
