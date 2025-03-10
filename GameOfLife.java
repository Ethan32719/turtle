import java.util.Arrays;

public class GameOfLife implements Board {

    // Integers: 0 or 1 for alive or dead
    private int[][] board;

    public GameOfLife(int x, int y) {
        // Construct a 2d array of the given x and y size.
        board = new int[x][y];  
    }

    // Set values on the board
    public void set(int x, int y, int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (i + x < board.length && j + y < board[0].length) {
                    board[i + x][j + y] = data[i][j];
                }
            }
        }
    }

    // Run the simulation for a number of turns
    public void run(int turns) {
        // Call step the number of times requested
        for (int i = 0; i < turns; i++) {
            step();
        }
    }

    // Step the simulation forward one turn.
    public void step() {
        int rows = board.length;  // Number of rows
        int cols = board[0].length;  // Number of columns
        int[][] newBoard = new int[rows][cols];  // Create a new board for the next generation

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neighbors = countNeighbors(i, j);  // Count the number of live neighbors

                if (board[i][j] == 1) {  // If the cell is alive
                    if (neighbors == 2 || neighbors == 3) {
                        newBoard[i][j] = 1;  // Cell stays alive
                    } else {
                        newBoard[i][j] = 0;  // Cell dies
                    }
                } else {  // If the cell is dead
                    if (neighbors == 3) {
                        newBoard[i][j] = 1;  // Dead cell with 3 neighbors becomes alive
                    } else {
                        newBoard[i][j] = 0;  // Cell stays dead
                    }
                }
            }
        }

        // Update the board with the next generation
        board = newBoard;
        print();  // Print the updated board (optional)
    }

    // Count the number of live neighbors around a given cell (x, y)
    public int countNeighbors(int x, int y) {
        int count = 0;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // Wrap around the grid edges
            if (nx < 0) nx = board.length - 1;
            if (nx >= board.length) nx = 0;
            if (ny < 0) ny = board[0].length - 1;
            if (ny >= board[0].length) ny = 0;

            count += board[nx][ny];
        }
        return count;
    }

    // Get a value from the board with "wrap around"
    public int get(int x, int y) {
        // Wrap the coordinates to simulate an infinite grid
        if (x < 0) x = board.length - 1;
        if (x >= board.length) x = 0;
        if (y < 0) y = board[0].length - 1;
        if (y >= board[0].length) y = 0;

        return board[x][y];
    }

    // Test helper to get the whole board state
    public int[][] get() {
        return board;
    }

    // Test helper to print the current state
    public void print() {
        System.out.print("\n  ");
        for (int y = 0; y < board[0].length; y++) {
            System.out.print(y % 10 + " ");
        }

        // Print each row of the board
        for (int x = 0; x < board.length; x++) {
            System.out.print("\n" + x % 10 + " ");
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == 1) {
                    System.out.print("⬛ ");  // Live cell is ⬛
                } else {
                    System.out.print("⬜ ");  // Dead cell is ⬜
                }
            }
        }
        System.out.println();
    }
}
