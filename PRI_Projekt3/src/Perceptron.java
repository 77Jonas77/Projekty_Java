import java.util.*;

public class Perceptron {
    private ArrayList<RowData> tr_data;
    private ArrayList<RowData> test_data;

    private double[] wagi;
    private String language;

    public Perceptron(ArrayList<RowData> tr_data, ArrayList<RowData> test_data, String language) {
        this.tr_data = tr_data;
        this.test_data = test_data;
        this.language = language;
        wagi = new double[tr_data.getFirst().getData_num().length];
        Arrays.fill(wagi, 0.5);
    }

    //vector classifier
    public String categorize(RowData vector) {
//        System.out.println(vector.getDecision() + " fakt->" + language);
//        System.out.println(Arrays.toString(vector.getData_num()));
//        System.out.println(Arrays.toString(wagi));
        double wyjscie = 0;
//        System.out.println(Arrays.toString(vector.getData_num()));
        for (int n = 0; n < test_data.getFirst().getData_num().length; n++) {
            wyjscie += wagi[n] * vector.getData_num()[n];
        }
//        System.out.println(wyjscie + " " +test_data.size() + " " + vector.getData_num().length + " " + wagi.length);
//
//        System.out.println("Vector danych: ");
//        for(int i= 0; i< vector.getData_num().length;i++){
//            System.out.print(vector.getData_num()[i] + " ");
//        }
//        System.out.println();
//        System.out.println("=====");
//        System.out.println();
//
//        System.out.println("Wagi: ");
//        for(int i= 0; i< wagi.length;i++){
//            System.out.print(wagi[i] + " ");
//        }
//        System.out.println();
//        System.out.println("=====");
        //System.out.println(wyjscie);
        return wyjscie >= 0 ? language : "NOPE";
    }

    //training one specific perceptron
    public void trainPerceptron() {
        int skutecznosc = 0;
        double wyjscie = 0;
        double[] wejscie;
        double alfa = 0.5;
        int y;
        int d;
        int checkloop = 0;
        boolean incorrectAtFirst = false;
        boolean trained = false;

        while (skutecznosc != tr_data.size()) {
            skutecznosc = 0;
            if (checkloop > 1000) {
                System.out.println("Nietrenowalny zbior");
                break;
            }

            //regula delta
            for (int i = 0; i < tr_data.size(); i++) {
                wejscie = tr_data.get(i).getData_num();
                trained = false;

                while (!trained) {
                    wyjscie = 0;

                    //iloczyn skalarny
                    for (int n = 0; n < tr_data.getFirst().getData_num().length; n++) {
                        wyjscie += wagi[n] * wejscie[n];
                    }

                    //Ewentualna modyfikacja wektora wag
                    y = wyjscie >= 0 ? 1 : 0;
                    d = tr_data.get(i).getDecision().equals(language) ? 1 : 0;

                    if (y != d) {
                        double[] wagi_prim = new double[wagi.length];

                        for (int k = 0; k < wagi.length; k++) {
                            wagi_prim[k] = wagi[k] + (d - y) * alfa * wejscie[k];
                        }

//                        for (int k = 0; k < wagi.length; k++) {
//                            System.out.print(wagi_prim[k] + " ");
//                        }
                        wagi = wagi_prim;
                        incorrectAtFirst = true;

                    } else if (!incorrectAtFirst) {
                        skutecznosc++;
                        trained = true;
                    } else {
                        trained = true;
                        incorrectAtFirst = false;
                    }
                }
            }
            checkloop++;
        }
    }
}
