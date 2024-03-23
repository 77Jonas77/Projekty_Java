import java.util.Arrays;

public class RowData {

    private double[] data_num;
    private String decision;

    public RowData() {

    }

    public double[] getData_num() {
        return data_num;
    }

    public void setData_num(double[] data_num) {
        this.data_num = data_num;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    @Override
    public String toString() {
        return "RowData{" +
                "data_num=" + Arrays.toString(data_num) +
                ", decision='" + decision + '\'' +
                '}';
    }
}
