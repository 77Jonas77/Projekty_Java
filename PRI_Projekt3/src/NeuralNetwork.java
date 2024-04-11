import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<RowData> tr_data;
    private ArrayList<RowData> test_data;
    private ArrayList<String> languages;
    private ArrayList<Perceptron> network;

    public NeuralNetwork(ArrayList<String> languages, ArrayList<RowData> tr_data, ArrayList<RowData> test_data) {
        this.tr_data = tr_data;
        this.test_data = test_data;
        this.languages = languages;
        network = new ArrayList<>();
        for (int i = 0; i < languages.size(); i++) {
            Perceptron perceptron = new Perceptron(tr_data, test_data, languages.get(i));
            network.add(perceptron);
        }
    }

    //training neural network
    public void trainNeuralNetwork() {
        for (Perceptron p : network) {
            p.trainPerceptron();
        }
    }

    //Showing stats for neural network
    public void showStatistics() {
        int correct = 0;

        for (int i = 0; i < test_data.size(); i++) {
            RowData vector = test_data.get(i);
            String dec = "";
            for (Perceptron p : network) {
                String classified = p.categorize(vector);
                if (!classified.equals("NONE")) {
                    dec = classified;
                }
                //System.out.print(classified + " ");
                if (dec.equals(vector.getDecision()))
                    correct++;
            }
//            System.out.println("dec: " + vector.getDecision());
//            System.out.println("========");
        }
        System.out.println("Classified correctly: " + correct + " Effectiveness: " + ((double) correct / test_data.size()) * 100 + "%");
    }


}
