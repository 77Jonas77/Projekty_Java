package Ex01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public
class Main
        extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main(){
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.GREEN);
        leftPanel.setPreferredSize( new Dimension( 300, 300));
        this.getContentPane().add(leftPanel, BorderLayout.LINE_START);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.RED);
        rightPanel.setPreferredSize( new Dimension( 300, 300));
        this.getContentPane().add(rightPanel, BorderLayout.LINE_END);

        JButton jButton = new JButton("click me");
        leftPanel.add(jButton);

        jButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setBackground(
                                new Color(
                                        (int)(Math.random()*255),
                                        (int)(Math.random()*255),
                                        (int)(Math.random()*255)
                                )
                        );
                    }
                }
        );

        this.setSize( 640, 480);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
