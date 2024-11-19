import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public
abstract class TransmissionStation
        extends Thread {

    public static int stationNumber = 0;

    protected boolean running;
    protected int number;
    protected int sentSMS;
    protected Queue<byte[]> waitingSMSes;
    protected final JPanel visualRepresentation;
    protected byte[] pdu;
    protected LayerCommunicator nextLayer;

    protected JLabel sentSMSLabel;
    protected JLabel waitingSMSLabel;

    public TransmissionStation(String name, LayerCommunicator nextLayer) {
        super(name + (stationNumber + 1));

        this.nextLayer = nextLayer;

        waitingSMSes = new LinkedList<>();
        this.number = incrementStationNumber();
        sentSMS = 0;

        running = true;

        visualRepresentation = new JPanel();
        visualRepresentation.setLayout(new BoxLayout(visualRepresentation, BoxLayout.Y_AXIS));
        JLabel numberLabel = new JLabel("Num: " + this.number);
        this.sentSMSLabel = new JLabel("Sent: " + this.sentSMS);
        this.waitingSMSLabel = new JLabel("Waiting: " + this.waitingSMSes.size());
        visualRepresentation.add(numberLabel);
        visualRepresentation.add(sentSMSLabel);
        visualRepresentation.add(waitingSMSLabel);
        visualRepresentation.setBorder(new LineBorder(Color.BLACK, 1));
    }

    public synchronized int incrementStationNumber() {
        stationNumber++;
        return stationNumber;
    }

    public void setNextLayer(LayerCommunicator nextLayer) {
        this.nextLayer = nextLayer;
    }

    public JPanel getVisualRepresentation() {
        return visualRepresentation;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public synchronized void addWaitingSMS(byte[] pdu) {
        this.waitingSMSes.add(pdu);
        this.waitingSMSLabel.setText("Waiting: " + waitingSMSes.size());
    }

    public synchronized void pollWaitingSMS() {
        this.waitingSMSLabel.setText("Waiting: " + (waitingSMSes.size() - 1));
        this.pdu = waitingSMSes.poll();
    }

    public synchronized void incrementSentSMSCounter() {
        this.sentSMS++;
        this.sentSMSLabel.setText("Sent: " + sentSMS);
    }

    public synchronized int getWaitingSMSCounter() {
        return waitingSMSes.size();
    }

    @Override
    public String toString() {
        return "TransmissionStation{" +
                "number=" + number +
                '}';
    }

    public void destroy() {
        this.running = false;
    }

    public synchronized void receiveMessage(byte[] pdu) {
        System.out.println(getName() + " dostałem " + Arrays.toString(pdu));
        addWaitingSMS(pdu);
    }

    protected synchronized void endMessageKeeping() throws InterruptedException {
        System.out.println(getName() + " pozbywam się " + Arrays.toString(pdu));
        this.pdu = null;
        incrementSentSMSCounter();
    }
}
