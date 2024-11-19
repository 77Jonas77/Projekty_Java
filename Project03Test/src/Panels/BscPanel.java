package Panels;

import Interfeces.SendToNextInterface;
import Threads.BscThread;
import Threads.BtsLeftThread;

import javax.swing.*;
import java.awt.*;

public class BscPanel extends MyDevicePanel {

    private int incoming;
    private int sent;
    private SendToNextInterface nextLayer;
    private BscThread bscThread;
    private JLabel processedLabel;
    private JLabel pendingLabel;
    public BscPanel(SendToNextInterface nextLayer) {
        super();
        this.sent=0;
        this.incoming=0;
        this.nextLayer=nextLayer;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel stationLabel = new JLabel("Stacja " + this.ID);
        add(stationLabel);

        processedLabel = new JLabel("Przetworzone: " + sent);
        add(processedLabel);

        pendingLabel = new JLabel("Oczekujące: " + incoming);
        add(pendingLabel);

        //separator
        add(Box.createRigidArea(new Dimension(0, 5)));

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        add(separator);

        bscThread = new BscThread(this.nextLayer, this);
        bscThread.start();
    }

    public synchronized void increaseSent(){
        this.sent++;
        this.processedLabel.setText("Przetworzone: " + sent);
    }

    public synchronized void increaseIncoming(){
        this.incoming++;
        this.pendingLabel.setText("Oczekujące: " + incoming);
    }

    public synchronized void decreaseIncoming(){
        this.incoming--;
        this.pendingLabel.setText("Oczekujące:  " + incoming);
    }

    public BscThread getBscThread() {
        return bscThread;
    }

    public SendToNextInterface getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(SendToNextInterface nextLayer) {
        this.nextLayer = nextLayer;
    }
}