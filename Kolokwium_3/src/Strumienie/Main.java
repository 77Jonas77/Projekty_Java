package Strumienie;

import Dziedziczenie1.Person;

import java.io.*;

public class Main {
    private static Object Person;

    public static void main(String[] args) {
        File file = new File("odp.txt");

        //Wczytuje bajtowo po kolei
        try {
            FileInputStream fis = new FileInputStream(file);
            int tmp;
            while ((tmp = fis.read()) != -1) {
                System.out.println(tmp);
                System.out.println((char)tmp);
            }
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Wczytuje znakowo

        try {
            FileReader fr = new FileReader(file);
            int tmp;
            while ((tmp = fr.read()) != -1) {
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Wypisuje znakowo
        try {
            FileWriter fwr = new FileWriter(file);
            int tmp;
            fwr.write('x');
            fwr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int val = 16000000;

        //wypisuje bajtowo
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(val);
            fos.write(val >> 8);
            fos.write(val >> 16);
            fos.write(val >> 24);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            int res;
            res = fis.read();
            res = res | (fis.read()<<8);
            res = res | (fis.read()<<16);
            res = res | (fis.read()<<24);

            fis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //implements Serializable przy nazwie klasy
        //Wypisuje objekty
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Person);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            //String str = "";
            StringBuilder strBuilder = new StringBuilder();
            StringBuffer strBuffer = new StringBuffer();

            FileReader fr = new FileReader(file);
            int tmp;
            while( (tmp = fr.read()) != -1)
                strBuilder.append((char)tmp);
            System.out.println(strBuilder.toString());
            fr.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


