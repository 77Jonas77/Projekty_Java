import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        File file = new File("binary.bin");

        if (!file.exists()) {
            new BinaryFile(200, 200).createFile();
        }
        new MyFrame();
    }

}

class MyFrame extends Frame {
    public MyFrame() {
        super("Project1");
        this.setSize(200, 200);
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                repaint();
            }
        });
        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                }
        );

    }

    boolean ifPrime(long l) {
        if (l < 2)
            return false;
        if (l == 2)
            return true;
        if (l % 2 == 0)
            return false;

        for (int i = 3; i < l; i += 2) {
            if (l % i == 0)
                return false;
        }
        return true;
    }


    public void paint(Graphics g) {
        super.paint(g);

        //wyznaczenie srodka
        int middleY = getHeight() / 2;
        int middleX = getWidth() / 2;

        g.setColor(Color.black);

        long valueCounter = 2;
        //wyznaczanie wspolrzednych rogow
        g.setColor(Color.red);
        int toprightx = middleX + 1;
        int toprighty = middleY - 1;

        int topleftx = middleX - 1;
        int toplefty = middleY - 1;

        int bottomleftx = middleX - 1;
        int bottomlefty = middleY + 1;

        int bottomrightx = middleX + 1;
        int bottomrighty = middleY;

        //x, y startowe
        int y = middleY;
        int x = middleX;

        //wektory poruszania sie spirali
        int currDir = 0;
        int[][] dir = {
                {1, 0}, //right
                {0, -1}, //up
                {-1, 0}, //left
                {0, 1} //down
        };
        //pierwszy ruch
        x += dir[currDir][0];
        y += dir[currDir][1];

        //zaznaczenie srodka
        g.setColor(Color.blue);
        g.drawLine(middleX, middleY, middleX, middleY);

        g.setColor(Color.black);

        try {
            FileInputStream fis = new FileInputStream("binary.bin");

            //wczytanie pierwszych danych z pliku i okreslenie warunku liczby bajtow
            int bytes = 1;
            int size = (int) BinaryFile.readFromFile(fis, 8);
            long number = BinaryFile.readFromFile(fis, bytes);
            int counter = 1;

            //rysowanie
            while (valueCounter <= (getWidth() * getHeight())) {
                //zmiana rogow
                if (x == toprightx && y == toprighty) {
                    currDir++;
                    toprightx += 1;
                    toprighty -= 1;
                } else if (x == topleftx && y == toplefty) {
                    currDir++;
                    topleftx -= 1;
                    toplefty -= 1;
                } else if (x == bottomleftx && y == bottomlefty) {
                    currDir++;
                    bottomleftx -= 1;
                    bottomlefty += 1;
                } else if (x == bottomrightx && y == bottomrighty) {
                    currDir++;
                    bottomrightx += 1;
                    bottomrighty += 1;
                }

                //wyrysowywanie piksela
                if (valueCounter == number && counter < size) {
                    g.drawLine(x, y, x, y);
                    number = BinaryFile.readFromFile(fis, bytes);
                    counter++;
                } else if (valueCounter == number && counter >= size) {
                    BinaryFile.readFromFile(fis, 1);
                    g.drawLine(x, y, x, y);
                    ++bytes;
                    size = (int) BinaryFile.readFromFile(fis, 8);
                    number = BinaryFile.readFromFile(fis, bytes);
                    counter = 1;

                }
                //poruszanie sie spirali
                x += dir[currDir % 4][0];
                y += dir[currDir % 4][1];

                //liczba w spirali
                valueCounter++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class BinaryFile {
    private long limit;

    public BinaryFile(long width, long height) {
        this.limit = width * height * 100;
    }

    public void createFile() {
        try {
            FileOutputStream fos = new FileOutputStream("binary.bin");
            for (int i = 1; i <= 8; i++) {
                int counter = 0;
                MyList primes = new MyList();
                for (int j = 0; j < limit; j++) {
                    if (countBytes(j) == i && ifPrime(j)) {
                        primes.addAtEnd(j);
                        counter++;
                    }
                }
                //licznik cyfr na ilu bajtach
                if (counter != 0)
                    saveIntoFile(fos, 8, counter);

                for (int k = 0; k < counter; k++) {
                    saveIntoFile(fos, i, primes.getNumber());
                }
                saveIntoFile(fos, 1, '\n');
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int countBytes(long x) {
        return (int) (Math.log(x) / Math.log(256)) + 1;
    }

    boolean ifPrime(long l) {
        if (l < 2)
            return false;
        if (l == 2)
            return true;
        if (l % 2 == 0)
            return false;
        for (int i = 3; i < l; i += 2) {
            if (l % i == 0)
                return false;
        }
        return true;
    }

    public static void saveIntoFile(FileOutputStream fos, int b, long number) {
        try {
            for (int i = 0; i < b; i++)
                fos.write((int) (number >> 8 * i));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long readFromFile(FileInputStream fis, int b) {
        long number = 0;
        try {
            for (int i = 0; i < b; i++) {
                number |= ((long)fis.read() << 8 * i);
                //number=((long) fis.read() << 8*i)|number
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return number;
    }
}

class MyList {
    private Element head;

    public void addAtEnd(long nr) {
        if (this.head == null) {
            this.head = new Element(nr);
        } else {
            Element tmp = this.head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = new Element(nr);
        }
    }

    public long getNumber() {
        Element tmp = head;
        head = tmp.next;
        return tmp.number;
    }
}

class Element {
    long number;
    Element next;
    public Element(long number) {
        this.number = number;
        this.next = null;
    }
}

