import java.util.*;

public class AI {
    private ArrayList<RowData> tr_data;
    private ArrayList<RowData> test_data;
    private int k;

    public AI(ArrayList<RowData> tr_data, ArrayList<RowData> test_data, int k) {
        this.tr_data = tr_data;
        this.test_data = test_data;
        this.k = k;
    }

    //metoda odpowiadajaca za przedstawienie skutecznosci naszego programu
    public void showStatistics() {
        int correct = 0;
        for(int i=0;i<test_data.size();i++){
            RowData vector = test_data.get(i);
            if(categorize(vector).equals(vector.getDecision()))
                correct++;
        }
        System.out.println("Classified correctly: " + correct + " Effectiveness: " + ((double)correct/test_data.size()) +"%");
    }

    //metoda odpowiadajaca za klasyfikacje wektora + zwraca komunikat
    public void categorizeWithResponse(RowData vector) {
        System.out.println("AI prediction: ");
        System.out.println(categorize(vector));
    }

    //metoda odpowiadajaca za klasyfikacje wektora
    public String categorize(RowData vector) {
        ArrayList<RowData> eukl_values = new ArrayList<>();

        for (int i = 0; i < tr_data.size(); i++) {
            RowData tr_line = tr_data.get(i);
            RowData pairsOfEukl = new RowData();

            pairsOfEukl.setDecision(tr_line.getDecision());

            double eukl_val = 0;
            for (int j = 0; j < vector.getData_num().length; j++) {
                eukl_val += Math.pow(tr_line.getData_num()[j] - vector.getData_num()[j], 2);
            }

            pairsOfEukl.setData_num(new double[]{Math.sqrt(eukl_val)});
            eukl_values.add(pairsOfEukl);

//            System.out.println("Dane vector: " );
//            for(int j=0;j<vector.getData_num().length;j++){
//                System.out.print(vector.getData_num()[j] + " ");
//            }
//            System.out.print("======vector======");
//
//            System.out.print("Dane treningowe: " );
//            for(int j=0;j<tr_line.getData_num().length;j++){
//                System.out.print(tr_line.getData_num()[j] + " ");
//            }
//            System.out.print("======training======");
//
//            System.out.println(pairsOfEukl.getDecision());
        }

        //sortujemy arrayliste
        eukl_values.sort((o1, o2) -> {
            // Porównujemy wartości na pozycji indeksu 1 w obu tablicach
            double eukl1 = o1.getData_num()[0];
            double eukl2 = o2.getData_num()[0];

            if (eukl1 < eukl2) {
                return -1; // Pierwszy element jest mniejszy
            } else if (eukl1 > eukl2) {
                return 1; // Pierwszy element jest większy
            } else {
                return 0; // Obie wartości są równe
            }
        });

//        for(RowData rowData : eukl_values){
//            System.out.println(rowData.toString());
//        }

//        for (double[] x : eukl_values) {
//            System.out.println(x[0] + " " + x[1]);
//        }

        //liczenie wystapien z k sasiadow
        //kNN - liczenie
        HashMap<String,Integer> countAppearance = new HashMap<>();
        for (int i = 0; i < k; i++) {
//            countAppearance[(eukl_values.get(i).getDecision()-1]++;
            String k = eukl_values.get(i).getDecision();
            countAppearance.put(k, countAppearance.getOrDefault(k, 0) + 1);

        }

//        for(Map.Entry<String, Integer> map : countAppearance.entrySet()){
//            System.out.println(map);
//        }

        //Rozstrzygniecie
        int maxCount = 0;
        String maxKey = tr_data.getFirst().getDecision();

        for(Map.Entry<String, Integer> el : countAppearance.entrySet()){
            if(el.getValue() > maxCount){
                maxKey = el.getKey();
                maxCount = el.getValue();
            }
        }

        return maxKey;
    }
}
