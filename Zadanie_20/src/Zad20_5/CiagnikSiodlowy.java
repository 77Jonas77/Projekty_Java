package Zad20_5;

public class CiagnikSiodlowy extends PojazdKolowy{
    private double masa;

    public CiagnikSiodlowy(String color, int iloscOsi, double masa){
        super(color, iloscOsi);
        this.masa = masa;
    }

    public void rozpocznijJazde() {
        double nacisk = masa / super.getIloscOsi();
        if (nacisk > 11000) {
            System.out.println("Jazda niebezpieczna, odmowa uruchomienia silnika");
        } else {
            System.out.println("wrummmmm");
        }
    }

}
