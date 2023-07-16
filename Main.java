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

        while (!field.areAllMinesMarked()) {
            int x;
            int y;
            do {
                System.out.print("Set/delete mines marks (x and y coordinates): ");
                String[] userCoordinates = scanner.nextLine().split(" ");
                x = Integer.parseInt(userCoordinates[0]);
                y = Integer.parseInt(userCoordinates[1]);
                if (field.isNumber(x, y, true)) {
                    System.out.println("There is a number here!");
                }
            } while (field.isNumber(x, y, true));

            System.out.println();
            field.markCell(x, y, true);
            System.out.println(field.getFoggedField());
        }
        System.out.println("Congratulations! You found all the mines!");
    }
}
