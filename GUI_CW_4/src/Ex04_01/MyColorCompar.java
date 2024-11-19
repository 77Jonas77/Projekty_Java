package Ex04_01;

import java.util.Comparator;

public class MyColorCompar implements Comparator<MyColor> {

    private ColComponent colComponent;

    public MyColorCompar(ColComponent colComponent) {
        this.colComponent = colComponent;
    }

    @Override
    public int compare(MyColor o1, MyColor o2) {
        return switch (this.colComponent) {
            case RED -> o1.getRed()-o2.getRed();
            case GREEN -> o1.getGreen()-o2.getGreen();
            case BLUE -> o1.getBlue()-o2.getBlue();
            case NONE -> 0;
        };
    }
}

//numerical recepies