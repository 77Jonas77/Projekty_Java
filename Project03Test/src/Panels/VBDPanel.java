package Panels;

import Events.ButtonEndEvent;
import Events.ComboBoxStatusChangedEvent;
import Events.SliderValueChangedEvent;
import Interfeces.ButtonEndListener;
import Interfeces.ComboBoxStatusChangeListener;
import Interfeces.SliderValueChangedListener;
import Layers.BtsLayerLeft;
import Threads.VBDThread;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VBDPanel extends MyDevicePanel{

    private String msg;
    private int ID;
    public static int counter=1;

    private ArrayList<ButtonEndListener> listeners;
    private ArrayList<ComboBoxStatusChangeListener> listenersStatus;

    private ArrayList<SliderValueChangedListener> listenersValueChanged;
    private BtsLayerLeft nextLayer;
    private VBDThread vbdThread;

    public VBDPanel(String msg, BtsLayerLeft btsLayerLeft) {
        this.nextLayer = btsLayerLeft;
        this.msg = msg;
        this.ID = counter++;
        this.listeners = new ArrayList<>();
        this.listenersStatus = new ArrayList<>();
        this.listenersValueChanged = new ArrayList<>();

        JPanel vbdPanel = new JPanel();
        vbdPanel.setLayout(new GridLayout());

        JSlider freqSlider = new JSlider(1,60);
        freqSlider.setMinimum(1);
        freqSlider.setMaximum(60);
        freqSlider.setValue(1);
        freqSlider.setMajorTickSpacing(10);
        freqSlider.setMinorTickSpacing(5);
        freqSlider.setPaintTicks(true);
        freqSlider.setPaintLabels(true);
        freqSlider.setLabelTable(freqSlider.createStandardLabels(5));
        freqSlider.setOrientation(SwingConstants.HORIZONTAL);


        JButton endButton = new JButton("End");
        JTextField idField = new JTextField(String.valueOf(ID));
        idField.setHorizontalAlignment(SwingConstants.CENTER);

        idField.setEditable(false);
        JComboBox<String> stateComboBox = new JComboBox<>(new String[]{"ACTIVE", "WAITING"});

        vbdPanel.add(freqSlider);
        vbdPanel.add(endButton);
        vbdPanel.add(idField);
        vbdPanel.add(stateComboBox);

        vbdThread = new VBDThread(nextLayer, msg);
        vbdThread.start();

        this.addEndListner(vbdThread);
        this.addStatusListner(vbdThread);
        this.addValueChangedListner(vbdThread);

        endButton.addActionListener(e ->{
            fireEnd(true);
            Container parentContainer = vbdPanel.getParent();
            parentContainer.remove(vbdPanel);
            parentContainer.revalidate();
            parentContainer.repaint();

        });

        stateComboBox.addActionListener(e -> {
            fireState(stateComboBox.getSelectedIndex() != 0);
        });

        freqSlider.addChangeListener(e -> {
            int sliderValue = freqSlider.getValue();
            fireValue(sliderValue);
        });

        add(vbdPanel);
    }

    protected void fireEnd(boolean evt){
        ButtonEndEvent et = new ButtonEndEvent(this, evt);
        for(ButtonEndListener listener : listeners)
            listener.buttonEnd(et);
    }

    protected void fireState(boolean evt) {
        ComboBoxStatusChangedEvent et = new ComboBoxStatusChangedEvent(this, evt);
        for (ComboBoxStatusChangeListener listener : listenersStatus)
            listener.ComboBoxStatusChange(et);
    }

    protected void fireValue(int evt) {
        SliderValueChangedEvent et = new SliderValueChangedEvent(this, evt);
        for (SliderValueChangedListener listener : listenersValueChanged)
            listener.sliderValueChanged(et);
    }

    public void addEndListner(ButtonEndListener listener){
        this.listeners.add(listener);
    }
    public void addStatusListner(ComboBoxStatusChangeListener listener){
        this.listenersStatus.add(listener);
    }
    public void addValueChangedListner(SliderValueChangedListener listener){
        this.listenersValueChanged.add(listener);
    }
    public void removeEndListner(ButtonEndListener listener){
        this.listeners.remove(listener);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public BtsLayerLeft getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(BtsLayerLeft nextLayer) {
        this.nextLayer = nextLayer;
    }

    public VBDThread getVbdThread() {
        return vbdThread;
    }
}
