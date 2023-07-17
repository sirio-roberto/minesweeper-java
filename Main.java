package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int side = 9;
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many mines do you want on the field? ");
        int numOfMines = Integer.parseInt(scanner.nextLine());
        System.out.println();

        Field field = new Field(side, numOfMines);
        field.addMines();
        System.out.println(field.getFoggedField());

        while (!field.isGameOver()) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            String[] userInput = scanner.nextLine().split(" ");
            if (isInvalidInput(userInput, side)) {
                continue;
            }
            int x = Integer.parseInt(userInput[0]);
            int y = Integer.parseInt(userInput[1]);
            String action = userInput[2];
            System.out.println();

            if ("free".equals(action)) {
                field.exploreCell(x, y, true);
            } else {
                field.markCell(x, y, true);
            }

            System.out.println(field.getFoggedField());
        }
    }

    private static boolean isInvalidInput(String[] userInput, int side) {
        if (userInput.length != 3) {
            System.out.println("User input should contain 3 fields (x, y and action)");
            return true;
        }
        if (!userInput[0].matches("\\d+") || !userInput[1].matches("\\d+")) {
            System.out.println("The coordinates should be numeric values");
            return true;
        }
        int x = Integer.parseInt(userInput[0]);
        int y = Integer.parseInt(userInput[1]);
        if (x < 1 || y < 1 || x >= side || y >= side) {
            System.out.println("Coordinates should be between allowed range");
            return true;
        }
        if (!("free".equals(userInput[2]) || "mine".equals(userInput[2]))) {
            System.out.println("Action should be valid: free or mine");
            return true;
        }
        return false;
    }
}
