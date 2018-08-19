package myBallGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ResultView {

    private JFrame frame;
    private JLabel userLabel;
    private JButton exitButton;

    public ResultView(String result) {
        frame = new JFrame("Result");
        frame.setSize(350, 125);
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel(result);
        userLabel.setBounds(20, 10, 300, 25);
        panel.add(userLabel);

        exitButton = new JButton("Exit");
        exitButton.setBounds(210, 80, 80, 25);
        panel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        frame.setVisible(true);
    }


    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
