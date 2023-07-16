package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {

    private final int SIZE;
    private final char[][] cells;

    private Field foggedField;

    public Field(int size) {
        this.SIZE = size;
        this.cells = startEmptyField();
    }

    public Field getFoggedField() {
        if (foggedField == null) {
            foggedField = new Field(SIZE);
        }
        return foggedField;
    }

    private char[][] startEmptyField() {
        char[][] cells = new char[SIZE][SIZE];
        for (char[] row: cells) {
            Arrays.fill(row, '.');
        }
        return cells;
    }

    public void addMines(int numOfMines) {
        Random random = new Random();
        int i = 0;
        while (i < numOfMines) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            if (cells[x][y] == '.') {
                cells[x][y] = 'X';
                i++;
            }
        }
        addNumberOfMinesAroundCells();
        replicateCluesToFoggedField();
    }

    private void replicateCluesToFoggedField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!isMine(i, j)) {
                    getFoggedField().cells[i][j] = cells[i][j];
                }
            }
        }
    }

    private void addNumberOfMinesAroundCells() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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
                    if (j < SIZE - 1 && isMine(i - 1, j + 1)) {
                        addNumber(i, j);
                    }
                }
                // check south
                if (i < SIZE - 1) {
                    if (isMine(i + 1, j)){
                        addNumber(i, j);
                    }
                    // check southwest
                    if (j > 0 && isMine(i + 1, j - 1)) {
                        addNumber(i, j);
                    }
                    // check southeast
                    if (j < SIZE - 1 && isMine(i + 1, j + 1)) {
                        addNumber(i, j);
                    }
                }
                // check west
                if (j > 0 && isMine(i, j - 1)) {
                    addNumber(i, j);
                }
                // check east
                if (j < SIZE - 1 && isMine(i, j + 1)) {
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
        StringBuilder result = new StringBuilder(" |123456789|");
        result.append("\n").append("-|---------|").append("\n");
        int i = 1;
        for (char[] row: cells) {
            result.append(i).append("|");
            for (char c: row) {
                result.append(c);
            }
            result.append("|").append("\n");
            i++;
        }
        result.append("-|---------|");
        return result.toString();
    }
}
