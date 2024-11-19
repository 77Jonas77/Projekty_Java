package Ex06_01;

import java.util.Iterator;

public class Roll implements Iterable<Integer>{
    public static int pubiterator = 0;
    private int pviterator;
    public Roll() {
        pubiterator++;
        this.pviterator = pubiterator;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int l1=0,l2=0,l3=0;
            @Override
            public boolean hasNext() {
                if(l1==0 || l2==0 || l3 == 0)
                    return true;
                return l1+l2+l3!=11;
            }

            @Override
            public Integer next() {
                l3 = l2;
                l2 = l1;
                l1 = (int)((Math.random()*6)+1);
                return l1;
            }
        };
    }
}

class RollDice {
    public static void main(String[] args) {
        for (int turn = 0; turn < 10; ++turn) {
            for (Integer i : new Roll())
                System.out.print(i + " ");
            System.out.println();
        }
    }
}
