package minesweeper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
            return isNumber(y - 1, x - 1);
        }
        return isNumber(x, y);
    }

    public boolean isNumber(int x, int y) {
        return cells[x][y] >= '1' && cells[x][y] <= '8';
    }

    public void markCell(int x, int y, boolean isUserCoordinate) {
        if (isUserCoordinate) {
            markCell(y - 1, x - 1);
        }
        else {
            markCell(x, y);
        }
    }

    public void markCell(int x, int y) {
        if (getFoggedField().cells[x][y] == '*') {
            getFoggedField().cells[x][y] = '.';
        } else {
            getFoggedField().cells[x][y] = '*';
        }
    }

    public void exploreCell(int x, int y, boolean isUserCoordinate) {
        if (isUserCoordinate) {
            exploreCell(y - 1, x - 1);
        } else {
            exploreCell(x, y);
        }
    }

    private void exploreCell(int x, int y) {
        if (isNumber(x, y)) {
            getFoggedField().cells[x][y] = cells[x][y];
        } else if (isMine(x, y)) {
            mineExploded = true;
            copyAllMinesToFogged();
        } else {
            exploreAndExpand(x, y);
        }
    }

    private void exploreAndExpand(int x, int y) {
        Set<String> setCoordinates = new HashSet<>();

        exploreRecursively(x, y, setCoordinates);

        for (String coordinateStr: setCoordinates) {
            String[] coordinateArray = coordinateStr.split(" ");
            int i = Integer.parseInt(coordinateArray[0]);
            int j = Integer.parseInt(coordinateArray[1]);
            getFoggedField().cells[i][j] = isNumber(i, j) ? cells[i][j] : '/';
        }
    }

    private void exploreRecursively(int i, int j, Set<String> setCoordinates) {
        setCoordinates.add(convertToSetCoordinate(i, j));
        if (isNumber(i, j)) {
            return;
        }
        // check north
        if (i > 0) {
            if (isEmptyOrNumber(i - 1, j) && isUnexplored(i - 1, j)) {
                String setCoordinate = convertToSetCoordinate(i - 1, j);
                if (!setCoordinates.contains(setCoordinate)) {
                    exploreRecursively(i - 1, j, setCoordinates);
                }
            }
            // check northwest
            if (j > 0 && isEmptyOrNumber(i - 1, j - 1) && isUnexplored(i - 1, j - 1)) {
                String setCoordinate = convertToSetCoordinate(i - 1, j - 1);
                if (!setCoordinates.contains(setCoordinate)) {
                    exploreRecursively(i - 1, j - 1, setCoordinates);
                }
            }
            // check northeast
            if (j < SIZE - 1 && isEmptyOrNumber(i - 1, j + 1) && isUnexplored(i - 1, j + 1)) {
                String setCoordinate = convertToSetCoordinate(i - 1, j + 1);
                if (!setCoordinates.contains(setCoordinate)) {
                    exploreRecursively(i - 1, j + 1, setCoordinates);
                }
            }
        }
        // check south
        if (i < SIZE - 1) {
            if (isEmptyOrNumber(i + 1, j) && isUnexplored(i + 1, j)){
                String setCoordinate = convertToSetCoordinate(i + 1, j);
                if (!setCoordinates.contains(setCoordinate)) {
                    exploreRecursively(i + 1, j, setCoordinates);
                }
            }
            // check southwest
            if (j > 0 && isEmptyOrNumber(i + 1, j - 1) && isUnexplored(i + 1, j - 1)) {
                String setCoordinate = convertToSetCoordinate(i + 1, j - 1);
                if (!setCoordinates.contains(setCoordinate)) {
                    exploreRecursively(i + 1, j - 1, setCoordinates);
                }
            }
            // check southeast
            if (j < SIZE - 1 && isEmptyOrNumber(i + 1, j + 1) && isUnexplored(i + 1, j + 1)) {
                String setCoordinate = convertToSetCoordinate(i + 1, j + 1);
                if (!setCoordinates.contains(setCoordinate)) {
                    exploreRecursively(i + 1, j + 1, setCoordinates);
                }
            }
        }
        // check west
        if (j > 0 && isEmptyOrNumber(i, j - 1) && isUnexplored(i, j - 1)) {
            String setCoordinate = convertToSetCoordinate(i, j - 1);
            if (!setCoordinates.contains(setCoordinate)) {
                exploreRecursively(i, j - 1, setCoordinates);
            }
        }
        // check east
        if (j < SIZE - 1 && isEmptyOrNumber(i, j + 1) && isUnexplored(i, j + 1)) {
            String setCoordinate = convertToSetCoordinate(i, j + 1);
            if (!setCoordinates.contains(setCoordinate)) {
                exploreRecursively(i, j + 1, setCoordinates);
            }
        }
    }

    private boolean isEmptyOrNumber(int i, int j) {
        return isEmptyCell(i, j) || isNumber(i, j);
    }

    private boolean isUnexplored(int i, int j) {
        return getFoggedField().cells[i][j] != '/';
    }

    private boolean isEmptyCell(int i, int j) {
        return cells[i][j] == '.';
    }

    private String convertToSetCoordinate(int x, int y) {
        return x + " " + y;
    }

    private void copyAllMinesToFogged() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isMine(i, j)) {
                    getFoggedField().cells[i][j] = cells[i][j];
                }
            }
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
