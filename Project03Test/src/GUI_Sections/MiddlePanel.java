package GUI_Sections;


import Events.BscEvent;
import Interfeces.ButtonAddRemoveBscListener;
import Interfeces.SendToNextInterface;
import Layers.BscLayer;
import Layers.BtsLayerLeft;
import Layers.BtsLayerRight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MiddlePanel extends JPanel {

    private JPanel jpanelCenter;
    private ArrayList<ButtonAddRemoveBscListener> listeners = new ArrayList<>();

    private BtsLayerLeft btsLayerLeft;

    private BtsLayerRight btsLayerRight;
    public static SendToNextInterface nextLayerForBsc;

    private RightPanel VrdLayer;

    //NASLUCHWIANIE DEF: czyli jak button wysyla event poprzez fire, to powiadamy wszystkich nasluchujacych (bscLayerPanel), czyli wywolujemy metode zaimplementowana przez interfejs w celu (np. usuniecia ostatniego panelu z listy)
    public MiddlePanel(RightPanel panelRight) {
        setLayout(new BorderLayout());

        this.VrdLayer=panelRight;

        jpanelCenter = new JPanel();
        jpanelCenter.setLayout(new BoxLayout(jpanelCenter,BoxLayout.X_AXIS));

        btsLayerRight = new BtsLayerRight(VrdLayer);

        nextLayerForBsc = btsLayerRight;

        BscLayer bscLayer = new BscLayer(nextLayerForBsc);
        this.btsLayerLeft = new BtsLayerLeft(bscLayer.getBscColumns().get(0));

        jpanelCenter.add(btsLayerLeft, BorderLayout.WEST);
        jpanelCenter.add(bscLayer, BorderLayout.CENTER);
        jpanelCenter.add(btsLayerRight, BorderLayout.EAST);

        this.addBscListner(bscLayer);

        JScrollPane scrollPane = new JScrollPane(jpanelCenter);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addBscButton = new JButton("Add BSC");
        JButton removeBscButton = new JButton("Remove BSC");

        buttonPanel.add(addBscButton);
        buttonPanel.add(removeBscButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addBscButton.addActionListener(e -> {
            fireAddRemoveBsc(true);
            bscLayer.revalidate();
            bscLayer.repaint();
        });

        removeBscButton.addActionListener(e -> {
            fireAddRemoveBsc(false);
            bscLayer.revalidate();
            bscLayer.repaint();
        });
    }


    public void addBscListner(ButtonAddRemoveBscListener listener){
        this.listeners.add(listener);
    }
    public void removeBscListner(ButtonAddRemoveBscListener listener){
        this.listeners.remove(listener);
    }

    protected void fireAddRemoveBsc(boolean evt){
        BscEvent et = new BscEvent( this, evt);
        for(ButtonAddRemoveBscListener listener : listeners)
            listener.addRemoveBsc(et);
    }

    public JPanel getjScrollPaneCenter() {
        return jpanelCenter;
    }

    public BtsLayerLeft getBtsLayerLeft() {
        return btsLayerLeft;
    }

    public void setBtsLayerLeft(BtsLayerLeft btsLayerLeft) {
        this.btsLayerLeft = btsLayerLeft;
    }

    public SendToNextInterface getNextLayerForBsc() {
        return nextLayerForBsc;
    }

    public void setNextLayerForBsc(SendToNextInterface nextLayerForBsc) {
        this.nextLayerForBsc = nextLayerForBsc;
    }
}
