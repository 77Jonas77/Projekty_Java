package Panels;

import javax.swing.*;

public abstract class MyDevicePanel extends JPanel {
    protected int ID;
    protected static int stationIndex=1;
    public MyDevicePanel() {
        this.ID=stationIndex++;
    }

    public int getID() {
        return ID;
    }
}
