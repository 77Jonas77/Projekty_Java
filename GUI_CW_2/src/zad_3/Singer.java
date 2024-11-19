package zad_3;

public abstract class Singer {
    public String imie;
    public static int count=1;
    public int nr;

    public String text;
    public Singer(String imie) {
        this.imie = imie;
        this.nr = count++;
    }

    abstract String sing();

    public String toString() {
        return "("+nr+") "+imie + ": " + this.text;
    }

    public static String loudest(Singer[] singers){
        int[] tab = new int[singers.length];

        for(int i=0;i<singers.length;i++){
            if(singers[i].sing().charAt(i)>='A' && singers[i].sing().charAt(i)<='Z'){
                tab[i]++;
            }
        }
        int max = tab[0];
        int maxi = 0;
        for(int i=1;i<singers.length;i++){
            if(max < tab[i]){
                max = tab[i];
                maxi = i;
            }
        }
        return "("+singers[maxi].nr+") "+singers[maxi].imie + ": " +singers[maxi].sing();
    }
}
