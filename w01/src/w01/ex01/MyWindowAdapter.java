package w01.ex01;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public
class MyWindowAdapter
        extends WindowAdapter {

    private Main myMain;

    public MyWindowAdapter(Main myMain) {
        this.myMain = myMain;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        myMain.myShow();
        System.exit(0);
    }
}
