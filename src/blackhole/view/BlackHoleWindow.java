package blackhole.view;

import javax.swing.*;
import java.awt.*;
import blackhole.model.Board;
import blackhole.model.Cell;

public class BlackHoleWindow extends BaseWindow{
    private final int size;
    private Board board;
    private final JLabel selectedLabel;
    private final JLabel playerTurnLabel;
    private final JLabel selectedCellAddressLabel;
    private final JLabel playerXScoreLabel;
    private final JLabel playerOScoreLabel;
    private JPanel mainPanel;

    private final MainWindow mainWindow;

    public BlackHoleWindow(int size, MainWindow mainWindow) {
        this.size = size;
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        board = new Board(size);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3,3));
        selectedLabel = new JLabel();
        playerTurnLabel = new JLabel(); selectedCellAddressLabel = new JLabel();
        playerOScoreLabel = new JLabel(); playerXScoreLabel = new JLabel();
        updateLabelText();

        topPanel.add(playerOScoreLabel); topPanel.add(playerXScoreLabel);
        topPanel.add(selectedLabel); topPanel.add(selectedCellAddressLabel);
        topPanel.add(playerTurnLabel);

        mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(size,size));

        for (int i = 0; i < size; i++) {
            for (int j =0; j< size; j++) {
                addCell(mainPanel, i, j);
            }
        }
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private void addCell(JPanel mainPanel, int i, int j) {
        final JButton cell = new JButton();
        System.out.println(board.getCell(i,j));
        cell.setText(board.getCell(i,j) == Cell.NOBODY? "" : board.getCell(i,j).toString());
        board.addButton(i, j, cell);
        cell.addActionListener(e -> {
            Cell c = board.clicked(i,j);
            cell.setText(c == Cell.NOBODY? "" : c.name());
            updateLabelText();
            Cell winner = board.findWinner();
            if (winner != Cell.NOBODY) showGameOverMessageAndReset(winner);
        });

        mainPanel.add(cell);
    }

    private void showGameOverMessageAndReset(Cell winner) {
        JOptionPane.showMessageDialog(this,
                "Game is over. Winner: " + winner.name());
        mainWindow.resetBoard(size);
        doUponExit();
    }

    private void updateLabelText() {
        selectedLabel.setText("Selected Cell Data: "+ board.getSelectedCellData());
        playerTurnLabel.setText("Player turn: " + board.getActualPlayer());
        selectedCellAddressLabel.setText("Selected Cell Address: "+ board.getSelectedCellAddress());
        playerXScoreLabel.setText("Player X: "+ board.getPlayerXScore());
        playerOScoreLabel.setText("Player O: " + board.getPlayerOScore());
    }
    @Override
    protected void doUponExit() {
        super.doUponExit();
        mainWindow.getWindowList().remove(this);
    }
}
