import java.util.Scanner;

public class UI {
    // Scanner object to read user inputs from the console
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        // Main loop for the game to allow repeated play
        while (true) {
            // Get the number of rows and columns for the board
            int rows = getInput("Enter number of rows (1-10): ", 1, 10);
            int cols = getInput("Enter number of columns (1-10): ", 1, 10);

            // Determine the maximum allowable number of bombs (capped at 30)
            int maxBombCount = Math.min(30, rows * cols - 1);
            int bombCount = getInput("Enter number of bombs (1-" + maxBombCount + "): ", 1, maxBombCount);

            // Create and initialize the game with the given dimensions and bomb count
            Game game = new Game(rows, cols, bombCount);
            game.initializeGame();

            boolean gameOver = false; // Flag to track whether the game is over
            do {
                printBoard(game.getVisibleBoard()); // Display the board to the player

                // Get row and column inputs
                int row = getInput("Enter Y coordinate: ", 1, rows) - 1;
                int col = getInput("Enter X coordinate: ", 1, cols) - 1;

                try {
                    // Check the cell at the selected coordinates
                    if (!game.checkCell(row, col)) {
                        System.out.println("Boom! You hit a bomb. Game Over.");
                        gameOver = true; // End the game if a bomb is hit
                    } else if (game.checkWin()) {
                        System.out.println("Congratulations! You won!");
                        gameOver = true; // End the game if the player wins
                    }
                } catch (IllegalArgumentException e) {
                    // Handle invalid coordinates (e.g., out of bounds)
                    System.out.println(e.getMessage());
                }
            } while (!gameOver); // Repeat until the game ends

            // Ask the player if they want to play another game
            if (!askToPlayAgain()) {
                break; // Exit the game loop if the player chooses not to play again
            }
        }
    }

    // Method to get validated user input within a specified range
    private static int getInput(String prompt, int min, int max) {
        while (true) {
            try {
                // Prompt the player for input
                System.out.print(prompt);
                int input = keyboard.nextInt();

                // Validate the input against the specified range
                if (input >= min && input <= max) {
                    return input; // Return valid input
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                // Handle invalid input types (e.g., non-integer values)
                System.out.println("Invalid input. Please enter a valid number.");
                keyboard.next(); // Clear the invalid input to avoid an infinite loop
            }
        }
    }

    // Method to display the current visible state of the board
    private static void printBoard(char[][] board) {
        System.out.println("\nCurrent Board:");

        // Print column indices for reference, directly above the cells
        System.out.print("   "); // Start at the same position as the first cell
        for (int col = 0; col < board[0].length; col++) {
            System.out.print((col + 1) + " "); // Column numbers directly over the cells
        }
        System.out.println(); // Move to the next line

        // Print each row with the row number aligned on the left
        for (int row = 0; row < board.length; row++) {
            System.out.print((row + 1) + " "); // Print row number

            for (char cell : board[row]) {
                System.out.print(cell + " "); // Print each cell in the row
            }
            System.out.println(); // Move to the next line after printing a row
        }
    }

    // Method to ask the player if they want to play again
    private static boolean askToPlayAgain() {
        while (true) {
            try {
                // Prompt the player for a response
                System.out.print("Do you want to play again? (yes/no): ");
                String response = keyboard.next();

                // Validate the response
                if (response.equalsIgnoreCase("yes")) {
                    return true; // Player wants to play again
                } else if (response.equalsIgnoreCase("no")) {
                    return false; // Player does not want to play again
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } catch (Exception e) {
                // Handle unexpected errors
                System.out.println("An error occurred. Please try again.");
            }
        }
    }
}
