package Wyklad_2;

public
class Cookie {

    private int weight;
    private String ingr;

    public Cookie(){
        this.weight = 10 - ((int)(Math.random()*10)-5);
        this.ingr = "cynamon";
    }

    public Cookie(int weight, String ingr){
        this.weight = weight;
        this.ingr = ingr;
    }

    public void show(){
        System.out.println(this+" "+this.weight+" "+this.ingr);
    }

    public static Cookie make(){
        return new Cookie((int)(Math.random()*20), "");
    }

    public int getWeight() {
        return weight;
    }

    public String getIngr() {
        return ingr;
    }
}
