package GUI_Sections;

import Interfeces.SendToNextInterface;
import Layers.Layer;
import Panels.VRDPanel;
import Threads.BtsLeftThread;
import Threads.NoReciverException;
import Threads.VBDThread;
import Threads.VRDThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import static Threads.VBDThread.readPduNumber;

public class RightPanel extends Layer implements SendToNextInterface {

    private JPanel scrollPanel;
    public static ArrayList<VRDThread> VRDPanelsList;
    public static ArrayList<VRDPanel> reciversList;
    public RightPanel() {
        reciversList= new ArrayList<>();
        VRDPanelsList=new ArrayList<VRDThread>();
        this.setPreferredSize(new Dimension(300, this.getHeight()));
        this.setLayout(new BorderLayout());

        this.scrollPanel = new JPanel();
        this.scrollPanel.setLayout(new BoxLayout(this.scrollPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(this.scrollPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            VRDPanel vrd = new VRDPanel(); //to jest watek, ktory ma sie uruchomic
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

    //todo: cos sie walilo po dodaniu nowego vrd, ze nie ma takiego odbiorcy czy cos? --> aktualizacja odbiorcow w reciversList w vbd
    @Override
    public synchronized void sendToNext(byte[] pdu) {
        //wybranie bts o najmniejszej liczbie przychodzacych wiadmosci
        for (VRDPanel vrdPanel : reciversList) {
            if (vrdPanel.getID() == readPduNumber(pdu)){
                vrdPanel.increaseIncoming();
            }else{
                try {
                    throw new NoReciverException();
                } catch (NoReciverException e) {
                    System.out.println("Nie ma takiego odbiorcy");
                }
            }
        }
    }

    public ArrayList<VRDPanel> getReciversList() {
        return reciversList;
    }
}
