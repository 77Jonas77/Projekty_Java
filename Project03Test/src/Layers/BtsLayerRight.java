package Layers;

import GUI_Sections.LeftPanel;
import GUI_Sections.RightPanel;
import Interfeces.SendToNextInterface;
import Panels.BscPanel;
import Panels.BtsPanelLeft;
import Panels.BtsPanelRight;
import Panels.VRDPanel;
import Threads.BtsLeftThread;
import Threads.BtsRightThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BtsLayerRight extends Layer implements SendToNextInterface {
    private JPanel BtsLayerJPanel;

    protected ArrayList<BtsRightThread> btsChoosePanel;

    private RightPanel RightPanelLayer;

    private boolean additionCheckThreadWorking;

    public BtsLayerRight(RightPanel RightPanelLayer) {
        this.BtsLayerJPanel = new JPanel();
        this.BtsLayerJPanel.setLayout(new BoxLayout(BtsLayerJPanel, BoxLayout.Y_AXIS));
        this.btsChoosePanel = new ArrayList<>();
        this.RightPanelLayer = RightPanelLayer;

        JScrollPane scrollPane = new JScrollPane(BtsLayerJPanel); // Tworzenie JScrollPane z btsLayerContent
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ustawienie zawsze widocznej poziomej belki przewijania
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLUE);
        separator.setForeground(Color.BLUE);

        setLayout(new BorderLayout());
        add(separator, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        addNewBtsColumn();

        additionCheckThreadWorking = true;
        Thread additionCheckThread = new Thread(() -> {
            while (additionCheckThreadWorking) {
                if (checkIfAddPanel())
                    addNewBtsRightPanel();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        additionCheckThread.start();
    }

    private synchronized boolean checkIfAddPanel() {
        for(BtsRightThread btsRightThread : btsChoosePanel){
            if(btsRightThread.getIncomingMsg()<=5)
                return false;
        }
        return true;
    }

    private synchronized void addNewBtsColumn() {
        JPanel btsContainer = new JPanel(); // Panel kontenera dla BscPanel
        btsContainer.setLayout(new BoxLayout(btsContainer, BoxLayout.Y_AXIS)); // Ustawienie układu pionowego

        BtsPanelRight btsPanel = new BtsPanelRight(RightPanelLayer);

        btsContainer.add(btsPanel);

        this.BtsLayerJPanel.add(btsContainer);
        this.btsChoosePanel.add(btsPanel.getBtsRightThread());
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();

    }

    private void addNewBtsRightPanel(){
        BtsPanelRight btsPanel = new BtsPanelRight(RightPanelLayer);

        this.btsChoosePanel.add(btsPanel.getBtsRightThread());
        BtsLayerJPanel.add(btsPanel);
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();
    }
    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        BtsRightThread next = btsChoosePanel.get(0);
        for (BtsRightThread btsPanel : btsChoosePanel) {
            if (next.getIncomingMsg() > btsPanel.getIncomingMsg())
                next = btsPanel;
        }
        next.receiveMsg(pdu);
    }

    public RightPanel getRightPanelLayer() {
        return RightPanelLayer;
    }

    public void setRightPanelLayer(RightPanel rightPanelLayer) {
        RightPanelLayer = rightPanelLayer;
    }
}
