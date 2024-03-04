import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class TicTacToeFrame extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private char currentPlayer;
    private Timer computerTimer;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        computerTimer = new Timer(1000, this::computerMove);

        initializeButtons();
    }

    private void initializeButtons() {
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (clickedButton.getText().equals("")) {
            clickedButton.setText(Character.toString(currentPlayer));
            if (checkForWinner()) {
                int option = JOptionPane.showConfirmDialog(this, "Player " + currentPlayer + " wins. Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    resetBoard();
                } else {
                    System.exit(0);
                }
            } else if (isBoardFull()) {
                int option = JOptionPane.showConfirmDialog(this, "It's a tie. Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    resetBoard();
                } else {
                    System.exit(0);
                }
            } else {
                if (currentPlayer == 'X') {
                    switchPlayer();
                    computerTimer.start();
                } else {
                    switchPlayer();
                }
            }
        }
    }

    private void computerMove(ActionEvent e) {
        computerTimer.stop();
        makeRandomMove();

        if (checkForWinner()) {
            int option = JOptionPane.showConfirmDialog(this, "Player O (Computer) wins. Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                resetBoard();
            } else {
                System.exit(0);
            }
        } else if (isBoardFull()) {
            int option = JOptionPane.showConfirmDialog(this, "It's a tie. Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                resetBoard();
            } else {
                System.exit(0);
            }
        }

        switchPlayer();
    }

    private void makeRandomMove() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));

        buttons[row][col].setText("O");
    }

    private boolean checkForWinner() {
        for (int i = 0; i < 3; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }

        return checkDiagonals();
    }

    private boolean checkRow(int row) {
        return buttons[row][0].getText().equals(Character.toString(currentPlayer)) &&
                buttons[row][1].getText().equals(Character.toString(currentPlayer)) &&
                buttons[row][2].getText().equals(Character.toString(currentPlayer));
    }

    private boolean checkColumn(int col) {
        return buttons[0][col].getText().equals(Character.toString(currentPlayer)) &&
                buttons[1][col].getText().equals(Character.toString(currentPlayer)) &&
                buttons[2][col].getText().equals(Character.toString(currentPlayer));
    }

    private boolean checkDiagonals() {
        return (buttons[0][0].getText().equals(Character.toString(currentPlayer)) &&
                buttons[1][1].getText().equals(Character.toString(currentPlayer)) &&
                buttons[2][2].getText().equals(Character.toString(currentPlayer))) ||
                (buttons[0][2].getText().equals(Character.toString(currentPlayer)) &&
                        buttons[1][1].getText().equals(Character.toString(currentPlayer)) &&
                        buttons[2][0].getText().equals(Character.toString(currentPlayer)));
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeFrame ticTacToeFrame = new TicTacToeFrame();
            ticTacToeFrame.setVisible(true);
        });
    }
}
