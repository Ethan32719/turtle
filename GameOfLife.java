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
                board[i + x][j + y] = data[i][j];
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
        int r = board.length;  // Number of rows
        int c = board[0].length;  // Number of columns
        int[][] newBoard = new int[r][c];  // Create a new board for the next generation

        // Iterate through each cell on the board
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int neighbors = countNeighbors(i, j);  // Count the number of live neighbors

                if (board[i][j] == 1) {
                    // Rule 1: Any live cell with fewer than two live neighbors dies (underpopulation)
                    if (neighbors == 0) {
                        newBoard[i][j] = 0;  // Cell dies due to isolation (no neighbors)
                    }
                    if (neighbors < 2) {
                        newBoard[i][j] = 0;  // Cell dies
                    }
                    // Rule 2: Any live cell with more than three live neighbors dies (overpopulation)
                    if (neighbors > 3) {
                        newBoard[i][j] = 0;  // Cell dies
                    }
                    // Rule 3: Any live cell with two or three live neighbors stays alive
                    if (neighbors == 2 || neighbors == 3) {
                        newBoard[i][j] = 1;  // Cell stays alive
                    }
                } else {  // If the cell is dead
                    // Rule 4: Any dead cell with exactly three live neighbors becomes alive (reproduction)
                    if (neighbors == 3) {
                        newBoard[i][j] = 1;  
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

            
            if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length) {
                count += board[nx][ny];
            }
        }
        return count;
    }

    // Get a value from the board with "wrap around"
    public int get(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
            return 0; 
        }
        return board[x][y];
    }


    // Test helper to get the whole board state
    public int[][] get() {
        return board;
    }

    // Test helper to print the current state
    public void print() {
        // Print the header with column numbers
        System.out.print("\n  ");
        for (int y = 0; y < board[0].length; y++) {
            System.out.print(y % 10 + " ");  // Print column indices
        }

        // Print each row of the board
        for (int x = 0; x < board.length; x++) {
            System.out.print("\n" + x % 10 + " ");  // Print row indices
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
