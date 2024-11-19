package w01.ex01;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public
class Main
        extends Frame {

    public static void main(String[] args) {
        new Main();
    }

    private WindowAdapter myWindowAdapter;

    public Main(){
        /*this.myWindowAdapter = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //...
            }
        };
        this.addWindowListener(this.myWindowAdapter);
        */

        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                }
        );

        this.setSize( 640, 480);
        this.setVisible(true);
    }

    public void myShow(){
        System.out.println("tu");
    }
}
