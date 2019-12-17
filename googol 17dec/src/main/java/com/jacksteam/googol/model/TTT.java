package com.jacksteam.googol.model;


public class TTT {
    /**
     * //Variables
     * board - matrix of the board state
     * currentPlayerMark - mark of the current player
     * id - unique id for the board
     * gameover - bool: is the game over?
     * 
     * //Getters
     * getCurrentPlayerMark
     * getId
     * getGameOver
     * 
     * //Methods
     * toggleGameOver - toggles GameOver, what more u wan?
     * initilaizeBoard() - starts the board with all -
     * printboard() - Return char[] of the current boardState
     * 
     */

    private char[][] board;
    private char currentPlayerMark;
    private final int id;
    private boolean gameover;

    public TTT(int id) {
        this.id = id;
        this.board = new char[3][3];
        this.currentPlayerMark = 'x';
        this.gameover=false;
        initializeBoard();
    }

    public boolean getGameOver(){
        return gameover;
    }
    public int getId() {
        return id;
    }
    public void toggleGameOver(){
        gameover = !gameover;
    }
    public char getCurrentPlayerMark()
    {
        return currentPlayerMark;
    }


   
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }
    
    // Return char[] of the current boardState
    public char[] printBoard() {
        char[] out = new char[9];

        out[0]=board[0][0];
        out[1]=board[0][1];
        out[2]=board[0][2];
        out[3]=board[1][0];
        out[4]=board[1][1];
        out[5]=board[1][2];
        out[6]=board[2][0];
        out[7]=board[2][1];
        out[8]=board[2][2];
        return out;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }


    // Returns true if there is a win, false otherwise.
    // This calls our other win check functions to check the entire board.
    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }


    // Loop through rows and see if any are winners.
    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
                return true;
            }
        }
        return false;
    }


    // Loop through columns and see if any are winners.
    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
                return true;
            }
        }
        return false;
    }


    // Check the two diagonals to see if either is a win. Return true if either wins.
    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
    }


    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }


    // Change player marks back and forth.
    public void changePlayer() {
        if (currentPlayerMark == 'x') {
            currentPlayerMark = 'o';
        }
        else {
            currentPlayerMark = 'x';
        }
    }

    // Places a mark at the cell specified by row and col with the mark of the current player.
    public boolean placeMark(int row, int col) {

        // Make sure that row and column are in bounds of the board.
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == '-') {
                    board[row][col] = currentPlayerMark;
                    return true;
                }
            }
        }

        return false;
    }
}