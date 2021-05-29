package problem1_tictactoe;

public class GameManager {
    public static final int X = 1;
    public static final int O = -1;

    private final int[][] board = new int[3][3];

    public GameManager() {
    }

    public boolean move(int m, int i, int j) {
        if (board[i][j] == 0) {
            board[i][j] = m;
            return true;
        }
        return false;
    }

    private boolean checkRowsOrColumnsForWin(int p, boolean isRows) {
        int line;
        for (int i = 0; i < 3; i++) {
            line = 0;
            for (int j = 0; j < 3; j++)
                if (isRows) {
                    if (board[i][j] == p) {
                        line++;
                    }
                } else {
                    if (board[j][i] == p) {
                        line++;
                    }
                }
            if (line == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(int p) {

        if (checkRowsOrColumnsForWin(p, true) || checkRowsOrColumnsForWin(p, false)) {
            return true;
        }

        int line = 0;
        for (int i = 0; i < 3; i++)
            if (board[i][i] == p) {
                line++;
            }
        if (line == 3) {
            return true;
        }

        line = 0;
        for (int i = 0; i < 3; i++)
            if (board[i][2 - i] == p) {
                line++;
            }
        return line == 3;
    }

    public boolean hasMoves(int p) {
        int line = 0;

        // check rows
        for (int i = 0; i < 3; i++) {
            line = 0;
            for (int j = 0; j < 3; j++)
                if (board[i][j] != -p)
                    line++;
            if (line == 3)
                return true;
        }

        // check columns
        for (int i = 0; i < 3; i++) {
            line = 0;
            for (int j = 0; j < 3; j++)
                if (board[j][i] != -p)
                    line++;
            if (line == 3)
                return true;
        }

        // check main diagonal
        line = 0;
        for (int i = 0; i < 3; i++)
            if (board[i][i] != -p)
                line++;
        if (line == 3)
            return true;

        // check other diagonal
        line = 0;
        for (int i = 0; i < 3; i++)
            if (board[i][2 - i] != -p)
                line++;
        if (line == 3)
            return true;

        return false;
    }
}
