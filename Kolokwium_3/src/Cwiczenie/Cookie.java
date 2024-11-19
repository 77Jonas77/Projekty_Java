package Cwiczenie;

public class Cookie {
    private String name;
    private double weight;

    public Cookie(String name, double weight){
        this.name = name;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public void show(){
        System.out.println(this.name + " "  + this.weight);
    }
}
