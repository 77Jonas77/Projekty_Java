package Zad22_8;

public class ConsoleGraphics {
    char[][] perspective;
    int xo;
    int yo;
    int[][] z_pos;

    public ConsoleGraphics() {
        this.perspective = new char[17][41];
        for (int i = 0; i < perspective.length; i++) {
            for (int j = 0; j < perspective[i].length; j++) {
                perspective[i][j] = ' ';
            }
        }
    }

    double[] calculateEquation(double[] loc1, double[] loc2) {
        double a, b;
        a = (loc1[1] - loc2[1]) / (loc1[0] - loc2[0]);
        b = loc1[1] - a * loc1[0];


        double[] equation = new double[2];
        equation[0] = a;
        equation[1] = b;

        return equation;
    }

    double[][] frame = {
            {41, 17},
            {41, 0},
            {0, 17},
            {0, 0}
    };

    public void draw(Maze lab, int[][] rys) {

        boolean[] view = new boolean[9];
        for (int i = 0; i < 9; i++)
            view[i] = false;

        if (lab.getPos()[1] - 2 >= 0 && lab.getPos()[0] - 1 >= 0 && rys[lab.getPos()[1] - 2][lab.getPos()[0] - 1] == 1) {
            view[0] = true;
        }
        if (lab.getPos()[1] - 2 >= 0 && lab.getPos()[0] >= 0 && rys[lab.getPos()[1] - 2][lab.getPos()[0]] == 1) {
            view[1] = true;
        }
        if (lab.getPos()[1] - 2 >= 0 && lab.getPos()[0] + 1 >= 0 && rys[lab.getPos()[1] - 2][lab.getPos()[0] + 1] == 1) {
            view[2] = true;
        }
        if (lab.getPos()[1] - 1 >= 0 && lab.getPos()[0] - 1 >= 0 && rys[lab.getPos()[1] - 1][lab.getPos()[0] - 1] == 1) {
            view[3] = true;
        }
        if (lab.getPos()[1] - 1 >= 0 && lab.getPos()[0] >= 0 && rys[lab.getPos()[1] - 1][lab.getPos()[0]] == 1) {
            view[4] = true;
        }
        if (lab.getPos()[1] - 1 >= 0 && lab.getPos()[0] + 1 >= 0 && rys[lab.getPos()[1] - 1][lab.getPos()[0] + 1] == 1) {
            view[5] = true;
        }
        if (lab.getPos()[1] >= 0 && lab.getPos()[0] - 1 >= 0 && rys[lab.getPos()[1]][lab.getPos()[0] - 1] == 1) {
            view[6] = true;
        }
        if (lab.getPos()[1] >= 0 && lab.getPos()[0] + 1 >= 0 && rys[lab.getPos()[1]][lab.getPos()[0] + 1] == 1) {
            view[7] = true;
        }
        if (lab.getPos()[1] - 3 >= 0 && lab.getPos()[0] >= 0 && rys[lab.getPos()[1] - 3][lab.getPos()[0]] == 1) {
            view[8] = true;
        }

        double[] p1 = calculateEquation(new double[]{0, 0}, new double[]{20, -8});
        double[] p2 = calculateEquation(new double[]{20, -8}, new double[]{40, 0});

        if(view[4]){
            for(int i=4;i<13;i++){
                for(int j=9;j<32;j++){
                    perspective[i][j] = '-';
                }
            }
        }
        if(view[8]){
            for(int i=7;i<10;i++){
                for(int j=20;j<27;j++){
                    perspective[i][j] = '-';
                }
            }
        }
        if(view[1] && !view[4]){
            for(int i=6;i<11;i++){
                for(int j=14;j<29;j++){
                    perspective[i][j] = '-';
                }
            }
        }
        if(view[6]) {
            for (int i = 0; i < perspective.length; i++) {
                for (int j = 0; j < perspective[i].length; j++) {
                    if (-i <= (p1[0] * j+ p1[1]) && -i >= p2[0] * j + p2[1] && j < 9) {
                        perspective[i][j] = '*';
                    }
                }
            }
        }

        if(view[7]){
            for (int i = 0; i < perspective.length; i++) {
                for (int j = 0; j < perspective[i].length; j++) {
                    if (i <= p1[0] * j * -1 + p1[1] && -i <= p2[0] * j + p2[1] && j>=32) {
                        perspective[i][j] = '*';
                    }
                }
            }
        }
        if(view[5] && !view[4]){
            for (int i = 0; i < perspective.length; i++) {
                for (int j = 0; j < perspective[i].length; j++) {
                    if (i <= p1[0] * j * -1 + p1[1] && -i <= p2[0] * j + p2[1] && j<32 && j>=27) {
                        perspective[i][j] = '|';
                    }
                }
            }
        }
        if(view[3] && !view[4]) {
            for (int i = 0; i < perspective.length; i++) {
                for (int j = 0; j < perspective[i].length; j++) {
                    if (i >= (p1[0] * j + p1[1]) * -1 && -i >= p2[0] * j + p2[1] && j >= 9 && j <= 13) {
                        perspective[i][j] = '|';
                    }
                }
            }
        }
        if(view[2] && !view[4] && !view[1]){
            for (int i = 0; i < perspective.length; i++) {
                for (int j = 0; j < perspective[i].length; j++) {
                    if (i <= p1[0] * j * -1 + p1[1] && -i <= p2[0] * j + p2[1] && j<=26 && j>=24) {
                        perspective[i][j] = '.';
                    }
                }
            }
        }
        if(view[0] && !view[4] && !view[1]) {
            for (int i = 0; i < perspective.length; i++) {
                for (int j = 0; j < perspective[i].length; j++) {
                    if (i >= (p1[0] * j + p1[1]) * -1 && -i >= p2[0] * j + p2[1] && j >= 14 && j <= 16) {
                        perspective[i][j] = '.';
                    }
                }
            }
        }
    }

    public void show() {
        for (int i = 0; i < perspective.length; i++) {
            for (int j = 0; j < perspective[i].length; j++) {
                System.out.print(perspective[i][j]);
            }
            System.out.println();
        }
    }

}

