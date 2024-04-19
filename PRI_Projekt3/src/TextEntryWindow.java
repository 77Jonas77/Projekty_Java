import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class TextEntryWindow extends JFrame {

    private JTextArea textArea;
    private JButton submitButton;

    public TextEntryWindow(FileManager fileManager, NeuralNetwork neuralNetwork) {
        setTitle("Entry text window");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String text = textArea.getText();
            String input_vector = text.trim();

            String[] text_splitted = input_vector.split(" ");
            RowData data = fileManager.parseToRowDataFromText(text_splitted);
            String classificationResult = neuralNetwork.classifyWithResponse(data);

            JOptionPane.showMessageDialog(this, "Language: " + classificationResult);
        });
        mainPanel.add(submitButton, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }
}
