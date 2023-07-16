package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many mines do you want on the field? ");
        int numOfMines = Integer.parseInt(scanner.nextLine());
        System.out.println();

        Field field = new Field(9, numOfMines);
        field.addMines();
        System.out.println(field.getFoggedField());

        while (!field.isGameOver()) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            String[] userInput = scanner.nextLine().split(" ");
            int x = Integer.parseInt(userInput[0]);
            int y = Integer.parseInt(userInput[1]);
            String action = userInput[2];
            System.out.println();

            if ("free".equals(action)) {

            } else {
                field.markCell(x, y, true);
            }

            System.out.println(field.getFoggedField());
        }
    }
}
