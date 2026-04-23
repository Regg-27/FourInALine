import java.util.Scanner;

public class FourInALine {
    public static void main(String[] args) {
        setup();
        startGame();
        printBoard();
         while (true) {
             if (humanFirst) {
                 getHumanMove();
                 printBoard();
                 char winner = checkWinner();
                 if (winner == 'O') {
                     System.out.println("You win!\nGame Over!");
                     return;
                 }
                 if (winner == 'X') {
                     System.out.println("Computer wins!\nGame Over!");
                     return;
                 }
                 makeComputerMove();
                 printBoard();
                 winner = checkWinner();
                 if (winner == 'O') {
                     System.out.println("You win!\nGame Over!");
                     return;
                 }
                 if (winner == 'X') {
                     System.out.println("Computer wins!\nGame Over!");
                     return;
                 }
             } else {
                 makeComputerMove();
                 printBoard();
                 char winner = checkWinner();
                 if (winner == 'O') {
                     System.out.println("You win!\nGame Over!");
                     return;
                 }
                 if (winner == 'X') {
                     System.out.println("Computer wins!\nGame Over!");
                     return;
                 }
                 getHumanMove();
                 printBoard();
                 winner = checkWinner();
                 if (winner == 'O') {
                     System.out.println("You win!\nGame Over!");
                     return;
                 }
                 if (winner == 'X') {
                     System.out.println("Computer wins!\nGame Over!");
                     return;
                 }
             }
         }
    }
    static char[][] board = new char [8][8];
    static Scanner scanner = new Scanner(System.in);
    static boolean humanFirst;
    static int timeLimit;
    static long startTime;




    static void setup() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '-';
            }
        }
    }

    static void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < 8; i++) {
            int letter = ('A' + i);
            System.out.print((char)letter + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void startGame() {
        System.out.print("Would you like to go first? (y/n): ");
        String input = scanner.next();
        humanFirst = input.equals("y");
        System.out.print("How long should the computer think about its moves (in seconds)?: ");
        timeLimit = scanner.nextInt();
    }

    static void getHumanMove() {
        while (true) {
            System.out.print("Choose your next move: ");
            String move = scanner.next();
            if (move.length() < 2) {
                System.out.println("Enter a valid move.");
                continue;
            }
            char rowChar = move.charAt(0);
            char colChar = move.charAt(1);
            int rowIndex = rowChar - 'A';
            int colIndex = colChar - '1';
            if (rowIndex >= 0 && rowIndex < 8 && colIndex >= 0 && colIndex < 8 && board[rowIndex][colIndex] == '-') {
                board[rowIndex][colIndex] = 'O';
                break;
            } else {
                System.out.println("Enter a valid move.");
            }
        }
    }

    static char checkWinner() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != '-' && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] &&
                        board[i][j]  == board[i][j+3]) {
                    return board[i][j];
                }
            }
        }
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 4; i++) {
                if (board[i][j] != '-' && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] &&
                        board[i][j]  == board[i+3][j]) {
                    return board[i][j];
                }
            }
        }
        return ' ';
    }

    static int evaluate() {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                int xCount = 0, oCount = 0;
                for (int k = 0; k < 4; k++) {
                    if (board[i][j+k] == 'X') {
                        xCount++;
                    } else if (board[i][j+k] == 'O') {
                        oCount++;
                    }
                }
                if (xCount == 3 && oCount == 0) score += 100;
                else if (xCount == 2 && oCount == 0) score += 10;
                else if (oCount == 3 && xCount == 0) score -= 100;
                else if (oCount == 2 && xCount == 0) score -= 10;
            }
        }
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 5; i++) {
                int xCount = 0, oCount = 0;
                for (int k = 0; k < 4; k++) {
                    if (board[i+k][j] == 'X') {
                        xCount++;
                    } else if (board[i+k][j] == 'O') {
                        oCount++;
                    }
                }
                if (xCount == 3 && oCount == 0) score += 100;
                else if (xCount == 2 && oCount == 0) score += 10;
                else if (oCount == 3 && xCount == 0) score -= 100;
                else if (oCount == 2 && xCount == 0) score -= 10;
            }
        }
        return score;
    }

    static int maxValue(int depth, int alpha, int beta) {
        long elapsed = System.currentTimeMillis() - startTime;
        char winner = checkWinner();

        if (elapsed >= timeLimit * 1000 || depth == 0) {
            return evaluate();
        }
        if (winner == 'X') {
            return 10000;
        }
        if (winner == 'O') {
            return -10000;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'X';
                    int score = minValue(depth - 1, alpha, beta);
                    board[i][j] = '-';
                    if (score > alpha) {
                        alpha = score;
                    }
                    if (alpha >= beta) {
                        return alpha;
                    }
                }
            }
        }
        return alpha;
    }

    static int minValue(int depth, int alpha, int beta) {
        long elapsed = System.currentTimeMillis() - startTime;
        char winner = checkWinner();

        if (elapsed >= timeLimit * 1000 || depth == 0) {
            return evaluate();
        }
        if (winner == 'X') {
            return 10000;
        }
        if (winner == 'O') {
            return -10000;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                    int score = maxValue(depth - 1, alpha, beta);
                    board[i][j] = '-';
                    if (score < beta) {
                        beta = score;
                    }
                    if (alpha >= beta) {
                        return beta;
                    }
                }
            }
        }
        return beta;
    }

    static void makeComputerMove() {
        startTime = System.currentTimeMillis();
        int bestI = -1, bestJ = -1;

        for (int depth = 1; ; depth++) {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= timeLimit * 1000) {
                break;
            }
            int bestScore = -10000;
            int tempI = -1, tempJ = -1;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        int score = minValue(depth, -10000, 10000);
                        board[i][j] = '-';
                        if (score > bestScore) {
                            bestScore = score;
                            tempI = i;
                            tempJ = j;
                        }
                    }
                }
            }
            bestI = tempI;
            bestJ = tempJ;
        }
        board[bestI][bestJ] = 'X';
    }

}
