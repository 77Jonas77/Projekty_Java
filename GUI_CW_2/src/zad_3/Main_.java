package zad_3;

public class Main_ {
    public static void main(String[] args) {

        Singer s1 = new Singer("Martin") {
            @Override
            public String toString() {
                super.text = "eima siema ELO baju jajo";
                return super.toString();
            }
            @Override
            String sing() {
                return "eima siema ELO baju jajo";
            }
        };

        Singer s2 = new Singer("Joplin") {
            @Override
            public String toString() {
                super.text = "\"ALLEJUJA simon siema\"";
                return super.toString();
            }
            @Override
            String sing() {
                return "ALLEJUJA simon siema";
            }
        };

        Singer s3 = new Singer("Houston") {
            @Override
            public String toString() {
                super.text = "hue hue p d e";
                return super.toString();
            }
            @Override
            String sing() {
                return "hue hue p d e";
            }

        };

        Singer sng[] = {s1, s2, s3};
        for (Singer s : sng)
            System.out.println(s);
        System.out.println("\n" + Singer.loudest(sng));
    }
}
