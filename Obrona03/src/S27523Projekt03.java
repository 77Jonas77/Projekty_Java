import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class S27523Projekt03 {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class BscEvent extends EventObject {

    private boolean AddRemove;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BscEvent(Object source, boolean AddRemove) {
        super(source);
        this.AddRemove = AddRemove;
    }

    public boolean isAddRemove() {
        return AddRemove;
    }
}

class ButtonEndEvent extends EventObject {

    private boolean end;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ButtonEndEvent(Object source, boolean end) {
        super(source);
        this.end = end;
    }

    public boolean isEnd() {
        return end;
    }
}

class CheckBoxVRDEvent extends EventObject {

    private boolean deleteevery10sec;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CheckBoxVRDEvent(Object source, boolean deleteevery10sec) {
        super(source);
        this.deleteevery10sec = deleteevery10sec;
    }

    public boolean isDeleteEvery10sec() {
        return deleteevery10sec;
    }
}

class ComboBoxStatusChangedEvent extends EventObject {

    private boolean status;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ComboBoxStatusChangedEvent(Object source, boolean status) {
        super(source);
        this.status = status;
    }

    public boolean isPaused() {
        return status;
    }
}

class SliderValueChangedEvent extends EventObject {
    private int value;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public SliderValueChangedEvent(Object source, int value) {
        super(source);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class WindowClosingEvent extends EventObject {
    private boolean closed;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public WindowClosingEvent(Object source, boolean closed) {
        super(source);
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }
}

interface WindowClosingListener {
    void windowClosing(WindowClosingEvent evt);
}

interface SliderValueChangedListener {
    void sliderValueChanged(SliderValueChangedEvent evt);
}

interface SendToNextInterface {
    void sendToNext(byte[] pdu);
}

interface ComboBoxStatusChangeListener {
    void ComboBoxStatusChange(ComboBoxStatusChangedEvent evt);
}

interface CheckBoxRemoveEvery10SecListener {
    void checkBoxRemoveEvery10Sec(CheckBoxVRDEvent evt);
}

interface ButtonEndListener {
    void buttonEnd(ButtonEndEvent evt);
}

interface ButtonAddRemoveBscListener {
    void addRemoveBsc(BscEvent evt);
}

class MyFrame {
    private ArrayList<WindowClosingListener> listeners;

    public MyFrame() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        listeners = new ArrayList<>();
        RightPanel panelRight = new RightPanel();
        MiddlePanel panelMiddle = new MiddlePanel(panelRight);
        LeftPanel panelLeft = new LeftPanel(panelMiddle.getBtsLayerLeft(), panelRight.getReciversList());

        listeners.add(panelLeft);

        frame.add(panelLeft, BorderLayout.WEST);
        frame.add(panelMiddle, BorderLayout.CENTER);
        frame.add(panelRight, BorderLayout.EAST);

        frame.setVisible(true);
        frame.setSize(1260, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fireWindowClosing(true);
            }
        });
    }

    private void fireWindowClosing(boolean evt) {
        for (WindowClosingListener listener : listeners) {
            listener.windowClosing(new WindowClosingEvent(this, evt));
        }
    }
}

class LeftPanel extends Layer implements WindowClosingListener {

    private JPanel scrollPanel;
    private ArrayList<VBDPanel> VBDPanelList;

    public LeftPanel(BtsLayerLeft btsLayerLeft, ArrayList<VRDPanel> reciversList) {
        VBDPanelList = new ArrayList<>();
        this.setPreferredSize(new Dimension(300, this.getHeight()));
        this.setLayout(new BorderLayout());

        this.scrollPanel = new JPanel();
        this.scrollPanel.setLayout(new BoxLayout(this.scrollPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(this.scrollPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = JOptionPane.showInputDialog(this, "Enter short messesage: ");
                if (msg != null && !msg.isEmpty()) {
                    VBDPanel vbd = new VBDPanel(msg, btsLayerLeft); //to jest watek, ktory ma sie uruchomic
                    addVBD(vbd);
                    revalidate();
                    repaint();
                    scrollPane.revalidate();
                    scrollPane.repaint();
                }
            }
        });
        add(addButton, BorderLayout.SOUTH);
    }

    private void addVBD(VBDPanel vbdPanel) {
        this.scrollPanel.add(vbdPanel);
        VBDPanelList.add(vbdPanel);
    }

    @Override
    public void windowClosing(WindowClosingEvent evt) {
        if (evt.isClosed()) {
            if(!VBDPanelList.isEmpty())
                FileManager.saveDataToFile(VBDPanelList);
        }
    }
}

class MiddlePanel extends JPanel {

    private JPanel jpanelCenter;
    private ArrayList<ButtonAddRemoveBscListener> listeners = new ArrayList<>();

    private BtsLayerLeft btsLayerLeft;

    private BtsLayerRight btsLayerRight;
    public static SendToNextInterface nextLayerForBsc;

    private RightPanel VrdLayer;

    //NASLUCHWIANIE DEF: czyli jak button wysyla event poprzez fire, to powiadamy wszystkich nasluchujacych (bscLayerPanel), czyli wywolujemy metode zaimplementowana przez interfejs w celu (np. usuniecia ostatniego panelu z listy)
    public MiddlePanel(RightPanel panelRight) {
        setLayout(new BorderLayout());

        this.VrdLayer = panelRight;

        jpanelCenter = new JPanel();
        jpanelCenter.setLayout(new BoxLayout(jpanelCenter, BoxLayout.X_AXIS));

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


    public void addBscListner(ButtonAddRemoveBscListener listener) {
        this.listeners.add(listener);
    }

    public void removeBscListner(ButtonAddRemoveBscListener listener) {
        this.listeners.remove(listener);
    }

    protected void fireAddRemoveBsc(boolean evt) {
        BscEvent et = new BscEvent(this, evt);
        for (ButtonAddRemoveBscListener listener : listeners)
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


class RightPanel extends Layer implements SendToNextInterface {

    private JPanel scrollPanel;
    public static ArrayList<VRDThread> VRDPanelsList;
    public static ArrayList<VRDPanel> reciversList;

    public RightPanel() {
        reciversList = new ArrayList<>();
        VRDPanelsList = new ArrayList<VRDThread>();
        this.setPreferredSize(new Dimension(300, this.getHeight()));
        this.setLayout(new BorderLayout());

        this.scrollPanel = new JPanel();
        this.scrollPanel.setLayout(new BoxLayout(this.scrollPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(this.scrollPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            VRDPanel vrd = new VRDPanel();
            addVRD(vrd);
            revalidate();
            repaint();
            VBDThread.reciversList.add(vrd);
            scrollPane.revalidate();
            scrollPane.repaint();
        });
        add(addButton, BorderLayout.SOUTH);
    }

    private void addVRD(VRDPanel vrdPanel) {
        this.scrollPanel.add(vrdPanel);
        VRDPanelsList.add(vrdPanel.getVrdThread());
        reciversList.add(vrdPanel);
    }

    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        boolean match = false;
        for (VRDPanel vrdPanel : reciversList) {
            if (Objects.equals(vrdPanel.getReciverNr(), VBDThread.decryptPduReciver(pdu))) {
                vrdPanel.increaseIncoming();
                match = true;
            }
        }
        if (!match) {
            try {
                throw new NoReciverException();
            } catch (NoReciverException e) {
                System.out.println("Nie ma takiego odbiorcy");
            }
        }
    }

    public ArrayList<VRDPanel> getReciversList() {
        return reciversList;
    }
}

class Layer extends JPanel {
    public Layer() {

    }
}

class BtsLayerLeft extends Layer implements SendToNextInterface {
    private JPanel BtsLayerJPanel;
    protected ArrayList<BtsLeftThread> btsChoosePanel;
    private BscColumnLayer bscColumnLayer;
    private boolean additionCheckThreadWorking;

    private JScrollPane scrollPane;

    public BtsLayerLeft(BscColumnLayer bscColumnLayer) {
        this.BtsLayerJPanel = new JPanel();
        btsChoosePanel = new ArrayList<>();
        this.bscColumnLayer = bscColumnLayer;
        this.BtsLayerJPanel.setLayout(new BoxLayout(BtsLayerJPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(BtsLayerJPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ustawienie widocznej poziomej belki przewijania
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLUE);
        separator.setForeground(Color.BLUE);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(separator, BorderLayout.EAST); // Dodanie separatora po prawej stronie JPanel

        addNewBtsColumn();

        additionCheckThreadWorking = true;
        Thread additionCheckThread = new Thread(() -> {
            while (additionCheckThreadWorking) {
                if (checkIfAddPanel())
                    addNewBtsLeftPanel();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        additionCheckThread.start();
    }

    private synchronized boolean checkIfAddPanel() {
        for (BtsLeftThread btsLeftThread : btsChoosePanel) {
            if (btsLeftThread.getIncomingMsg() <= 5)
                return false;
        }
        return true;
    }

    private void addNewBtsColumn() {
        JPanel btsContainer = new JPanel();
        btsContainer.setLayout(new BoxLayout(btsContainer, BoxLayout.Y_AXIS)); // Ustawienie układu pionowego

        BtsPanelLeft btsPanel = new BtsPanelLeft(this.bscColumnLayer);
        addTobtsChoosePanel(btsPanel.getBtsLeftThread());

        btsContainer.add(btsPanel);

        this.BtsLayerJPanel.add(btsContainer);
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();

    }

    private void addNewBtsLeftPanel() {
        BtsPanelLeft btsPanel = new BtsPanelLeft(this.bscColumnLayer);
        addTobtsChoosePanel(btsPanel.getBtsLeftThread());
        BtsLayerJPanel.add(btsPanel);
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();
    }

    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        BtsLeftThread next = btsChoosePanel.get(0);
        for (BtsLeftThread btsPanel : btsChoosePanel) {
            if (next.getIncomingMsg() > btsPanel.getIncomingMsg())
                next = btsPanel;
        }
        next.receiveMsg(pdu);
    }

    public ArrayList<BtsLeftThread> getBtsChoosePanel() {
        return btsChoosePanel;
    }

    protected void addTobtsChoosePanel(BtsLeftThread bts) {
        this.btsChoosePanel.add(bts);
    }

    public BscColumnLayer getBscColumnLayer() {
        return bscColumnLayer;
    }

    public void setBscColumnLayer(BscColumnLayer bscColumnLayer) {
        this.bscColumnLayer = bscColumnLayer;
    }

    public boolean isAdditionCheckThreadWorking() {
        return additionCheckThreadWorking;
    }

    public void setAdditionCheckThreadWorking(boolean additionCheckThreadWorking) {
        this.additionCheckThreadWorking = additionCheckThreadWorking;
    }
}

class BscLayer extends JPanel implements ButtonAddRemoveBscListener {

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

        JScrollPane scrollPane = new JScrollPane(BscLayerJPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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

        if (bscColumns.size() > 1) {
            ArrayList<BscThread> bscThreadlist = bscColumns.get(bscColumns.size() - 2).getBcsChoosePanel();
            for (BscThread bscThread : bscThreadlist) {
                bscThread.setNextLayer(layer);
                bscThread.getBscPanel().setNextLayer(layer);
            }
            bscColumns.get(bscColumns.size() - 2).setNextLayer(layer);
        }
        this.BscPanels.add(jsp);
    }

    private void removeLastBscPanel() {
        int lastIndex = BscPanels.size() - 1;
        if (lastIndex > 0) {
            JScrollPane lastPanel = BscPanels.get(lastIndex);
            BscPanels.remove(lastIndex);
            this.BscLayerJPanel.remove(lastPanel);
            this.BscLayerJPanel.revalidate();
            this.BscLayerJPanel.repaint();

            BscColumnLayer lastCol = bscColumns.get(lastIndex);
            BscColumnLayer beforeLastCol = bscColumns.get(lastIndex - 1);
            ArrayList<BscThread> lastColThreadsCopy = lastCol.getBcsChoosePanel();
            ArrayList<BscThread> beforelastColThreadsCopy = beforeLastCol.getBcsChoosePanel();

            beforeLastCol.setNextLayer(lastCol.getNextLayer());

            for (BscThread bscThread : beforeLastCol.getBcsChoosePanel()) {
                bscThread.setNextLayer(lastCol.getNextLayer());
            }

            bscColumns.remove(lastIndex);

            for (BscThread bscThread : lastColThreadsCopy) {
                bscThread.setSendAllMsgNow(true);
            }
            for (BscThread bscThread : lastColThreadsCopy) {
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

class BscColumnLayer extends Layer implements SendToNextInterface {
    protected ArrayList<BscThread> bcsChoosePanel;

    private SendToNextInterface nextLayer;
    private Thread additionCheckThread;
    private boolean additionCheckThreadWorking;

    private JPanel bscContainerColumn;
    private JScrollPane scrollPane;

    public BscColumnLayer(SendToNextInterface nextLayer) {
        this.bcsChoosePanel = new ArrayList<BscThread>();
        this.nextLayer = nextLayer;
        bscContainerColumn = new JPanel();
        bscContainerColumn.setLayout(new BoxLayout(bscContainerColumn, BoxLayout.Y_AXIS));

        JPanel bscContainer = new JPanel();
        bscContainer.setLayout(new BoxLayout(bscContainer, BoxLayout.Y_AXIS));
        BscPanel bscPanel = new BscPanel(this.nextLayer);
        addTobcsChoosePanel(bscPanel.getBscThread());

        scrollPane = new JScrollPane(bscContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        bscContainer.add(bscPanel);

        // preferowane rozmiary dla scrollPane i bscContainer
        Dimension preferredSize = new Dimension(bscPanel.getPreferredSize().width + 30, bscPanel.getPreferredSize().height);
        scrollPane.setPreferredSize(preferredSize);
        bscContainer.setPreferredSize(new Dimension(preferredSize.width, bscContainer.getPreferredSize().height));

        MiddlePanel.nextLayerForBsc = this;
        bscContainerColumn.add(scrollPane);
        add(bscContainerColumn);

        additionCheckThreadWorking = true;
        additionCheckThread = new Thread(() -> {
            while (additionCheckThreadWorking) {
                if (checkIfAddPanel())
                    addNewBscPanel();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        additionCheckThread.start();
    }

    private void addNewBscPanel() {
        BscPanel bscPanel = new BscPanel(this.nextLayer);
        addTobcsChoosePanel(bscPanel.getBscThread());
        add(bscPanel);

        scrollPane = new JScrollPane(bscPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // preferowane rozmiary dla scrollPane i bscContainer
        Dimension preferredSize = new Dimension(bscPanel.getPreferredSize().width + 30, bscPanel.getPreferredSize().height);
        scrollPane.setPreferredSize(preferredSize);

        bscContainerColumn.add(scrollPane);
        revalidate();
        repaint();
    }

    private boolean checkIfAddPanel() {
        for (BscThread bscThread : bcsChoosePanel) {
            if (bscThread.getIncomingMsg() <= 5)
                return false;
        }
        return true;
    }

    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        BscThread next = bcsChoosePanel.get(0);
        for (BscThread bcsPanel : bcsChoosePanel) {
            if (next.getIncomingMsg() > bcsPanel.getIncomingMsg())
                next = bcsPanel;
        }
        next.receiveMsg(pdu);
    }

    protected synchronized void addTobcsChoosePanel(BscThread bcs) {
        this.bcsChoosePanel.add(bcs);
    }

    public synchronized ArrayList<BscThread> getBcsChoosePanel() {
        return bcsChoosePanel;
    }

    public synchronized SendToNextInterface getNextLayer() {
        return nextLayer;
    }

    public synchronized void setNextLayer(SendToNextInterface nextLayer) {
        this.nextLayer = nextLayer;
    }

}

class BtsLayerRight extends Layer implements SendToNextInterface {
    private JPanel BtsLayerJPanel;

    protected ArrayList<BtsRightThread> btsChoosePanel;

    private RightPanel RightPanelLayer;

    private boolean additionCheckThreadWorking;

    public BtsLayerRight(RightPanel RightPanelLayer) {
        this.BtsLayerJPanel = new JPanel();
        this.BtsLayerJPanel.setLayout(new BoxLayout(BtsLayerJPanel, BoxLayout.Y_AXIS));
        this.btsChoosePanel = new ArrayList<>();
        this.RightPanelLayer = RightPanelLayer;

        JScrollPane scrollPane = new JScrollPane(BtsLayerJPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLUE);
        separator.setForeground(Color.BLUE);

        setLayout(new BorderLayout());
        add(separator, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        addNewBtsColumn();

        additionCheckThreadWorking = true;
        Thread additionCheckThread = new Thread(() -> {
            while (additionCheckThreadWorking) {
                if (checkIfAddPanel())
                    addNewBtsRightPanel();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        additionCheckThread.start();
    }

    private synchronized boolean checkIfAddPanel() {
        for (BtsRightThread btsRightThread : btsChoosePanel) {
            if (btsRightThread.getIncomingMsg() <= 5)
                return false;
        }
        return true;
    }

    private synchronized void addNewBtsColumn() {
        JPanel btsContainer = new JPanel();
        btsContainer.setLayout(new BoxLayout(btsContainer, BoxLayout.Y_AXIS)); // Ustawienie układu pionowego

        BtsPanelRight btsPanel = new BtsPanelRight(RightPanelLayer);

        btsContainer.add(btsPanel);

        this.BtsLayerJPanel.add(btsContainer);
        this.btsChoosePanel.add(btsPanel.getBtsRightThread());
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();

    }

    private void addNewBtsRightPanel() {
        BtsPanelRight btsPanel = new BtsPanelRight(RightPanelLayer);

        this.btsChoosePanel.add(btsPanel.getBtsRightThread());
        BtsLayerJPanel.add(btsPanel);
        this.BtsLayerJPanel.revalidate();
        this.BtsLayerJPanel.repaint();
    }

    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        BtsRightThread next = btsChoosePanel.get(0);
        for (BtsRightThread btsPanel : btsChoosePanel) {
            if (next.getIncomingMsg() > btsPanel.getIncomingMsg())
                next = btsPanel;
        }
        next.receiveMsg(pdu);
    }

    public RightPanel getRightPanelLayer() {
        return RightPanelLayer;
    }

    public void setRightPanelLayer(RightPanel rightPanelLayer) {
        RightPanelLayer = rightPanelLayer;
    }
}

class VBDPanel extends MyDevicePanel {

    private String msg;
    private int ID;
    public static int counter = 1;

    private ArrayList<ButtonEndListener> listeners;
    private ArrayList<ComboBoxStatusChangeListener> listenersStatus;

    private ArrayList<SliderValueChangedListener> listenersValueChanged;
    private BtsLayerLeft nextLayer;
    private VBDThread vbdThread;

    public VBDPanel(String msg, BtsLayerLeft btsLayerLeft) {
        this.nextLayer = btsLayerLeft;
        this.msg = msg;
        this.ID = counter;
        increaseCounter();
        this.listeners = new ArrayList<>();
        this.listenersStatus = new ArrayList<>();
        this.listenersValueChanged = new ArrayList<>();

        JPanel vbdPanel = new JPanel();
        vbdPanel.setLayout(new GridLayout());

        JSlider freqSlider = new JSlider(1, 60);
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

        endButton.addActionListener(e -> {
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

    protected void fireEnd(boolean evt) {
        ButtonEndEvent et = new ButtonEndEvent(this, evt);
        for (ButtonEndListener listener : listeners)
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

    public synchronized void addEndListner(ButtonEndListener listener) {
        this.listeners.add(listener);
    }

    public synchronized void addStatusListner(ComboBoxStatusChangeListener listener) {
        this.listenersStatus.add(listener);
    }

    public synchronized void addValueChangedListner(SliderValueChangedListener listener) {
        this.listenersValueChanged.add(listener);
    }

    public synchronized void removeEndListner(ButtonEndListener listener) {
        this.listeners.remove(listener);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public synchronized void increaseCounter() {
        counter++;
    }

    public synchronized int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public synchronized  BtsLayerLeft getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(BtsLayerLeft nextLayer) {
        this.nextLayer = nextLayer;
    }

    public synchronized VBDThread getVbdThread() {
        return vbdThread;
    }
}

class VRDPanel extends MyDevicePanel {
    private int ID;
    public static int counter = 1;

    private String ReciverNr;
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
        this.sent = 0;
        this.incoming = 0;
        recievedMsgCounter = 0;
        this.ID = counter;
        increaseCounter();
        this.listeners = new ArrayList<>();
        this.listenersDelete10 = new ArrayList<>();

        this.ReciverNr = "048" + String.valueOf(this.ID);
        while (ReciverNr.length() != 11) {
            ReciverNr = ReciverNr + "0";
        }

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
        deleteEvery10sThread = new Thread(() -> {
            while (deleteEvery10s) {
                System.out.println("Xd");
                this.incoming = 0;
                try {
                    Thread.sleep(9999);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        vrdEndButton.addActionListener(e -> {
            fireEnd(true);
            Container parentContainer = vrdPanel.getParent();

            RightPanel.VRDPanelsList.remove(this.getVrdThread());
            RightPanel.reciversList.remove(this);
            VBDThread.reciversList.remove(this);
            deleteEvery10s = false;

            parentContainer.remove(vrdPanel);
            parentContainer.revalidate();
            parentContainer.repaint();
        });

        vrdCheckBox.addActionListener(e -> {
            if (vrdCheckBox.isSelected()) {
                this.deleteEvery10s = true;
                deleteEvery10sThread.start();
            } else {
                this.deleteEvery10s = false;
            }
        });

        add(vrdPanel);
    }

    public synchronized void increaserecievedMsgCounter() {
        this.recievedMsgCounter++;
    }

    protected void fireEnd(boolean evt) {
        ButtonEndEvent et = new ButtonEndEvent(this, evt);
        for (ButtonEndListener listener : listeners)
            listener.buttonEnd(et);
    }

    protected void fireDeleteEvery10sec(boolean evt) {
        CheckBoxVRDEvent et = new CheckBoxVRDEvent(this, evt);
        for (CheckBoxRemoveEvery10SecListener listener : listenersDelete10)
            listener.checkBoxRemoveEvery10Sec(et);
    }

    public void addEndListner(ButtonEndListener listener) {
        this.listeners.add(listener);
    }

    public void removeEndListner(ButtonEndListener listener) {
        this.listeners.remove(listener);
    }

    public void addDelete10Listner(CheckBoxRemoveEvery10SecListener listener) {
        this.listenersDelete10.add(listener);
    }

    public synchronized void increaseCounter() {
        counter++;
    }

    public synchronized int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public synchronized VRDThread getVrdThread() {
        return vrdThread;
    }

    public synchronized void increaseIncoming() {
        this.incoming++;
        this.vrdLabel.setText("Number of recieved msg: " + incoming);
    }

    public synchronized String getReciverNr() {
        return ReciverNr;
    }
}

class BtsPanel extends MyDevicePanel {
    private int incoming;
    private int sent;
    private JLabel processedLabel;
    private JLabel pendingLabel;
    public BtsPanel() {
        super();
        sent = 0;
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

    public synchronized void increaseSent() {
        this.sent++;
        this.processedLabel.setText("Przetworzone: " + sent);
    }

    public synchronized void increaseIncoming() {
        this.incoming++;
        this.pendingLabel.setText("Oczekujące: " + incoming);
    }

    public synchronized void decreaseIncoming() {
        this.incoming--;
        this.pendingLabel.setText("Oczekujące: " + incoming);
    }

    public synchronized int getIncoming() {
        return incoming;
    }

    public void setIncoming(int incoming) {
        this.incoming = incoming;
    }

    public synchronized int getSent() {
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

class BtsPanelLeft extends BtsPanel {

    private int processedLabel;
    private int pendingLabel;
    private BtsLeftThread btsLeftThread;

    public BtsPanelLeft(BscColumnLayer nextLayer) {
        super();
        btsLeftThread = new BtsLeftThread(nextLayer, this);
        btsLeftThread.start();
    }

    public BtsLeftThread getBtsLeftThread() {
        return btsLeftThread;
    }
}

class BtsPanelRight extends BtsPanel {

    private int processedLabel;
    private int pendingLabel;
    private BtsRightThread BtsRightThread;

    public BtsPanelRight(RightPanel nextLayer) {
        super();
        BtsRightThread = new BtsRightThread(nextLayer, this);
        BtsRightThread.start();
    }

    public synchronized BtsRightThread getBtsRightThread() {
        return BtsRightThread;
    }

}

class BscPanel extends MyDevicePanel {

    private int incoming;
    private int sent;
    private SendToNextInterface nextLayer;
    private BscThread bscThread;
    private JLabel processedLabel;
    private JLabel pendingLabel;

    public BscPanel(SendToNextInterface nextLayer) {
        super();
        this.sent = 0;
        this.incoming = 0;
        this.nextLayer = nextLayer;
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

    public synchronized void increaseSent() {
        this.sent++;
        this.processedLabel.setText("Przetworzone: " + sent);
    }

    public synchronized void increaseIncoming() {
        this.incoming++;
        this.pendingLabel.setText("Oczekujące: " + incoming);
    }

    public synchronized void decreaseIncoming() {
        this.incoming--;
        this.pendingLabel.setText("Oczekujące:  " + incoming);
    }

    public synchronized BscThread getBscThread() {
        return bscThread;
    }

    public synchronized SendToNextInterface getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(SendToNextInterface nextLayer) {
        this.nextLayer = nextLayer;
    }
}

class MyDevicePanel extends JPanel {
    protected int ID;
    protected static int stationIndex = 1;

    public MyDevicePanel() {
        this.ID = stationIndex;
        increaseStationIndex();
    }

    private synchronized void increaseStationIndex(){
        stationIndex++;
    }
    public synchronized int getID() {
        return ID;
    }
}

class VBDThread extends MyDeviceThread implements ButtonEndListener, ComboBoxStatusChangeListener, SliderValueChangedListener {

    private int smsFreq;
    private boolean paused;
    private byte[] pdu;

    private int smsSentCounter;
    private String msg;
    private int freq;
    private boolean checkStatus;
    private boolean working;
    public static ArrayList<VRDPanel> reciversList = new ArrayList<>();
    private BtsLayerLeft nextLayer;

    private final static char[] SEVEN_BITCHARSET = {
            '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'ç', '\n', 'Ø', 'ø', '\r', 'Å', 'å',
            'Ä', '_', 'Ö', 'Ã', 'Ë', 'Ù', 'Ð', 'Ø', 'Ó', 'È', 'Î', '\033', 'Æ', 'æ', 'ß', 'É',
            ' ', '!', '“', '#', '¤', '%', '&', '‘', '(', ')', '*', '+', ',', '-', '.', '/',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
            '¡', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§',
            '¿', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à'
    };

    public VBDThread(BtsLayerLeft nextLayer, String msg) {
        super();
        working = true;
        this.msg = msg;
        this.nextLayer = nextLayer;
        paused = false;
        freq = 1;
    }

    @Override
    public void run() {
        while (working) {
            if (!paused) {
                this.pdu = createPduReciver(this.msg);
                if (!Arrays.equals(pdu, new byte[]{-1})) {
                    nextLayer.sendToNext(pdu);
                    increaseSentSms();
                }
            }
            try {
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

    public static synchronized String decryptPduReciver(byte[] pdu) {
        byte[] semiOctet = {pdu[11], pdu[12], pdu[13], pdu[14], pdu[15], pdu[16]};
        String decrypted = "";
        int iterator = 0;
        for (byte octet : semiOctet) {
            int firstDigit = octet & 0b00001111;
            int secondDigit = (octet >> 4) & 0b00001111;
            decrypted += String.valueOf(firstDigit);
            decrypted += String.valueOf(secondDigit);
            iterator++;
        }

        //z racji ze u nas zawsze jest nieparzyste, to usuwamy ostatnia cyfre
        String finalNr = "";
        for (int i = 0; i < decrypted.length() - 2; i++) {
            finalNr += decrypted.charAt(i);
        }
        return finalNr;
    }

    private synchronized byte[] createPduReciver(String msg) { //synchronized
        if (!reciversList.isEmpty()) {

            byte TypeOfAddres = (byte) 0b10010001;

            String smsc = "85290288000";
            byte[] AddressValue = semiOctetRepr(smsc);

            int addresSMSCLength = (smsc.length() % 2 == 0 ? smsc.length() / 2 : smsc.length() / 2 + 1);
            byte AddressLength = (byte) (addresSMSCLength + 1);

            byte[] etap1 = new byte[(addresSMSCLength + 2)];

            etap1[0] = AddressLength;
            etap1[1] = TypeOfAddres;

            int iteratorEtap1 = 2;
            for (int i = 0; i < addresSMSCLength; i++) {
                etap1[iteratorEtap1++] = AddressValue[i];
            }

            int reciverNumber = reciversList.get((int) (Math.random() * reciversList.size())).getID();

            String number = String.valueOf(reciverNumber);
            number = "048" + number;
            while (number.length() != 11) {
                number += "0";
            }
            String data = number;
            LocalDateTime now = LocalDateTime.now();

            String year = "" + String.valueOf(now.getYear()).charAt(2) + String.valueOf(now.getYear()).charAt(3);
            String month = String.valueOf(now.getMonthValue());
            String day = String.valueOf(now.getDayOfMonth());
            String hour = String.valueOf(now.getHour());
            String minute = String.valueOf(now.getMinute());
            String second = String.valueOf(now.getSecond());
            String timezone = "08";

            if (month.length() == 1) {
                month = "0" + month;
            }
            if (day.length() == 1) {
                day = "0" + day;
            }
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            if (minute.length() == 1) {
                minute = "0" + minute;
            }
            if (second.length() == 1) {
                second = "0" + second;
            }

            byte firstOctet421 = 0b00000000;
            byte[] TPOA422 = new byte[(data.length() % 2 == 0 ? data.length() / 2 : (data.length() / 2 + 1)) + 2];
            byte[] AddressValue2 = semiOctetRepr(data);
            byte TypeOfAddres2 = (byte) 0b10010001;
            byte AddressLength2 = (byte) data.length();

            TPOA422[0] = AddressLength2;
            TPOA422[1] = TypeOfAddres2;
            int iterator = 2;
            for (byte bit : AddressValue2) {
                TPOA422[iterator++] = bit;
            }


            byte TPPID423 = 0b00000000;
            byte TPDCS424 = 0b00000000;
            String dataTimeStamp = year + month + day + hour + minute + second + timezone;
            byte[] TPSCTS = semiOctetRepr(dataTimeStamp);
            byte TPUDL = (byte) msg.length();
            byte[] TPUD = encode7BitPdu(msg);
            int etap2Length = ((data.length() % 2 == 0 ? data.length() / 2 : (data.length() / 2 + 1)) + 6 + msg.length() + (dataTimeStamp.length() % 2 == 0 ? dataTimeStamp.length() / 2 : (dataTimeStamp.length() / 2 + 1)));
            byte[] etap2 = new byte[etap2Length];

            etap2[0] = firstOctet421;
            int etap2Iterator = 1;
            for (byte bit : TPOA422) {
                etap2[etap2Iterator++] = bit;
            }
            etap2[etap2Iterator++] = TPPID423;
            etap2[etap2Iterator++] = TPDCS424;

            for (byte bit : TPSCTS) {
                etap2[etap2Iterator++] = bit;
            }

            etap2[etap2Iterator++] = TPUDL;
            for (byte bit : TPUD) {
                etap2[etap2Iterator++] = bit;
            }

            byte[] finalEtap = new byte[(addresSMSCLength + 2) + etap2Length];
            int finalEtapIterator = 0;
            for (int i = 0; i < (addresSMSCLength + 2); i++) {
                finalEtap[finalEtapIterator++] = etap1[i];
            }

            for (int i = 0; i < etap2Length; i++) {
                finalEtap[finalEtapIterator++] = etap2[i];
            }
            return finalEtap;
        }
        return new byte[]{-1};
    }

    public static byte[] encode7BitPdu(String msg) {
        int messageLength = msg.length();
        int pduLength = (messageLength * 7 + 7) / 8;
        byte[] encodedPdu = new byte[pduLength];
        int remainingBits = 0;
        int currentByteIndex = 0;

        for (int i = 0; i < messageLength; i++) {
            char c = msg.charAt(i);
            byte charValue = valueOfIndex(c);
            int shift = remainingBits;
            encodedPdu[currentByteIndex] |= (byte) (charValue << shift);

            remainingBits += 7;
            if (remainingBits >= 8) {
                remainingBits -= 8;
                currentByteIndex++;
                encodedPdu[currentByteIndex] = (byte) (charValue >> (7 - remainingBits));
            }
        }
        return encodedPdu;
    }

    public static byte valueOfIndex(char s) {
        for (int i = 0; i < SEVEN_BITCHARSET.length; i++) {
            if (s == SEVEN_BITCHARSET[i])
                return (byte) i;
        }
        return 0;
    }


    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();

        for (byte b : byteArray) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] semiOctetRepr(String smsc) {

        int semiLength = (smsc.length() % 2 == 0 ? smsc.length() / 2 : smsc.length() / 2 + 1);
        byte[] semiOctet = new byte[semiLength];

        if (smsc.length() % 2 == 0) {
            int smscIterator = 0;
            for (int i = 0; i < semiLength; i++) {
                semiOctet[i] |= (smsc.charAt(smscIterator++) - '0');
                semiOctet[i] |= (smsc.charAt(smscIterator++) - '0') << 4;
            }
        } else {
            int smscIterator = 0;
            for (int i = 0; i < semiLength; i++) {
                if (i + 1 != semiLength) {
                    semiOctet[i] |= (smsc.charAt(smscIterator++) - '0');
                    semiOctet[i] |= (smsc.charAt(smscIterator++) - '0') << 4;
                } else {
                    semiOctet[i] = (byte) 0b11110000;
                    semiOctet[i] |= (smsc.charAt(smscIterator++) - '0');
                }
            }
        }
        return semiOctet;
    }

    @Override
    public void sliderValueChanged(SliderValueChangedEvent evt) {
        freq = evt.getValue();
    }

    public synchronized int getSmsSentCounter() {
        return smsSentCounter;
    }

    public synchronized void increaseSentSms() {
        smsSentCounter++;
    }

    public synchronized byte[] getPdu() {
        return pdu;
    }
}


class BtsLeftThread extends Thread {

    private int incomingMsg;
    private int sentMsg;
    private int head;
    private int tail;
    private byte[] pdu;
    private SendToNextInterface nextLayer;
    private boolean working;
    private Queue<byte[]> pendingMsgg;
    private BtsPanelLeft btsPanelLeft;

    public BtsLeftThread(SendToNextInterface nextLayer, BtsPanelLeft btsPanelLeft) {
        this.btsPanelLeft = btsPanelLeft;
        working = true;
        this.incomingMsg = 0;
        this.sentMsg = 0;
        this.head = 0;
        this.tail = 0;
        this.nextLayer = nextLayer;
        pendingMsgg = new Queue<byte[]>() {
            private ArrayList<byte[]> list = new ArrayList<>();

            @Override
            public synchronized boolean add(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized boolean offer(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized byte[] remove() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                byte[] element = list.remove(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] poll() {
                if (isEmpty()) {
                    return null;
                }
                byte[] element = list.get(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] element() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                return list.get(head);
            }

            @Override
            public synchronized byte[] peek() {
                if (isEmpty()) {
                    return null;
                }
                return list.get(head);
            }

            @Override
            public synchronized int size() {
                return tail - head;
            }

            @Override
            public synchronized boolean isEmpty() {
                return head == tail;
            }

            @Override
            public synchronized boolean contains(Object o) {
                return list.contains(o);
            }

            @Override
            public synchronized Iterator<byte[]> iterator() {
                return list.iterator();
            }

            @Override
            public synchronized Object[] toArray() {
                return list.toArray();
            }

            @Override
            public synchronized <T> T[] toArray(T[] a) {
                return list.toArray(a);
            }

            @Override
            public synchronized boolean remove(Object o) {
                return list.remove(o);
            }

            @Override
            public synchronized boolean containsAll(Collection<?> c) {
                return list.containsAll(c);
            }

            @Override
            public synchronized boolean addAll(Collection<? extends byte[]> c) {
                if (c.isEmpty()) {
                    return false;
                }
                for (byte[] element : c) {
                    list.add(element);
                }
                tail += c.size();
                return true;
            }

            @Override
            public synchronized boolean removeAll(Collection<?> c) {
                return list.removeAll(c);
            }

            @Override
            public synchronized boolean retainAll(Collection<?> c) {
                return list.retainAll(c);
            }

            @Override
            public synchronized void clear() {
                list.clear();
                head = 0;
                tail = 0;
            }
        };
    }

    @Override
    public void run() {
        while (working) {
            if (!pendingMsgg.isEmpty()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.pdu = pendingMsgg.poll();
                nextLayer.sendToNext(pdu);
                endKeeping();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void increaseIncomingMsg() {
        this.incomingMsg++;
    }

    public synchronized void decreaseIncomingMsg() {
        this.incomingMsg--;
    }

    public synchronized void increaseSentMsg() {
        this.incomingMsg++;
    }

    public synchronized int getIncomingMsg() {
        return incomingMsg;
    }

    public void setIncomingMsg(int incomingMsg) {
        this.incomingMsg = incomingMsg;
    }

    public int getSentMsg() {
        return sentMsg;
    }

    public void setSentMsg(int sentMsg) {
        this.sentMsg = sentMsg;
    }

    public synchronized void receiveMsg(byte[] pdu) {
        this.pendingMsgg.add(pdu);
        this.btsPanelLeft.increaseIncoming();
        increaseIncomingMsg();
    }


    protected synchronized void endKeeping() {
        this.pdu = null;
        this.btsPanelLeft.decreaseIncoming();
        this.btsPanelLeft.increaseSent();
        decreaseIncomingMsg();
        //incrementSentSMSCounter();
    }
}

class BscThread extends Thread {

    private int incomingMsg;
    private int pendingMsg;
    private int head;
    private int tail;
    private byte[] pdu;
    private boolean sendAllMsgNow;
    private SendToNextInterface nextLayer;
    private boolean working;
    private Queue<byte[]> pendingMsgg;
    private BscPanel bscPanel;

    public BscThread(SendToNextInterface nextLayer, BscPanel bscPanel) {
        this.bscPanel = bscPanel;
        working = true;
        this.incomingMsg = 0;
        this.pendingMsg = 0;
        this.head = 0;
        this.tail = 0;
        this.nextLayer = nextLayer;
        this.sendAllMsgNow = false;
        pendingMsgg = new Queue<byte[]>() {
            private ArrayList<byte[]> list = new ArrayList<>();

            @Override
            public synchronized boolean add(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized boolean offer(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized byte[] remove() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                byte[] element = list.remove(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] poll() {
                if (isEmpty()) {
                    return null;
                }
                byte[] element = list.get(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] element() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                return list.get(head);
            }

            @Override
            public synchronized byte[] peek() {
                if (isEmpty()) {
                    return null;
                }
                return list.get(head);
            }

            @Override
            public synchronized int size() {
                return tail - head;
            }

            @Override
            public synchronized boolean isEmpty() {
                return head == tail;
            }

            @Override
            public synchronized boolean contains(Object o) {
                return list.contains(o);
            }

            @Override
            public synchronized Iterator<byte[]> iterator() {
                return list.iterator();
            }

            @Override
            public synchronized Object[] toArray() {
                return list.toArray();
            }

            @Override
            public synchronized <T> T[] toArray(T[] a) {
                return list.toArray(a);
            }

            @Override
            public synchronized boolean remove(Object o) {
                return list.remove(o);
            }

            @Override
            public synchronized boolean containsAll(Collection<?> c) {
                return list.containsAll(c);
            }

            @Override
            public synchronized boolean addAll(Collection<? extends byte[]> c) {
                if (c.isEmpty()) {
                    return false;
                }
                for (byte[] element : c) {
                    list.add(element);
                }
                tail += c.size();
                return true;
            }

            @Override
            public synchronized boolean removeAll(Collection<?> c) {
                return list.removeAll(c);
            }

            @Override
            public synchronized boolean retainAll(Collection<?> c) {
                return list.retainAll(c);
            }

            @Override
            public synchronized void clear() {
                list.clear();
                head = 0;
                tail = 0;
            }
        };
    }

    @Override
    public void run() {
        while (working) {
            if (!pendingMsgg.isEmpty()) {
                if (!sendAllMsgNow) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.pdu = pendingMsgg.poll();
                nextLayer.sendToNext(pdu);
                endKeeping();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized int getIncomingMsg() {
        return incomingMsg;
    }

    public void setIncomingMsg(int incomingMsg) {
        this.incomingMsg = incomingMsg;
    }

    public int getPendingMsg() {
        return pendingMsg;
    }

    public void setPendingMsg(int pendingMsg) {
        this.pendingMsg = pendingMsg;
    }

    public Queue<byte[]> getPendingMsgg() {
        return pendingMsgg;
    }

    public synchronized void increaseIncomingMsg() {
        this.incomingMsg++;
    }

    public synchronized void decreaseIncomingMsg() {
        this.incomingMsg--;
    }

    public synchronized void receiveMsg(byte[] pdu) {
        this.pendingMsgg.add(pdu);
        this.bscPanel.increaseIncoming();
        increaseIncomingMsg();
    }

    protected synchronized void endKeeping() {
        this.pdu = null;
        this.bscPanel.decreaseIncoming();
        this.bscPanel.increaseSent();
        decreaseIncomingMsg();
        //incrementSentSMSCounter();
    }

    public SendToNextInterface getNextLayer() {
        return nextLayer;
    }

    public synchronized void setNextLayer(SendToNextInterface nextLayer) {
        this.nextLayer = nextLayer;
    }

    public synchronized BscPanel getBscPanel() {
        return bscPanel;
    }

    public void setBscPanel(BscPanel bscPanel) {
        this.bscPanel = bscPanel;
    }

    public boolean isSendAllMsgNow() {
        return sendAllMsgNow;
    }

    public synchronized void setSendAllMsgNow(boolean sendAllMsgNow) {
        this.sendAllMsgNow = sendAllMsgNow;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}

class BtsRightThread extends Thread {
    private int incomingMsg;
    private int pendingMsg;
    private int head;
    private int tail;

    private byte[] pdu;

    private SendToNextInterface nextLayer;
    private boolean working;
    private Queue<byte[]> pendingMsgg;
    private BtsPanelRight btsPanelRight;

    public BtsRightThread(SendToNextInterface nextLayer, BtsPanelRight btsPanelRight) {
        this.btsPanelRight = btsPanelRight;
        working = true;
        this.incomingMsg = 0;
        this.pendingMsg = 0;
        this.head = 0;
        this.tail = 0;
        this.nextLayer = nextLayer;
        pendingMsgg = new Queue<byte[]>() {
            private ArrayList<byte[]> list = new ArrayList<>();

            @Override
            public synchronized boolean add(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized boolean offer(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized byte[] remove() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                byte[] element = list.remove(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] poll() {
                if (isEmpty()) {
                    return null;
                }
                byte[] element = list.get(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] element() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                return list.get(head);
            }

            @Override
            public synchronized byte[] peek() {
                if (isEmpty()) {
                    return null;
                }
                return list.get(head);
            }

            @Override
            public synchronized int size() {
                return tail - head;
            }

            @Override
            public synchronized boolean isEmpty() {
                return head == tail;
            }

            @Override
            public synchronized boolean contains(Object o) {
                return list.contains(o);
            }

            @Override
            public synchronized Iterator<byte[]> iterator() {
                return list.iterator();
            }

            @Override
            public synchronized Object[] toArray() {
                return list.toArray();
            }

            @Override
            public synchronized <T> T[] toArray(T[] a) {
                return list.toArray(a);
            }

            @Override
            public synchronized boolean remove(Object o) {
                return list.remove(o);
            }

            @Override
            public synchronized boolean containsAll(Collection<?> c) {
                return list.containsAll(c);
            }

            @Override
            public synchronized boolean addAll(Collection<? extends byte[]> c) {
                if (c.isEmpty()) {
                    return false;
                }
                for (byte[] element : c) {
                    list.add(element);
                }
                tail += c.size();
                return true;
            }

            @Override
            public synchronized boolean removeAll(Collection<?> c) {
                return list.removeAll(c);
            }

            @Override
            public synchronized boolean retainAll(Collection<?> c) {
                return list.retainAll(c);
            }

            @Override
            public synchronized void clear() {
                list.clear();
                head = 0;
                tail = 0;
            }
        };
    }

    @Override
    public void run() {
        while (working) {
            if (!pendingMsgg.isEmpty()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.pdu = pendingMsgg.poll();
                nextLayer.sendToNext(pdu);
                endKeeping();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized int getIncomingMsg() {
        return incomingMsg;
    }

    public void setIncomingMsg(int incomingMsg) {
        this.incomingMsg = incomingMsg;
    }

    public int getPendingMsg() {
        return pendingMsg;
    }

    public void setPendingMsg(int pendingMsg) {
        this.pendingMsg = pendingMsg;
    }

    public Queue<byte[]> getPendingMsgg() {
        return pendingMsgg;
    }

    public synchronized void increaseIncomingMsg() {
        this.incomingMsg++;
    }

    public synchronized void decreaseIncomingMsg() {
        this.incomingMsg--;
    }


    public void receiveMsg(byte[] pdu) {
        this.pendingMsgg.add(pdu);
        this.btsPanelRight.increaseIncoming();
        increaseIncomingMsg();
    }

    protected synchronized void endKeeping() {
        this.pdu = null;
        this.btsPanelRight.decreaseIncoming();
        this.btsPanelRight.increaseSent();
        decreaseIncomingMsg();
    }
}

class VRDThread extends MyDeviceThread implements ButtonEndListener {
    //todo: prwadopodobnie jest to klasa do usuniecia, ktorej nie potrzebujemy
    private int incomingMsg;
    private int pendingMsg;
    private int head;
    private int tail;

    private byte[] pdu;

    private boolean working;
    private Queue<byte[]> pendingMsgg;
    private VRDPanel vrdPanel;

    public VRDThread(VRDPanel vrdPanel) {
        this.vrdPanel = vrdPanel;
        working = true;
        this.incomingMsg = 0;
        this.pendingMsg = 0;
        this.head = 0;
        this.tail = 0;
        pendingMsgg = new Queue<byte[]>() {
            private ArrayList<byte[]> list = new ArrayList<>();

            @Override
            public synchronized boolean add(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized boolean offer(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public synchronized byte[] remove() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                byte[] element = list.remove(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] poll() {
                if (isEmpty()) {
                    return null;
                }
                byte[] element = list.get(head);
                head++;
                return element;
            }

            @Override
            public synchronized byte[] element() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                return list.get(head);
            }

            @Override
            public synchronized byte[] peek() {
                if (isEmpty()) {
                    return null;
                }
                return list.get(head);
            }

            @Override
            public synchronized int size() {
                return tail - head;
            }

            @Override
            public synchronized boolean isEmpty() {
                return head == tail;
            }

            @Override
            public synchronized boolean contains(Object o) {
                return list.contains(o);
            }

            @Override
            public synchronized Iterator<byte[]> iterator() {
                return list.iterator();
            }

            @Override
            public synchronized Object[] toArray() {
                return list.toArray();
            }

            @Override
            public synchronized <T> T[] toArray(T[] a) {
                return list.toArray(a);
            }

            @Override
            public synchronized boolean remove(Object o) {
                return list.remove(o);
            }

            @Override
            public synchronized boolean containsAll(Collection<?> c) {
                return list.containsAll(c);
            }

            @Override
            public synchronized boolean addAll(Collection<? extends byte[]> c) {
                if (c.isEmpty()) {
                    return false;
                }
                for (byte[] element : c) {
                    list.add(element);
                }
                tail += c.size();
                return true;
            }

            @Override
            public synchronized boolean removeAll(Collection<?> c) {
                return list.removeAll(c);
            }

            @Override
            public synchronized boolean retainAll(Collection<?> c) {
                return list.retainAll(c);
            }

            @Override
            public synchronized void clear() {
                list.clear();
                head = 0;
                tail = 0;
            }
        };
    }

    @Override
    public void run() {
        while (working) {
            if (!pendingMsgg.isEmpty()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //to nigdzie juz nie wysyla
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized int getIncomingMsg() {
        return incomingMsg;
    }

    public void setIncomingMsg(int incomingMsg) {
        this.incomingMsg = incomingMsg;
    }

    public int getPendingMsg() {
        return pendingMsg;
    }

    public void setPendingMsg(int pendingMsg) {
        this.pendingMsg = pendingMsg;
    }

    public Queue<byte[]> getPendingMsgg() {
        return pendingMsgg;
    }


    public synchronized void receiveMsg(byte[] pdu) {
        this.pendingMsgg.add(pdu);
        this.vrdPanel.increaseIncoming();
    }

    protected synchronized void endKeeping() {
        this.pdu = null;
        //incrementSentSMSCounter();
    }

    @Override
    public void buttonEnd(ButtonEndEvent evt) {
        //ta klasa jest bez sensu...
    }

}

class MyDeviceThread extends Thread {
//zalamka ... i tak z kazda
    protected static int deviceIndex = 1;

    protected int ID;

    public MyDeviceThread() {
        this.ID = deviceIndex++;
    }
}

class FileManager {


    public FileManager() {

    }

    public static void saveDataToFile(ArrayList<VBDPanel> vbdPanels) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("data.bin")) {
            for (VBDPanel vbd : vbdPanels) {
                int smsSentCounter = vbd.getVbdThread().getSmsSentCounter();
                byte[] pdu = vbd.getVbdThread().getPdu();

                // smsSentCounter jako 4 bajty
                for (int i = 0; i < 4; i++) {
                    byte b = (byte) (smsSentCounter >> (24 - i * 8));
                    fileOutputStream.write(b);
                }
                // każdy bajt pdu
                for (byte b : pdu) {
                    fileOutputStream.write(b);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class NoReciverException extends Exception {

}