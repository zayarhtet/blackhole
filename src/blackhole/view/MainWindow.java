package blackhole.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends BaseWindow {
    private List<BlackHoleWindow> gameWindows = new ArrayList<>();

    public MainWindow(final int [] boards) {
        setSize(400, 350);
        getContentPane().setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("BLACK HOLE v1.0");
        titlePanel.add(title);
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        JPanel menuPanel = new JPanel();
        for (int i : boards) {
            JButton button = new JButton(i + " x " + i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetBoard(i);
                }
            });
            menuPanel.add(button);
        }
        getContentPane().add(menuPanel,BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menuList = new JMenu("Options");
        menuBar.add(menuList);

        JMenuItem savedGame = new JMenuItem("Load game...");
        menuList.add(savedGame);
        savedGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Under development and will include in v2.0");
            }
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        menuList.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }
    public void resetBoard(final int size) {
        BlackHoleWindow window = new BlackHoleWindow(size, MainWindow.this);
        window.setVisible(true);
    }
    public List<BlackHoleWindow> getWindowList() {
        return gameWindows;
    }  
    @Override
    protected void doUponExit() {
        System.exit(0);
    }
}
