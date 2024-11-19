package task05_02;

import java.util.Comparator;
import java.util.function.IntBinaryOperator;

public enum SortType
        implements Comparator<Integer> {
    BY_VAL((o1,o2) -> o1 - o2),

    BY_VAL_REV((o1,o2) -> o2 - o1),

    BY_NUM_OF_DIVS((o1,o2)-> {
        Integer res = sumOfDigs(o1) - sumOfDigs(o2);
        return (res=countDivs(o1)-countDivs(o2)!=0?res:o1 - o2);
    }),

    BY_SUM_OF_DIGS ((o1,o2)-> {
        Integer res = sumOfDigs(o1) - sumOfDigs(o2);
        return (res!= 0?res : o1 - o2);
    });

    public final IntBinaryOperator op;
    SortType(IntBinaryOperator op) {
        this.op = op;
    }

    private static int sumOfDigs(Integer o) {
        int sum = 0;
        while (o > 0) {
            sum += o % 10;
            o /= 10;
        }
        return sum;
    }

    private static int countDivs(Integer o) {
        if(o==0) throw new IllegalArgumentException();
        int count = 0;
        for (int i = 1; i < o; i++) {
            if (o % i == 0)
                count++;
        }
        return count;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return op.applyAsInt(o1,o2);
    }

}
