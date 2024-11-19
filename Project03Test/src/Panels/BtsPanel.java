package Panels;

import javax.swing.*;
import java.awt.*;

public abstract class BtsPanel extends MyDevicePanel{
    private int incoming;
    private int sent;
    private JLabel processedLabel;
    private JLabel pendingLabel;
//get /set refresh
    public BtsPanel() {
        super();
        sent=0;
        incoming = 0;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel stationLabel = new JLabel("Stacja " + this.ID);
        add(stationLabel);

        processedLabel = new JLabel("Przetworzone: " + sent);
        add(processedLabel);

        pendingLabel = new JLabel("Oczekujące: " + incoming);
        add(pendingLabel);

        //separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        add(separator);
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
        this.pendingLabel.setText("Oczekujące: " + incoming);
    }

    public int getIncoming() {
        return incoming;
    }

    public void setIncoming(int incoming) {
        this.incoming = incoming;
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public void setProcessedLabel(JLabel processedLabel) {
        this.processedLabel = processedLabel;
    }

    public void setPendingLabel(JLabel pendingLabel) {
        this.pendingLabel = pendingLabel;
    }
}

