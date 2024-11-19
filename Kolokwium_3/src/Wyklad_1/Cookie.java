package Wyklad_1;

public
class Cookie {
    /*
        public static Cookie makeCookie(){
            return new Cookie(10, "czekolada");
        }
    */
    private int weight;
    private String ingr;

    public Cookie(int weight, String ingr){
        this.weight = weight;
        this.ingr = ingr;
    }
    public Cookie(int weight){
        this( weight, "czkolada");
    }

    public Cookie getHalf(){
        return new Cookie(this.weight /= 2, this.ingr);
    }


    public void show(){
        System.out.println(
                this+" "+this.weight+" "+this.ingr
        );
    }


}
