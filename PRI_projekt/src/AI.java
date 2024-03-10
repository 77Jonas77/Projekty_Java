import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class AI {
    private ArrayList<double[]> tr_data;
    private ArrayList<double[]> test_data;
    private int k;

    public AI(ArrayList<double[]> tr_data, ArrayList<double[]> test_data, int k) {
        this.tr_data = tr_data;
        this.test_data = test_data;
        this.k = k;
    }

    //metoda odpowiadajaca za przedstawienie skutecznosci naszego programu
    public void showStatistics() {
        int correct = 0;
        for(int i=0;i<test_data.size();i++){
            double[] vector = test_data.get(i);
            if(categorize(vector)==(int)vector[vector.length-1])
                correct++;
        }
        System.out.println("Classified correctly: " + correct + " Effectiveness: " + ((double)correct/test_data.size()) +"%");
    }

    //metoda odpowiadajaca za klasyfikacje wektora + zwraca komunikat
    public void categorizeWithResponse(double[] vector) {
        System.out.println("AI prediction: ");
        switch (categorize(vector)){
            case 1 -> System.out.println("Iris-virginica");
            case 2 -> System.out.println("Iris-versicolor");
            case 3 -> System.out.println("Iris-setosa" );
        }
    }

    //metoda odpowiadajaca za klasyfikacje wektora
    public int categorize(double[] vector) {
        ArrayList<double[]> eukl_values = new ArrayList<>();
        int num_of_atryb = tr_data.getFirst().length;

        for (int i = 0; i < tr_data.size(); i++) {
            double[] tr_line = tr_data.get(i);
            double[] pairsOfEukl = new double[2]; // Deklaracja nowej tablicy dla każdej iteracji
            pairsOfEukl[0] = tr_line[num_of_atryb - 1];
            double eukl_val = 0;

            for (int j = 0; j < vector.length - 1; j++) {
                eukl_val += Math.pow(tr_line[j] - vector[j], 2);
            }

            pairsOfEukl[1] = Math.sqrt(eukl_val);
            eukl_values.add(pairsOfEukl);
            //            System.out.println("Dane vector: " );
//            for(int j=0;j<vector.length;j++){
//                System.out.print(vector[j] + " ");
//            }
//            System.out.print("======vector======");
//
//            System.out.print("Dane treningowe: " );
//            for(int j=0;j<tr_line.length;j++){
//                System.out.print(tr_line[j] + " ");
//            }
//            System.out.print("======training======");
//
//            System.out.println(pairsOfEukl[1]);
        }

        //sortujemy arrayliste
        eukl_values.sort((o1, o2) -> {
            // Porównujemy wartości na pozycji indeksu 1 w obu tablicach
            double eukl1 = o1[1];
            double eukl2 = o2[1];

            if (eukl1 < eukl2) {
                return -1; // Pierwszy element jest mniejszy
            } else if (eukl1 > eukl2) {
                return 1; // Pierwszy element jest większy
            } else {
                return 0; // Obie wartości są równe
            }
        });

//        for (double[] x : eukl_values) {
//            System.out.println(x[0] + " " + x[1]);
//        }

        //liczenie wystapien z k sasiadow
        //kNN - liczenie
        int[] countAppearance = new int[3];
        for (int i = 0; i < k; i++) {
            countAppearance[(int)(eukl_values.get(i)[0])-1]++;
        }

        //todo: co w przypadku kiedy rowne?
        //int tie = 0;

        //Rozstrzygniecie
        int maxIndex = 1;
        int maxCount = countAppearance[0];

        for (int i = 0; i < countAppearance.length; i++) {
            if (countAppearance[i] > maxCount) {
                maxIndex = i+1;
                maxCount = countAppearance[i];
            }
        }
        return maxIndex;
    }
}
