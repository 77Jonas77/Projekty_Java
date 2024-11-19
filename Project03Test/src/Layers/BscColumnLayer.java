package Layers;

import Interfeces.SendToNextInterface;
import Panels.BscPanel;
import Threads.BscThread;
import Threads.BtsLeftThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static GUI_Sections.MiddlePanel.nextLayerForBsc;

public class BscColumnLayer extends Layer implements SendToNextInterface {
    protected ArrayList<BscThread> bcsChoosePanel;

    private SendToNextInterface nextLayer;
    private Thread additionCheckThread;
    private boolean additionCheckThreadWorking;

    private JPanel bscContainerColumn;
    private JScrollPane scrollPane;
    public BscColumnLayer(SendToNextInterface nextLayer) {
        this.bcsChoosePanel = new ArrayList<BscThread>();
        this.nextLayer=nextLayer;
        bscContainerColumn = new JPanel();
        bscContainerColumn.setLayout(new BoxLayout(bscContainerColumn, BoxLayout.Y_AXIS));

        JPanel bscContainer = new JPanel(); // Panel kontenera dla BscPanel
        bscContainer.setLayout(new BoxLayout(bscContainer, BoxLayout.Y_AXIS)); // Ustawienie układu pionowego
        BscPanel bscPanel = new BscPanel(this.nextLayer);
        addTobcsChoosePanel(bscPanel.getBscThread());

        // Utwórz kolumnę JScrollPane
        scrollPane = new JScrollPane(bscContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        bscContainer.add(bscPanel);

        // Ustaw preferowane rozmiary dla scrollPane i bscContainer
        Dimension preferredSize = new Dimension(bscPanel.getPreferredSize().width+30, bscPanel.getPreferredSize().height);
        scrollPane.setPreferredSize(preferredSize);
        bscContainer.setPreferredSize(new Dimension(preferredSize.width, bscContainer.getPreferredSize().height));

        nextLayerForBsc = this;
        bscContainerColumn.add(scrollPane);
        add(bscContainerColumn);

        additionCheckThreadWorking = true;
        additionCheckThread = new Thread(() -> {
            while (additionCheckThreadWorking) {
                if (checkIfAddPanel())
                    addNewBscPanel();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        additionCheckThread.start();
    }

    private void addNewBscPanel(){
        BscPanel bscPanel = new BscPanel(this.nextLayer);
        addTobcsChoosePanel(bscPanel.getBscThread());
        add(bscPanel);

        scrollPane = new JScrollPane(bscPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Ustaw preferowane rozmiary dla scrollPane i bscContainer
        Dimension preferredSize = new Dimension(bscPanel.getPreferredSize().width+30, bscPanel.getPreferredSize().height);
        scrollPane.setPreferredSize(preferredSize);

        bscContainerColumn.add(scrollPane);
        revalidate();
        repaint();
    }

    private boolean checkIfAddPanel() {
        for(BscThread bscThread : bcsChoosePanel){
            if(bscThread.getIncomingMsg()<=5)
                return false;
        }
        return true;
    }

    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        BscThread next = bcsChoosePanel.get(0);
        for (BscThread bcsPanel : bcsChoosePanel) {
            if (next.getIncomingMsg() > bcsPanel.getIncomingMsg())
                next = bcsPanel;
        }
        next.receiveMsg(pdu);
    }
    protected void addTobcsChoosePanel(BscThread bcs){
        this.bcsChoosePanel.add(bcs);
    }
    public ArrayList<BscThread> getBcsChoosePanel() {
        return bcsChoosePanel;
    }

    public SendToNextInterface getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(SendToNextInterface nextLayer) {
        this.nextLayer = nextLayer;
    }

}
