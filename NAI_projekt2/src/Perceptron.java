import java.util.*;

public class Perceptron {
    private ArrayList<RowData> tr_data;
    private ArrayList<RowData> test_data;

    private double[] wagi;

    public Perceptron(ArrayList<RowData> tr_data, ArrayList<RowData> test_data) {
        this.tr_data = tr_data;
        this.test_data = test_data;
        wagi = new double[test_data.getFirst().getData_num().length];
        Arrays.fill(wagi, 0.5);
    }

    //metoda odpowiadajaca za przedstawienie skutecznosci naszego programu
    public void showStatistics() {
        int correct = 0;
        trainPerceptron();

        for(int i=0;i<test_data.size();i++){
            RowData vector = test_data.get(i);
            //System.out.println(categorize(vector) + " dec: " + vector.getDecision());
            if(categorize(vector).equals(vector.getDecision())) {
                correct++;
                //System.out.println(categorize(vector) + " dec2: " + vector.getDecision());
            }
        }
        System.out.println("Classified correctly: " + correct + " Effectiveness: " + ((double)correct/test_data.size())*100 +"%");
    }

    //metoda odpowiadajaca za klasyfikacje wektora + zwraca komunikat
    public void categorizeWithResponse(RowData vector) {
        System.out.println("AI prediction: ");
        System.out.println(categorize(vector));
    }

    //metoda odpowiadajaca za klasyfikacje wektora

    public String categorize(RowData vector) {
        double wyjscie = 0;

        for (int n = 0; n < tr_data.getFirst().getData_num().length; n++) {
            wyjscie += wagi[n] * vector.getData_num()[n];
        }

        return wyjscie>=0?"Iris-setosa":"Iris-nie-setosa";
    }

    private void trainPerceptron(){
        int skutecznosc = 0;
        double wyjscie = 0;
        double[] wejscie;
        double alfa = 0.5;
        int y;
        int d;
        int checkloop = 0;
        boolean incorrectAtFirst=false;
        boolean trained = false;

        while(skutecznosc!=tr_data.size()){
            skutecznosc = 0;
            if(checkloop > 1000) {
                System.out.println("Nietrenowalny zbior");
                break;
            }

            //regula delta
            for(int i=0;i<tr_data.size();i++){
                wejscie = tr_data.get(i).getData_num();
                trained = false;

                while(!trained) {
                    wyjscie = 0;

                    //iloczyn skalarny
                    for (int n = 0; n < tr_data.getFirst().getData_num().length; n++) {
                        wyjscie += wagi[n] * wejscie[n];
                    }

                    //Ewentualna modyfikacja wektora wag
                    y = wyjscie >= 0 ? 1 : 0;
                    d = tr_data.get(i).getDecision().equals("Iris-setosa") ? 1 : 0;

                    if (y != d) {
                        double[] wagi_prim = new double[wagi.length];

                        for (int k = 0; k < wagi.length; k++) {
                            wagi_prim[k] = wagi[k] + (d - y) * alfa * wejscie[k];
                        }

//                        for (int k = 0; k < wagi.length; k++) {
//                            System.out.print(wagi_prim[k] + " ");
//                        }
                        wagi = wagi_prim;
                        incorrectAtFirst=true;

                    }else if(!incorrectAtFirst){
                        skutecznosc++;
                        trained=true;
                    }else{
                        trained=true;
                        incorrectAtFirst = false;
                    }
                }
            }
            checkloop++;
            System.out.println(checkloop);
        }
    }
}
