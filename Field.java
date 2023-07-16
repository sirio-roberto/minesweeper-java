package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private final char[][] cells;

    public Field(int size) {
        this.cells = startEmptyField(size);
    }

    private char[][] startEmptyField(int size) {
        char[][] cells = new char[size][size];
        for (char[] row: cells) {
            Arrays.fill(row, '.');
        }
        return cells;
    }

    public void addMines(int numOfMines) {
        Random random = new Random();
        int i = 0;
        while (i < numOfMines) {
            int x = random.nextInt(cells.length);
            int y = random.nextInt(cells.length);
            if (cells[x][y] == '.') {
                cells[x][y] = 'X';
                i++;
            }
        }
        addNumberOfMinesAroundCells();
    }

    private void addNumberOfMinesAroundCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                // check north
                if (i > 0) {
                    if (isMine(i - 1, j)) {
                        addNumber(i, j);
                    }
                    // check northwest
                    if (j > 0 && isMine(i - 1, j - 1)) {
                        addNumber(i, j);
                    }
                    // check northeast
                    if (j < cells.length - 1 && isMine(i - 1, j + 1)) {
                        addNumber(i, j);
                    }
                }
                // check south
                if (i < cells.length - 1) {
                    if (isMine(i + 1, j)){
                        addNumber(i, j);
                    }
                    // check southwest
                    if (j > 0 && isMine(i + 1, j - 1)) {
                        addNumber(i, j);
                    }
                    // check southeast
                    if (j < cells.length - 1 && isMine(i + 1, j + 1)) {
                        addNumber(i, j);
                    }
                }
                // check west
                if (j > 0 && isMine(i, j - 1)) {
                    addNumber(i, j);
                }
                // check east
                if (j < cells.length - 1 && isMine(i, j + 1)) {
                    addNumber(i, j);
                }
            }
        }
    }

    private void addNumber(int x, int y) {
        if (cells[x][y] == '.') {
            cells[x][y] = '1';
        } else if (!isMine(x, y)){
            cells[x][y]++;
        }
    }

    private boolean isMine(int x, int y) {
        return cells[x][y]  == 'X';
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (char[] row: cells) {
            for (char c: row) {
                result.append(c);
            }
            result.append("\n");
        }
        return result.toString();
    }
}
