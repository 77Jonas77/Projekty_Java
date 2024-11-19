import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public
abstract class TransmissionStationPanel
        extends JPanel
        implements LayerCommunicator {

    protected final JPanel myViewport;
    protected final List<TransmissionStation> transmissionStationList;
    protected final Thread additionChecker;
    protected boolean additionCheckerWorking;
    protected LayerCommunicator nextLayer;

    public TransmissionStationPanel() {
        myViewport = new JPanel();
        myViewport.setLayout(new GridLayout(0, 1));

        transmissionStationList = new ArrayList<>();

        additionCheckerWorking = true;
        additionChecker = new Thread(() -> {
            while (additionCheckerWorking) {
                if(checkIsAdditionNeeded())
                    addTransmissionStation();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        additionChecker.start();
    }



    public void setAdditionCheckerWorking(boolean additionCheckerWorking) {
        this.additionCheckerWorking = additionCheckerWorking;
    }

    public abstract void addTransmissionStation();

    public void refreshPanel() {
        myViewport.revalidate();
        myViewport.repaint();
        this.revalidate();
        this.repaint();
    }

    private boolean checkIsAdditionNeeded() {
        if (transmissionStationList.isEmpty())
            return false;
        for (TransmissionStation transmissionStation : transmissionStationList) {
            if (transmissionStation.getWaitingSMSCounter() <= 5)
                return false;
        }
        return true;
    }

    @Override
    public synchronized void passMessage(byte[] pdu) {
        TransmissionStation next = transmissionStationList.get(0);
        for (TransmissionStation transmissionStation : transmissionStationList) {
            if (next.getWaitingSMSCounter() > transmissionStation.getWaitingSMSCounter())
                next = transmissionStation;
        }
        next.receiveMessage(pdu);
    }
}
