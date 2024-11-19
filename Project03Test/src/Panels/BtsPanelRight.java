package Panels;

import GUI_Sections.RightPanel;
import Layers.BscColumnLayer;
import Threads.BtsRightThread;

import javax.swing.*;
import java.awt.*;

public class BtsPanelRight extends BtsPanel {

    private int processedLabel;
    private int pendingLabel;
    private BtsRightThread BtsRightThread;
    //konstruktor
    public BtsPanelRight(RightPanel nextLayer) {
        super();
        BtsRightThread = new BtsRightThread(nextLayer, this);
        BtsRightThread.start();
    }

    public BtsRightThread getBtsRightThread() {
        return BtsRightThread;
    }

}
