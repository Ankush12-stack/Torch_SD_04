import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class sudoku extends JFrame {
    private JTextField[][] sudokuGrid;

    public sudoku() {
        setTitle("Sudoku Solver");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sudokuPanel = new JPanel(new GridLayout(9, 9));
        sudokuGrid = new JTextField[9][9];

        // Create text fields for Sudoku grid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuGrid[i][j] = new JTextField(1);
                sudokuGrid[i][j].setHorizontalAlignment(JTextField.CENTER);
                sudokuPanel.add(sudokuGrid[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel();
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        buttonPanel.add(solveButton);

        add(sudokuPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void solveSudoku() {
        int[][] puzzle = new int[9][9];

        // Parse the Sudoku puzzle from the text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = sudokuGrid[i][j].getText();
                if (!value.isEmpty()) {
                    puzzle[i][j] = Integer.parseInt(value);
                } else {
                    puzzle[i][j] = 0;
                }
            }
        }

        // Call Sudoku solver
        if (solve(puzzle)) {
            // Update the text fields with the solved puzzle
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuGrid[i][j].setText(Integer.toString(puzzle[i][j]));
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists for this Sudoku puzzle.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean solve(int[][] puzzle) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidMove(puzzle, row, col, num)) {
                            puzzle[row][col] = num;

                            if (solve(puzzle)) {
                                return true;
                            } else {
                                puzzle[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int[][] puzzle, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (puzzle[row][i] == num || puzzle[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 grid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (puzzle[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new sudoku().setVisible(true);
            }
        });
    }
}

