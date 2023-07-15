package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private char[][] cells;

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
