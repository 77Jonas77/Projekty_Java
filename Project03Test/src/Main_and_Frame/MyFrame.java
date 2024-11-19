package Main_and_Frame;

import Events.WindowClosingEvent;
import GUI_Sections.LeftPanel;
import GUI_Sections.MiddlePanel;
import GUI_Sections.RightPanel;
import Interfeces.ButtonEndListener;
import Interfeces.WindowClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MyFrame {
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
        frame.setSize(1260,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fireWindowClosing(true);
            }
        });
    }

    private void fireWindowClosing(boolean evt){
        for(WindowClosingListener listener: listeners){
            listener.windowClosing(new WindowClosingEvent(this,evt));
        }
    }
}
