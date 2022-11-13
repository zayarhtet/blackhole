package blackhole.model;

import java.awt.Color;

import javax.swing.JButton;

public class Board {
    private final int size;
    private final Cell[][] board;
    private final JButton[][] buttons;
    private Cell cellToShift = Cell.NOBODY;
    private int cellRowToShift = -1;
    private int cellColToShift = -1;
    private int playerXScore = 0;
    private int playerOScore = 0;

    private Cell actualPlayer = Cell.X;

    public Board(int size) {
        this.size = size;
        board = new Cell[size][size];
        buttons = new JButton[size][size];

        for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) board[i][j] = Cell.NOBODY;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if ( i < size/2 && j < size/2 && i == j) {
                    board[i][j] = Cell.X;
                    board[i][size-i-1] = Cell.X;
                } else if (i > size/2 && j > size/2 && i == j) {
                    board[i][j] = Cell.O;
                    board[i][size-i-1] = Cell.O;
                } else if (i == size/2 && j == size/2) board[i][j] = Cell.BH;

    }

    public void addButton(int i, int j, JButton button) {
        buttons[i][j] = button;
    }

    public Cell clicked(int i, int j) {
        if (board[i][j] != Cell.NOBODY && board[i][j] != actualPlayer && board[i][j] != Cell.BH) {
            return board[i][j];
        }
        if (i == cellRowToShift && j == cellColToShift) {
            board[i][j] = cellToShift;
            buttons[i][j].setBackground(null);
            cellToShift = Cell.NOBODY;
            cellRowToShift = -1;
            cellColToShift = -1;
            return board[i][j];
        }
        if (board[i][j] == Cell.X || board[i][j] == Cell.O) {
            if (cellToShift == Cell.NOBODY) {
                buttons[i][j].setBackground(Color.GREEN);
                cellToShift = board[i][j];
                cellRowToShift = i; cellColToShift = j;
            }
        } else if (board[i][j] == Cell.NOBODY || board[i][j] == Cell.BH) {
            if (cellToShift != Cell.NOBODY) {
                if (!makeCellShiftTillTheEnd(i,j)) return board[i][j];
                buttons[cellRowToShift][cellColToShift].setBackground(null);
                board[cellRowToShift][cellColToShift] = Cell.NOBODY;
                buttons[cellRowToShift][cellColToShift].setText("");

                if (cellRowToShift != i || cellColToShift != j) {
                    if (actualPlayer == Cell.X) actualPlayer = Cell.O;
                    else actualPlayer = Cell.X;
                }
                cellToShift = Cell.NOBODY; cellColToShift = -1; cellRowToShift = -1;
            }
        }
        return board[i][j];
    }
    public boolean makeCellShiftTillTheEnd(int i, int j) {
        //row shift
        int highestColIndex = size-1; int lowestColIndex = 0;
        for (int k = cellColToShift+1; k < size; k++) {
            if (board[cellRowToShift][k] != Cell.NOBODY) {
                highestColIndex = board[cellRowToShift][k] == Cell.BH?k:k-1;
                break;
            }
        }
        for (int k = cellColToShift-1; k >= 0; k--) {
            if (board[cellRowToShift][k] != Cell.NOBODY) {
                lowestColIndex = board[cellRowToShift][k] == Cell.BH? k:k+1;
                break;
            }
        }
        
        //col shift
        int highestRowIndex = size-1; int lowestRowIndex = 0;
        for (int k = cellRowToShift+1; k < size; k++) {
            if (board[k][cellColToShift] != Cell.NOBODY) {
                highestRowIndex = board[k][cellColToShift] == Cell.BH? k:k-1;
                break;
            }
        }
        for (int k = cellRowToShift-1; k >= 0; k--) {
            if (board[k][cellColToShift] != Cell.NOBODY) {
                lowestRowIndex = board[k][cellColToShift] == Cell.BH? k:k+1;
                break;
            }
        }

        if (j <= cellColToShift && i == cellRowToShift) {
            if (board[cellRowToShift][lowestColIndex] == Cell.BH) resetRecord();
            else if (board[cellRowToShift][lowestColIndex] == Cell.X || board[cellRowToShift][lowestColIndex] == Cell.O) return false;
            else {
                board[cellRowToShift][lowestColIndex] = cellToShift;
                buttons[cellRowToShift][lowestColIndex].setText(cellToShift.toString());
            }
            return true;
        } else if (j >= cellColToShift && i == cellRowToShift) {   //right shift
            if (board[cellRowToShift][highestColIndex] == Cell.BH) resetRecord();
            else if (board[cellRowToShift][highestColIndex] == Cell.X || board[cellRowToShift][highestColIndex] == Cell.O) return false;
            else {
                board[cellRowToShift][highestColIndex] = cellToShift;
                buttons[cellRowToShift][highestColIndex].setText(cellToShift.toString());
            }
            return true;
        } else if (i <= cellRowToShift && j == cellColToShift) {   //up shift
            if (board[lowestRowIndex][cellColToShift] == Cell.BH) resetRecord();
            else if (board[lowestRowIndex][cellColToShift] == Cell.X || board[lowestRowIndex][cellColToShift] == Cell.O) return false;
            else {
                board[lowestRowIndex][cellColToShift] = cellToShift;
                buttons[lowestRowIndex][cellColToShift].setText(cellToShift.toString());
            }
            return true;
        } else if (i >= cellRowToShift && j == cellColToShift) {   //down shift
            if (board[highestRowIndex][cellColToShift] == Cell.BH) resetRecord();
            else if (board[highestRowIndex][cellColToShift] == Cell.X || board[highestRowIndex][cellColToShift] == Cell.O) return false;
            else {
                board[highestRowIndex][cellColToShift] = cellToShift;
                buttons[highestRowIndex][cellColToShift].setText(cellToShift.toString());
            }
            return true;
        }
        return false;
    }
    private void resetRecord() {
        if (cellToShift == Cell.X) playerXScore++;
        else playerOScore++;
    }

    // private boolean validCellShift(int i, int j) {
    //     // row
    //     int highestColIndex = size-1; int lowestColIndex = 0;
    //     for (int k = cellColToShift+1; k < size; k++) {
    //         if (board[cellRowToShift][k] != Cell.NOBODY) {
    //             highestColIndex = board[cellRowToShift][k] == Cell.BH?k:k-1;
    //             break;
    //         }
    //     }
    //     for (int k = cellColToShift-1; k >= 0; k--) {
    //         if (board[cellRowToShift][k] != Cell.NOBODY) {
    //             lowestColIndex = board[cellRowToShift][k] == Cell.BH? k:k+1;
    //             break;
    //         }
    //     }
    //     // col
    //     int highestRowIndex = size-1; int lowestRowIndex = 0;
    //     for (int k = cellRowToShift+1; k < size; k++) {
    //         if (board[k][cellColToShift] != Cell.NOBODY) {
    //             highestRowIndex = board[k][cellColToShift] == Cell.BH? k:k-1;
    //             break;
    //         }
    //     }
    //     for (int k = cellRowToShift-1; k >= 0; k--) {
    //         if (board[k][cellColToShift] != Cell.NOBODY) {
    //             lowestRowIndex = board[k][cellColToShift] == Cell.BH? k:k+1;
    //             break;
    //         }
    //     }
    //     if (cellRowToShift == i && cellColToShift == j) {
    //         return true;
    //     } else if (cellRowToShift == i && cellColToShift != j) {
    //         if (j >= lowestColIndex && j <= highestColIndex) {
    //             return true;
    //         }
    //     } else if (cellRowToShift != i && cellColToShift == j) {
    //         if (i >= lowestRowIndex && i <= highestRowIndex) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public Cell findWinner() {
        Cell winner = Cell.NOBODY;
        if (cellToShift == Cell.NOBODY) {
            if (actualPlayer == Cell.X) {
                for (int i = 0; i < size; i++) for (int j =0; j < size; j++) if (board[i][j] == Cell.O) return Cell.NOBODY;
                winner = Cell.O;
            } else {
                for (int i = 0; i < size; i++) for (int j =0; j < size; j++) if (board[i][j] == Cell.X) return Cell.NOBODY;
                winner = Cell.X;
            }
        }
        return winner;
    }

    public Cell getCell(int i, int j) {
        return board[i][j];
    }

    public Cell getActualPlayer() {
        return actualPlayer;
    }

    public Cell getSelectedCellData() {
        return cellToShift;
    }

    public String getSelectedCellAddress() {
        return "("+cellRowToShift+ "," + cellColToShift + ")";
    }

    public int getPlayerXScore() {
        return playerXScore;
    }

    public int getPlayerOScore() {
        return playerOScore;
    }

    //     public Cell clicked(int i, int j) {
//         if (board[i][j] != Cell.NOBODY && board[i][j] != actualPlayer && board[i][j] != Cell.BH) {
//             return board[i][j];
//         }
//         if (board[i][j] == Cell.X) {
//             if (cellToShift == Cell.NOBODY) {
//                 cellToShift = Cell.X;
//                 cellRowToShift = i; cellColToShift = j;
//                 board[i][j] = Cell.NOBODY;
//             }
//         } else if (board[i][j] == Cell.O) {
//             if (cellToShift == Cell.NOBODY) {
//                 cellToShift = Cell.O;
//                 cellRowToShift = i; cellColToShift = j;
//                 board[i][j] = Cell.NOBODY;
//             }
//         } else if (board[i][j] == Cell.NOBODY || board[i][j] == Cell.BH) {
//             if (cellToShift != Cell.NOBODY && validCellShift(i,j)) {

//                 if (board[i][j] == Cell.NOBODY) board[i][j] = cellToShift;

//                 if (cellRowToShift != i || cellColToShift != j) {
//                     if (actualPlayer == Cell.X) {
//                         if (board[i][j] == Cell.BH) {
//                             playerXScore++;
//                         }
//                         actualPlayer = Cell.O;
//                     } else {
//                         if (board[i][j] == Cell.BH) playerOScore++;
//                         actualPlayer = Cell.X;
//                     }
//                 }
//                 cellToShift = Cell.NOBODY; cellColToShift = -1; cellRowToShift = -1;
//             }
//         }
// //        System.out.println(board[i][j]);
//         return board[i][j];
//     }


}
