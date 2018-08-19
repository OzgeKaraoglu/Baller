package myBallGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuView {

    private JFrame frame;
    private JLabel userLabel;
    private JTextField userText;
    private JLabel ipAddress;
    private JTextField ipAddressText;
    private JButton startServerButton;
    private JButton startClientButton;
    private JButton exitButton;

    public MenuView(ActionListener controller) {
        frame = new JFrame("MenuView");
        frame.setSize(800, 600);
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel("Username: ");
        userLabel.setBounds(20, 10, 140, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(150, 10, 160, 25);
        panel.add(userText);

        ipAddress = new JLabel("IP Address: ");
        ipAddress.setBounds(20, 45, 140, 25);
        panel.add(ipAddress);

        ipAddressText = new JTextField(20);
        ipAddressText.setBounds(150, 45, 160, 25);
        panel.add(ipAddressText);

        startServerButton = new JButton("Server");
        startServerButton.setBounds(10, 80, 80, 25);
        panel.add(startServerButton);

        startClientButton = new JButton("Client");
        startClientButton.setBounds(110, 80, 80, 25);
        panel.add(startClientButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(210, 80, 80, 25);
        panel.add(exitButton);

        exitButton.addActionListener(controller);
        startServerButton.addActionListener(controller);
        startClientButton.addActionListener(controller);
        frame.setVisible(true);
    }

    public String getUserName() {
        return userText.getText();
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public String getRemoteAddress() {
        return ipAddressText.getText();
    }
}
