package Ex04_01;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MyColor c1 = new MyColor(1,2,3);
        MyColor c2 = new MyColor(255,0,0);
        MyColor c3 = new MyColor(10,255,10);

        ArrayList <MyColor> ColorList = new ArrayList<MyColor>();

        ColorList.add(c1);
        ColorList.add(c2);
        ColorList.add(c3);

        Collections.sort(ColorList, new MyColorCompar(ColComponent.BLUE));

        System.out.println(ColorList);
        System.out.println("===========");

        Collections.sort(ColorList);

        System.out.println(ColorList);
    }
}
