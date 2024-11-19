package Ex01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanel extends JPanel {

    public JButton JButton;
    public LeftPanel leftPanel;

    public RightPanel(Color color, JButton jbutton, LeftPanel leftPanel) {
        this.setBackground(color);
        this.setPreferredSize( new Dimension( 300, 300));
        this.JButton=jbutton;
        this.leftPanel=leftPanel;
        this.add(jbutton);
        this.JButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        leftPanel.setBackground(
                                new Color(
                                        (int)(Math.random()*255),
                                        (int)(Math.random()*255),
                                        (int)(Math.random()*255)
                                )
                        );
                    }
                }
        );
    }


}
