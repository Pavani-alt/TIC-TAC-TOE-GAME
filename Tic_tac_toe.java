import java.util.*;

public class TicTacToeGame {

    static char[][] board;
    static char currentPlayer;
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe!");

        int choice;
        do {
            System.out.println("\nChoose Game Mode:");
            System.out.println("1. Player vs Player");
            System.out.println("2. Player vs System");
            System.out.println("3. System vs System");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number (1-4).");
                sc.next();
            }

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> playGame("PVP");
                case 2 -> playGame("PVS");
                case 3 -> playGame("SVS");
                case 4 -> System.out.println("Exiting game. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    static void playGame(String mode) {
        initializeBoard();
        currentPlayer = 'X';
        boolean gameEnded = false;

        // Main game loop
        while (!gameEnded) {
            printBoard();

            if (mode.equals("PVP")) {
                playerMove();
            } else if (mode.equals("PVS")) {
                if (currentPlayer == 'X') {
                    playerMove();
                } else {
                    systemMove();
                }
            } else if (mode.equals("SVS")) {
                systemMove();
                delay(1000);  // Pause for system vs system
            }

            if (checkWin()) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                gameEnded = true;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                gameEnded = true;
            } else {
                switchPlayer();
            }
        }
    }

    static void initializeBoard() {
        board = new char[][] {
            {'1', '2', '3'},
            {'4', '5', '6'},
            {'7', '8', '9'}
        };
    }

    static void printBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) System.out.println("---|---|---");
        }
        System.out.println();
    }

    // Handles user input and move validation
    static void playerMove() {
        int move;
        boolean valid = false;

        while (!valid) {
            System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
            if (sc.hasNextInt()) {
                move = sc.nextInt();
                if (isValidMove(move)) {
                    makeMove(move);
                    valid = true;
                } else {
                    System.out.println("That cell is already taken or invalid. Try again.");
                }
            } else {
                System.out.println("Please enter a number.");
                sc.next();
            }
        }
    }

    // Random valid move by system
    static void systemMove() {
        int move;
        do {
            move = random.nextInt(9) + 1;
        } while (!isValidMove(move));

        System.out.println("System (" + currentPlayer + ") chooses: " + move);
        makeMove(move);
    }

    static boolean isValidMove(int pos) {
        if (pos < 1 || pos > 9) return false;
        int row = (pos - 1) / 3;
        int col = (pos - 1) % 3;
        return board[row][col] != 'X' && board[row][col] != 'O';
    }

    static void makeMove(int pos) {
        int row = (pos - 1) / 3;
        int col = (pos - 1) % 3;
        board[row][col] = currentPlayer;
    }

    static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    static boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell != 'X' && cell != 'O') return false;
            }
        }
        return true;
    }

    // Checks for all win conditions (rows, columns, diagonals)
    static boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer &&
                 board[i][1] == currentPlayer &&
                 board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer &&
                 board[1][i] == currentPlayer &&
                 board[2][i] == currentPlayer)) {
                return true;
            }
        }

        if ((board[0][0] == currentPlayer &&
             board[1][1] == currentPlayer &&
             board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer &&
             board[1][1] == currentPlayer &&
             board[2][0] == currentPlayer)) {
            return true;
        }

        return false;
    }

    static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignored
        }
    }
}
