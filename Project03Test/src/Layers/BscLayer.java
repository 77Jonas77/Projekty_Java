package Layers;

import Events.BscEvent;
import Interfeces.ButtonAddRemoveBscListener;
import Interfeces.SendToNextInterface;
import Panels.BscPanel;
import Threads.BscThread;
import Threads.BtsLeftThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static GUI_Sections.MiddlePanel.nextLayerForBsc;

public class BscLayer extends JPanel implements ButtonAddRemoveBscListener {

    private JPanel BscLayerJPanel;
    private ArrayList<JScrollPane> BscPanels;
    private SendToNextInterface nextLayer;

    private ArrayList<BscColumnLayer> bscColumns;

    public BscLayer(SendToNextInterface nextLayer) {
        bscColumns = new ArrayList<BscColumnLayer>();
        this.BscPanels = new ArrayList<>();
        this.BscLayerJPanel = new JPanel();
        this.BscLayerJPanel.setLayout(new BoxLayout(BscLayerJPanel, BoxLayout.X_AXIS));
        this.nextLayer = nextLayer;

        JScrollPane scrollPane = new JScrollPane(BscLayerJPanel); // Tworzenie JScrollPane z bscLayerContent
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ustawienie zawsze widocznej poziomej belki przewijania
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        addNewBscColumn();

    }

    @Override
    public void addRemoveBsc(BscEvent evt) {
        if (evt.isAddRemove()) {
            this.addNewBscColumn();
        } else {
            this.removeLastBscPanel();
        }
    }

    private void addNewBscColumn() {
        BscColumnLayer layer = new BscColumnLayer(this.nextLayer);
        JScrollPane jsp = new JScrollPane(layer);

        this.BscLayerJPanel.add(jsp);
        this.BscLayerJPanel.revalidate();
        this.BscLayerJPanel.repaint();

        this.bscColumns.add(layer);

        if(bscColumns.size() > 1) {
            ArrayList<BscThread> bscThreadlist = bscColumns.get(bscColumns.size() - 2).getBcsChoosePanel();
            for (BscThread bscThread : bscThreadlist){
                bscThread.setNextLayer(layer);
                bscThread.getBscPanel().setNextLayer(layer);
            }
            bscColumns.get(bscColumns.size() - 2).setNextLayer(layer);
        }
        this.BscPanels.add(jsp);
    }

    //todo: zrobic dla usuwania przekazywanie
    private void removeLastBscPanel() {
        int lastIndex = BscPanels.size() - 1;
        if (lastIndex > 0) {
            JScrollPane lastPanel = BscPanels.get(lastIndex);
            BscPanels.remove(lastIndex);
            this.BscLayerJPanel.remove(lastPanel);
            this.BscLayerJPanel.revalidate();
            this.BscLayerJPanel.repaint();

            BscColumnLayer lastCol = bscColumns.get(lastIndex);
            BscColumnLayer beforeLastCol = bscColumns.get(lastIndex-1);
            ArrayList<BscThread> lastColThreadsCopy = lastCol.getBcsChoosePanel();
            ArrayList<BscThread> beforelastColThreadsCopy = beforeLastCol.getBcsChoosePanel();

            beforeLastCol.setNextLayer(lastCol.getNextLayer());

            for(BscThread bscThread: beforeLastCol.getBcsChoosePanel()){
                bscThread.setNextLayer(lastCol.getNextLayer());
            }

            bscColumns.remove(lastIndex);

            for(BscThread bscThread :lastColThreadsCopy){
                bscThread.setSendAllMsgNow(true);
            }
            for(BscThread bscThread :lastColThreadsCopy){
                bscThread.setWorking(false);
            }
        }
    }

    public ArrayList<BscColumnLayer> getBscColumns() {
        return bscColumns;
    }

    public void setBscColumns(ArrayList<BscColumnLayer> bscColumns) {
        this.bscColumns = bscColumns;
    }
}
