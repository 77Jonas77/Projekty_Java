package zad_2;

import zad_2.ConcatRev;
import zad_2.Separ;
import zad_2.TwoStringsOper;

public
class InterS {

    public static void main(String[] args) {
        TwoStringsOper[] a = {
                new Concat(), new ConcatRev(),
                new Initials(), new Separ(" loves ")
        };
        for (TwoStringsOper op : a) {
            System.out.println(op.apply("Mary", "John"));
        }
    }
}

class Concat implements TwoStringsOper {
    @Override
    public String apply(String ciag1, String ciag2) {
        return ciag1 + ciag2;
    }

}

class ConcatRev implements TwoStringsOper {
    @Override
    public String apply(String ciag1, String ciag2) {
        return ciag2 + ciag1;
    }
}


class Initials implements TwoStringsOper {
    @Override
    public String apply(String ciag1, String ciag2) {
        return "" + ciag1.charAt(0) + ciag2.charAt(0);
    }

}

class Separ implements TwoStringsOper {
    private String between;

    public Separ(String between) {
        this.between = between;
    }

    @Override
    public String apply(String ciag1, String ciag2) {
        return ciag1 + " " + between + " " + ciag2;
    }
}

interface TwoStringsOper {
    String apply(String ciag1, String ciag2);
}
