package blackhole.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class BaseWindow extends JFrame {
    public BaseWindow() {
        setTitle("BlackHole GUI Game");
        setSize(500, 550);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });
        URL url = BlackHoleWindow.class.getResource("../resources/black-hole-icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }

    private void showExitConfirmation() {
        int n = JOptionPane.showConfirmDialog(this, "Do you really want to quit?",
                "Really?", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }

    protected void doUponExit() {
        this.dispose();
    }
}
