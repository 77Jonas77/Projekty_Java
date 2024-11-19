package Ex07_02;

import java.util.*;

public class CarDealership {
    public static void main(String[] args) {
        String[] arr = {
                "salon A", "Mercedes", "130000",
                "salon B", "Mercedes", "120000",
                "salon C", "Ford", "110000",
                "salon B", "Opel", "90000",
                "salon C", "Honda", "95000",
                "salon A", "Ford", "105000",
                "salon A", "Renault", "75000"
        };

        Map<String, List<Car>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i += 3) {
            String dealership = arr[i];
            String make = arr[i + 1];
            int price = Integer.parseInt(arr[i + 2]);
            Car car = new Car(make, price);

            if (!map.containsKey(dealership)) {
                map.put(dealership, new ArrayList<>());
            }
            car.setDealership(dealership);
            map.get(dealership).add(car);
        }

        System.out.println(map);

        Car cheapestCar = null;
        for (List<Car> cars : map.values()) {
            for (Car car : cars) {
                if (cheapestCar == null || car.getPrice() < cheapestCar.getPrice()) {
                    cheapestCar = car;
                }
            }
        }
        System.out.println(cheapestCar.getMake() + " in " + cheapestCar.getDealership() + " for " + cheapestCar.getPrice());
    }
}

class Car {
    private String make;
    private int price;
    private String dealership;

    public Car(String make, int price) {
        this.make = make;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public int getPrice() {
        return price;
    }

    public String getDealership() {
        return dealership;
    }

    public void setDealership(String dealership) {
        this.dealership = dealership;
    }

    @Override
    public String toString() {
        return make + " " + price;
    }
}

