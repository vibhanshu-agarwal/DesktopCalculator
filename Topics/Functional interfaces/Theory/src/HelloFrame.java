import javax.swing.*;
import java.awt.*;

public class HelloFrame extends JFrame {
    public HelloFrame() {
        super("Hello, App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Your Name");
        nameLabel.setBounds(40, 20, 100, 30);
        add(nameLabel);

        JTextField nameTextField = new JTextField();
        nameTextField.setBounds(160, 20, 100, 30);
        add(nameTextField);

        JPanel greenPanel = new JPanel();
        greenPanel.setBounds(40, 150, 220, 70);
        greenPanel.setLayout(new BorderLayout());
        greenPanel.setBackground(Color.GREEN);
        add(greenPanel);

        JLabel helloLabel = new JLabel("Hello");
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helloLabel.setVerticalAlignment(SwingConstants.CENTER);

        Font font = new Font("Courier", Font.BOLD, 12);
        helloLabel.setFont(font);
        helloLabel.setFont(helloLabel.getFont().deriveFont(16f));

        greenPanel.add(helloLabel);

        JButton acceptButton = new JButton("Accept");
        acceptButton.setBounds(100, 70, 100, 30);
        add(acceptButton);

        acceptButton.addActionListener(e -> {
            String helloText = "Hello";
            String name = nameTextField.getText();
            if(name != null && !name.trim().isEmpty()) {
                helloText +=  String.format(", %s", name);
            }
            helloLabel.setText(helloText);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new HelloFrame();
    }

}
