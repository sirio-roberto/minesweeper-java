package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {

    private final int SIZE;
    private final int NUM_OF_MINES;
    private final char[][] cells;
    private Field foggedField;
    private boolean mineExploded = false;

    public Field(int size, int numOfMines) {
        this.SIZE = size;
        this.NUM_OF_MINES = numOfMines;
        this.cells = startEmptyField();
    }

    public Field getFoggedField() {
        if (foggedField == null) {
            foggedField = new Field(SIZE, NUM_OF_MINES);
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

    public void addMines() {
        Random random = new Random();
        int i = 0;
        while (i < NUM_OF_MINES) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            if (cells[x][y] == '.') {
                cells[x][y] = 'X';
                i++;
            }
        }
        addNumberOfMinesAroundCells();
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

    public boolean isNumber(int x, int y, boolean isUserCoordinate) {
        if (isUserCoordinate) {
            int aux = x;
            x = y;
            y = aux;
            x--;
            y--;
        }
        return cells[x][y] >= '1' && cells[x][y] <= '8';
    }

    public void markCell(int x, int y, boolean isUserCoordinate) {
        if (isUserCoordinate) {
            int aux = x;
            x = y;
            y = aux;
            x--;
            y--;
        }

        if (getFoggedField().cells[x][y] == '*') {
            getFoggedField().cells[x][y] = '.';
        } else {
            getFoggedField().cells[x][y] = '*';
        }
    }

    public boolean areAllMinesMarked() {
        int numOfMarks = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isMine(i, j) || getFoggedField().isMarked(i, j)) {
                    if (!isMine(i, j) || !getFoggedField().isMarked(i, j)) {
                        return false;
                    }
                    numOfMarks++;
                }
            }
        }
        return NUM_OF_MINES == numOfMarks;
    }

    private boolean isMarked(int i, int j) {
        return cells[i][j] == '*';
    }

    public boolean isGameOver() {
        if (mineExploded) {
            System.out.println("You stepped on a mine and failed!");
            return true;
        }
        if (areAllMinesMarked() || areAllCellsExplored()) {
            System.out.println("Congratulations! You found all the mines!");
            return true;
        }
        return false;
    }

    private boolean areAllCellsExplored() {
        int unexploredCells = 0;
        for (char[] row: getFoggedField().cells) {
            for (char c: row) {
                if (c == '*' || c == '.') {
                    unexploredCells++;
                    if (unexploredCells > NUM_OF_MINES) {
                        return false;
                    }
                }
            }
        }
        return unexploredCells == NUM_OF_MINES;
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
