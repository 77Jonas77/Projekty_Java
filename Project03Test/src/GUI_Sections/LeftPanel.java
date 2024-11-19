package GUI_Sections;

import Events.WindowClosingEvent;
import Interfeces.WindowClosingListener;
import Layers.BscColumnLayer;
import Layers.BtsLayerLeft;
import Layers.BtsLayerRight;
import Layers.Layer;
import Panels.VBDPanel;
import Panels.VRDPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class LeftPanel extends Layer implements WindowClosingListener {

    private JPanel scrollPanel;
    private ArrayList<VBDPanel> VBDPanelList;
    public LeftPanel(BtsLayerLeft btsLayerLeft, ArrayList<VRDPanel> reciversList) {
        VBDPanelList = new ArrayList<>();
        this.setPreferredSize(new Dimension(300,this.getHeight()));
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
                if(msg!=null && !msg.isEmpty()){
                    VBDPanel vbd = new VBDPanel(msg,btsLayerLeft); //to jest watek, ktory ma sie uruchomic
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

    private void addVBD(VBDPanel vbdPanel){
        this.scrollPanel.add(vbdPanel);
        VBDPanelList.add(vbdPanel);
    }

    @Override
    public void windowClosing(WindowClosingEvent evt) {
        if(evt.isClosed()){
            FileManager.saveDataToFile(VBDPanelList);
        }
    }
}

