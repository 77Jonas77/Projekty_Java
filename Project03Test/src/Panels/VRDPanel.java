package Panels;

import Events.ButtonEndEvent;
import Events.CheckBoxVRDEvent;
import GUI_Sections.RightPanel;
import Interfeces.ButtonEndListener;
import Interfeces.CheckBoxRemoveEvery10SecListener;
import Threads.VBDThread;
import Threads.VRDThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VRDPanel extends MyDevicePanel {
    private int ID;
    public static int counter=1;

    private ArrayList<ButtonEndListener> listeners;
    private ArrayList<CheckBoxRemoveEvery10SecListener> listenersDelete10;

    private boolean deleteEvery10s;
    private Thread deleteEvery10sThread;
    private int recievedMsgCounter;
    private VRDThread vrdThread;
    private int sent;
    private int incoming;

    private JLabel vrdLabel;
    public VRDPanel() {
        this.sent=0;
        this.incoming=0;
        recievedMsgCounter=0;
        this.ID = counter++;
        this.listeners = new ArrayList<>();
        this.listenersDelete10 = new ArrayList<>();

        JPanel vrdPanel = new JPanel();
        vrdPanel.setLayout(new GridLayout());

        JButton vrdEndButton = new JButton("End");
        vrdLabel = new JLabel("Number of recieved msg: " + incoming); //dodac ilosc dostarczanych sms
        JCheckBox vrdCheckBox = new JCheckBox("Delete every 10 sec");
        vrdCheckBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        vrdPanel.add(vrdEndButton);
        vrdPanel.add(vrdLabel);
        vrdPanel.add(vrdCheckBox);

        vrdThread = new VRDThread(this);

        this.addEndListner(vrdThread);

        deleteEvery10s = false;
        deleteEvery10sThread = new Thread(() ->{
            while(deleteEvery10s){
                System.out.println("Xd");
                this.incoming=0;
                try {
                    Thread.sleep(9999);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        vrdEndButton.addActionListener(e ->{
            fireEnd(true);
            Container parentContainer = vrdPanel.getParent();

            RightPanel.VRDPanelsList.remove(this.getVrdThread());
            RightPanel.reciversList.remove(this);
            VBDThread.reciversList.remove(this);
            deleteEvery10s=false;

            parentContainer.remove(vrdPanel);
            parentContainer.revalidate();
            parentContainer.repaint();
        });

        vrdCheckBox.addActionListener(e -> {
            if(vrdCheckBox.isSelected()){
                this.deleteEvery10s = true;
                deleteEvery10sThread.start();
            }else{
                this.deleteEvery10s=false;
            }
        });

        add(vrdPanel);
    }

    public synchronized void increaserecievedMsgCounter(){
        this.recievedMsgCounter++;
    }

    protected void fireEnd(boolean evt){
        ButtonEndEvent et = new ButtonEndEvent(this, evt);
        for(ButtonEndListener listener : listeners)
            listener.buttonEnd(et);
    }

    protected void fireDeleteEvery10sec(boolean evt){
        CheckBoxVRDEvent et = new CheckBoxVRDEvent(this, evt);
        for(CheckBoxRemoveEvery10SecListener listener : listenersDelete10)
            listener.checkBoxRemoveEvery10Sec(et);
    }
    public void addEndListner(ButtonEndListener listener){
        this.listeners.add(listener);
    }
    public void removeEndListner(ButtonEndListener listener){
        this.listeners.remove(listener);
    }

    public void addDelete10Listner(CheckBoxRemoveEvery10SecListener listener){
        this.listenersDelete10.add(listener);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public VRDThread getVrdThread() {
        return vrdThread;
    }

    public synchronized void increaseIncoming(){
        this.incoming++;
        this.vrdLabel.setText("Number of recieved msg: " + incoming);
    }
}
