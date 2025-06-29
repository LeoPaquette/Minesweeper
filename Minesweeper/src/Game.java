import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    // Fields to represent the game configuration and state
    private int rowNum; // Number of rows in the board
    private int colNum; // Number of columns in the board
    private int bombNum; // Number of bombs on the board
    private char[][] hiddenBoard; // Internal board with bomb locations and adjacent bomb counts
    private char[][] visibleBoard; // Player-facing board showing uncovered cells

    // Constructor to initialize the game with specified rows, columns, and bombs
    public Game(int rowNum, int colNum, int bombNum) {
        this.rowNum = rowNum; // Assign number of rows
        this.colNum = colNum; // Assign number of columns
        this.bombNum = bombNum; // Assign number of bombs

        // Validate that the number of bombs does not exceed total cells
        if (bombNum > rowNum * colNum) {
            throw new IllegalArgumentException("Bomb count exceeds total cells!");
        }

        // Initialize the boards
        this.hiddenBoard = new char[rowNum][colNum]; // Hidden board for game logic
        this.visibleBoard = new char[rowNum][colNum]; // Visible board for the player
    }

    // Initialize the game board by setting hidden spaces and placing bombs
    public void initializeGame() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                visibleBoard[i][j] = 'X'; // Mark all cells as hidden on the visible board
                hiddenBoard[i][j] = '0'; // Initialize hidden board with empty spaces
            }
        }
        placeBombs(); // Randomly place bombs on the hidden board
        calculateAdjacentBombs(); // Compute bomb counts for each cell
    }

    // Method to place bombs randomly on the hidden board
    private void placeBombs() {
        // Create a list to store all possible board positions
        List<int[]> positions = new ArrayList<>();

        // Generate all possible board positions (row, col pairs)
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                positions.add(new int[] { i, j }); // Add the position as an array [row, col]
            }
        }

        // Randomize the list of positions to ensure bomb placement is truly random
        Collections.shuffle(positions);

        // Place bombs at the first 'bombNum' positions from the shuffled list
        for (int i = 0; i < bombNum; i++) {
            int row = positions.get(i)[0]; // Extract row index
            int col = positions.get(i)[1]; // Extract column index
            hiddenBoard[row][col] = 'B'; // Mark the position with a bomb ('B')
        }
    }

    // Calculate the number of bombs adjacent to each cell
    private void calculateAdjacentBombs() {
        int[] directions = { -1, 0, 1 }; // Direction offsets for adjacency checks
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                if (hiddenBoard[r][c] == 'B')
                    continue; // Skip cells that contain bombs
                int bombCount = 0; // Initialize adjacent bomb count for the cell
                for (int dr : directions) {
                    for (int dc : directions) {
                        int nr = r + dr; // Calculate adjacent row index
                        int nc = c + dc; // Calculate adjacent column index
                        // Check if the adjacent cell is within bounds and contains a bomb
                        if (nr >= 0 && nr < rowNum && nc >= 0 && nc < colNum && hiddenBoard[nr][nc] == 'B') {
                            bombCount++; // Increment bomb count for valid adjacent cells
                        }
                    }
                }
                hiddenBoard[r][c] = (char) ('0' + bombCount); // Update the cell with the bomb count
            }
        }
    }

    // Method to check a cell's content and update the visible board
    public boolean checkCell(int row, int col) {
        // Validate cell coordinates to prevent out-of-bounds errors
        if (row < 0 || row >= rowNum || col < 0 || col >= colNum) {
            throw new IllegalArgumentException("Invalid cell coordinates!");
        }
        // Check if the cell contains a bomb
        if (hiddenBoard[row][col] == 'B') {
            return false; // The player hit a bomb
        }
        revealCell(row, col); // Reveal the cell and recursively reveal surrounding cells if applicable
        return true; // The cell is safe
    }

    // Method to reveal a cell on the visible board
    private void revealCell(int row, int col) {
        if (visibleBoard[row][col] != 'X')
            return; // Skip already revealed cells
        visibleBoard[row][col] = hiddenBoard[row][col]; // Reveal the cell's content
        // If the cell has no adjacent bombs, recursively reveal surrounding cells
        if (hiddenBoard[row][col] == '0') {
            checkAdjacentCells(row, col);
        }
    }

    // Check adjacent cells
    private void checkAdjacentCells(int row, int col) {
        int[] directions = { -1, 0, 1 }; // Direction offsets for adjacency checks
        for (int dr : directions) {
            for (int dc : directions) {
                int nr = row + dr; // Calculate adjacent row index
                int nc = col + dc; // Calculate adjacent column index
                // Check if the adjacent cell is within bounds and hidden
                if (nr >= 0 && nr < rowNum && nc >= 0 && nc < colNum && visibleBoard[nr][nc] == 'X') {
                    revealCell(nr, nc); // Reveal the adjacent cell
                }
            }
        }
    }

    // Check if the player has uncovered all safe cells (not bombs)
    public boolean checkWin() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                // If any non-bomb cell is still hidden, the player has not won
                if (hiddenBoard[i][j] != 'B' && visibleBoard[i][j] == 'X') {
                    return false;
                }
            }
        }
        return true; // All safe cells are revealed, player wins
    }

    // Getter method to return the visible board for the player
    public char[][] getVisibleBoard() {
        return visibleBoard;
    }
}
