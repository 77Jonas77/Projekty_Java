package Task24_2;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Task24/Data/plik1");
        PNWReader ex = new PNWReader(file,1);
        ex.show();
    }
}