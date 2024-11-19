package Layers;

import Interfeces.SendToNextInterface;
import Panels.BtsPanelLeft;
import Threads.BtsLeftThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BtsLayerLeft extends Layer implements SendToNextInterface {
    private JPanel BtsLayerJPanel;
    protected ArrayList<BtsLeftThread> btsChoosePanel;
    private BscColumnLayer bscColumnLayer;
    private boolean additionCheckThreadWorking;

    private JScrollPane scrollPane;
    public BtsLayerLeft(BscColumnLayer bscColumnLayer) {
        this.BtsLayerJPanel = new JPanel();
        btsChoosePanel = new ArrayList<>();
        this.bscColumnLayer = bscColumnLayer;
        this.BtsLayerJPanel.setLayout(new BoxLayout(BtsLayerJPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(BtsLayerJPanel); // Tworzenie JScrollPane z btsLayerContent
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ustawienie zawsze widocznej poziomej belki przewijania
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLUE);
        separator.setForeground(Color.BLUE);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(separator, BorderLayout.EAST); // Dodanie separatora po prawej stronie JPanel

        addNewBtsColumn();

        additionCheckThreadWorking = true;
        Thread additionCheckThread = new Thread(() -> {
            while (additionCheckThreadWorking) {
                if (checkIfAddPanel())
                    addNewBtsLeftPanel();
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
        for(BtsLeftThread btsLeftThread : btsChoosePanel){
            if(btsLeftThread.getIncomingMsg()<=5)
                return false;
        }
        return true;
    }
    private void addNewBtsColumn() {
        JPanel btsContainer = new JPanel(); // Panel kontenera dla BscPanel
        btsContainer.setLayout(new BoxLayout(btsContainer, BoxLayout.Y_AXIS)); // Ustawienie układu pionowego

        BtsPanelLeft btsPanel = new BtsPanelLeft(this.bscColumnLayer);
        addTobtsChoosePanel(btsPanel.getBtsLeftThread());

        btsContainer.add(btsPanel);

        this.BtsLayerJPanel.add(btsContainer);
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();

    }
    private void addNewBtsLeftPanel(){
        BtsPanelLeft btsPanel = new BtsPanelLeft(this.bscColumnLayer);
        addTobtsChoosePanel(btsPanel.getBtsLeftThread());
        BtsLayerJPanel.add(btsPanel);
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();
    }
    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        BtsLeftThread next = btsChoosePanel.get(0);
        for (BtsLeftThread btsPanel : btsChoosePanel) {
            if (next.getIncomingMsg() > btsPanel.getIncomingMsg())
                next = btsPanel;
        }
        next.receiveMsg(pdu);
    }

    public ArrayList<BtsLeftThread> getBtsChoosePanel() {
        return btsChoosePanel;
    }

    protected void addTobtsChoosePanel(BtsLeftThread bts){
        this.btsChoosePanel.add(bts);
    }

    public BscColumnLayer getBscColumnLayer() {
        return bscColumnLayer;
    }

    public void setBscColumnLayer(BscColumnLayer bscColumnLayer) {
        this.bscColumnLayer = bscColumnLayer;
    }

    public boolean isAdditionCheckThreadWorking() {
        return additionCheckThreadWorking;
    }

    public void setAdditionCheckThreadWorking(boolean additionCheckThreadWorking) {
        this.additionCheckThreadWorking = additionCheckThreadWorking;
    }
}
