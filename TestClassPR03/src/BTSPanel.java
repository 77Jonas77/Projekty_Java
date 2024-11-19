
import javax.swing.*;
import java.awt.*;

public
class BTSPanel
        extends TransmissionStationPanel {

    private final JScrollPane btsScrollPane;

    public BTSPanel(LayerCommunicator nextLayer) {
        super();
        setLayout(new BorderLayout());

        this.nextLayer = nextLayer;

        btsScrollPane = new JScrollPane(myViewport);
        btsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(btsScrollPane, BorderLayout.CENTER);

        BTS startingBTS = new BTS("BTS ", this.nextLayer);
        transmissionStationList.add(startingBTS);
        myViewport.add(startingBTS.getVisualRepresentation());
        startingBTS.start();
    }

    @Override
    public void addTransmissionStation() {
        BTS bts = new BTS("BTS ", this.nextLayer);
        bts.start();
        transmissionStationList.add(bts);
        myViewport.add(bts.getVisualRepresentation());
        refreshPanel();
    }

    @Override
    public void refreshPanel() {
        super.refreshPanel();
        btsScrollPane.revalidate();
        btsScrollPane.repaint();
    }
}
