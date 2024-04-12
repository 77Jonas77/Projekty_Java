import javax.swing.*;
import java.awt.*;

public class TextEntryWindow extends JFrame {

    private JTextField textField;
    private JButton submitButton;

    public TextEntryWindow(FileManager fileManager, NeuralNetwork neuralNetwork) {
        setTitle("Entry text window");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        textField = new JTextField();
        mainPanel.add(textField, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String text = textField.getText();
            String input_vector = text.toString().trim();

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
