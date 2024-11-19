package zad_1;

import java.util.Objects;

public class OnlineStore extends ShoppingCart {
    private Product[] products = new Product[20];
    private int indeks;

    public OnlineStore() {
        this.indeks = 0;
    }

    public static void main(String[] args) {
        OnlineStore onlineStore = new OnlineStore();
        Product phone = new Electronics("IPhone X", 1500);
        Product bluza = new Clothing("Black Hoodie", 299.99);
        Product book = new Clothing("Star Wars", 49.99);

        onlineStore.addProduct(phone);
        onlineStore.addProduct(bluza);
        onlineStore.addProduct(book);

        onlineStore.getTotalPrice();

        onlineStore.removeProduct(phone);
        onlineStore.getTotalPrice();
        onlineStore.show();
    }

    public void addProduct(Product product) {
        products[indeks++] = product;
    }

    public void show(){
        for(int i=0;i<products.length;i++){
            if(products[i]!=null)
                System.out.println(products[i].getPrice() + " " +products[i].getName());
        }
    }
    public void removeProduct(Product product) {
        Product[] tmp = new Product[20];
        int count=0;

        for (int i = 0; i < products.length; i++) {
            if(product.equals(products[i])){
                products[i]=null;
            }
        }

        for(int i=0;i<products.length;i++){
            if(products[i]!=null){
                tmp[count++]=products[i];
            }
        }
        indeks--;
    }
    public void getTotalPrice() {
        double sum = 0;
        for (int i = 0; i < products.length; i++) {
            if(products[i]!=null)
                sum += products[i].getPrice();
        }
        System.out.println("laczna cena: " + sum);
    }
}

abstract class Product extends Object{
    String name;
    double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public boolean equals(Product o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }
}

abstract class ShoppingCart {
    public void addProduct(Product product) {
    }

    public void removeProduct(Product product) {
    }

    public void getTotalPrice() {
    }
}

class Electronics extends Product {
    public Electronics(String name, double price) {
        super(name, price);

    }
    public boolean equals(Product o) {
        return super.equals(o);
    }
}

class Clothing extends Product {
    public Clothing(String name, double price) {
        super(name, price);
    }
    public boolean equals(Product o) {
        return super.equals(o);
    }
}

class Book extends Product {
    public Book(String name, double price) {
        super(name, price);
    }
    public boolean equals(Product o) {
        return super.equals(o);
    }
}


