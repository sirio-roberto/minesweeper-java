package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many mines do you want on the field? ");
        int numOfMines = Integer.parseInt(scanner.nextLine());

        Field field = new Field(9);
        field.addMines(numOfMines);
        System.out.println(field);
    }
}
