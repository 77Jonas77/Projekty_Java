package Threads;

import Events.ButtonEndEvent;
import Events.ComboBoxStatusChangedEvent;
import Events.SliderValueChangedEvent;
import Interfeces.ButtonEndListener;
import Interfeces.ComboBoxStatusChangeListener;
import Interfeces.SliderValueChangedListener;
import Layers.BtsLayerLeft;
import Panels.VRDPanel;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class VBDThread extends MyDeviceThread implements ButtonEndListener, ComboBoxStatusChangeListener, SliderValueChangedListener {

    private int smsFreq;
    private boolean paused;
    private byte[] pdu;

    private int smsSentCounter;

    private int freq;
    private boolean checkStatus;
    private boolean working;
    public static ArrayList<VRDPanel> reciversList = new ArrayList<>();
    private BtsLayerLeft nextLayer;

    public VBDThread(BtsLayerLeft nextLayer, String msg) {
        super();
        working = true;
        this.nextLayer = nextLayer;
        paused = false;
        freq = 1;
    }

//metoda niszczaca vbd musi working = false;

    @Override
    public void run() {
        while (working) {
            if (!paused) {
                this.pdu = createPduReciver();
                if (pdu != null) {
                    nextLayer.sendToNext(pdu);
                    increaseSentSms();
                }
            }
            try {
                //i tutaj bedzie czestotliwosc
                Thread.sleep(1000 / freq);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected synchronized void endKeeping() {
        this.pdu = null;
    }

    public synchronized static int readPduNumber(byte[] pdu) {
        return pdu[0];
    }

    @Override
    public void buttonEnd(ButtonEndEvent evt) {
        if (evt.isEnd()) {
            working = false;
        }
    }

    @Override
    public void ComboBoxStatusChange(ComboBoxStatusChangedEvent evt) {
        if (evt.isPaused()) {
            paused = true;
        } else {
            paused = false;
        }
    }

    @Override
    public void sliderValueChanged(SliderValueChangedEvent evt) {
        freq = evt.getValue();
    }

    public int getSmsSentCounter() {
        return smsSentCounter;
    }

    public synchronized void increaseSentSms() {
        smsSentCounter++;
    }

    public byte[] getPdu() {
        return pdu;
    }
}
