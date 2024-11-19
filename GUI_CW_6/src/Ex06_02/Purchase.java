package Ex06_02;

import java.io.*;
import java.util.*;

public class Purchase {
    private String name;
    private int price;

    public Purchase(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}

class ShoppingData {
    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(new File("/Users/jonaszsojka/IdeaProjects/GUI_CW_6/src/Data.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String, List<Purchase>> map = new HashMap<>();
        while (input.hasNext()) {
            String key = input.next();
            if (!map.containsKey(key)) {
                List<Purchase> purchaseList = new ArrayList<>();
                purchaseList.add(new Purchase(input.next(), input.nextInt()));
                map.put(key, purchaseList);
            } else {
                map.get(key).add(new Purchase(input.next(), input.nextInt()));
            }
        }
        for (String key : map.keySet()) {
            Set<String> uniqeNames = new HashSet<>();
            int sum = 0;
            for (Purchase p : map.get(key)) {
                uniqeNames.add(p.getName());
                sum += p.getPrice();
            }
            System.out.println(
                    key + " " +
                            map.get(key).size() + " " +
                            uniqeNames.size() + " " +
                            sum
            );
            saveDataToFile("Raport.txt",map);
        }
    }
    public static void saveDataToFile(String fileName, Map<String, List<Purchase>> map) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (String key : map.keySet()) {
                Set<String> uniqeNames = new HashSet<>();
                int sum = 0;
                for (Purchase p : map.get(key)) {
                    uniqeNames.add(p.getName());
                    sum += p.getPrice();
                }
                writer.write(
                        key + " " +
                                map.get(key).size() + " " +
                                uniqeNames.size() + " " +
                                sum + "\n"
                );
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}