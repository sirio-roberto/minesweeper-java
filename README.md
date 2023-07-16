# Minesweeper Game

This is a simple implementation of the classic Minesweeper game in Java.

## How to Play

1. Run the `Main` class in your preferred Java IDE or execute the compiled program from the command line.
2. Upon starting the game, you'll be prompted to enter the number of mines you want on the game field.
3. The game field will be displayed, initially covered with fogged cells denoted by dots (`.`).
4. Enter your actions by specifying the coordinates and action separated by spaces. Actions can be either "free" to uncover a cell or "mine" to mark/unmark a suspected mine.
5. Follow the prompts and enter the desired actions until the game is over.
6. The game ends when you either step on a mine and fail or when you successfully uncover all safe cells and mark all mines.

## Rules

- The game field is represented by a square grid of cells.
- Mines are randomly distributed across the field.
- Each cell can be in one of the following states:
  - Fogged (covered with dots)
  - Uncovered and empty (denoted by a number indicating the number of adjacent mines)
  - Uncovered and containing a mine (game over)
- Players can mark cells suspected to contain mines to keep track of them.
- The number of adjacent mines is automatically calculated and displayed when uncovering empty cells.
- Cells can be specified using zero-based (x, y) coordinates.

## Project Structure

The project consists of two classes:

- `Field.java`: Represents the game field and handles operations related to marking, exploring, and managing mines.
- `Main.java`: Contains the main method and handles user input, game flow, and interaction.

## Class Descriptions

- `Field`: Manages the game field, including mine placement, tracking of marked cells, and exploration logic.
- `Main`: Handles user input, game initialization, and overall game flow.

## Dependencies

The project does not have any external dependencies and can be run using Java SE 8 or later.

Feel free to modify the code, expand the game features, or enhance the user interface as desired.

Enjoy playing Minesweeper!
