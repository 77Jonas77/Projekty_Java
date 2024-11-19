package Panels;

import Interfeces.SendToNextInterface;
import Layers.BscColumnLayer;
import Layers.Layer;
import Threads.BtsLeftThread;

import javax.swing.*;
import java.awt.*;

public class BtsPanelLeft extends BtsPanel {

    private int processedLabel;
    private int pendingLabel;
    private BtsLeftThread btsLeftThread;
    //konstruktor
    public BtsPanelLeft(BscColumnLayer nextLayer) {
        super();
        btsLeftThread = new BtsLeftThread(nextLayer, this);
        btsLeftThread.start();
    }

    public BtsLeftThread getBtsLeftThread() {
        return btsLeftThread;
    }
}
