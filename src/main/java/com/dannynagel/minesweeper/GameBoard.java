package com.dannynagel.minesweeper;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GameBoard extends Pane {

    private final double TILE_WIDTH, TILE_HEIGHT;
    private final int NUM_ROWS, NUM_COLS;
    private Tile[][] board;
    private int numMines;
    private Runner.Display display;


    GameBoard(int numRows, int numCols, double tileWidth, double tileHeight, int numMines, Runner.Display display) {
        TILE_WIDTH = tileWidth;
        TILE_HEIGHT = tileHeight;
        NUM_ROWS = numRows;
        NUM_COLS = numCols;
        this.numMines = numMines;
        this.display = display;
        setPrefSize(NUM_COLS * TILE_WIDTH, NUM_ROWS * TILE_HEIGHT);

        setUpBoard();
    }

    public void setUpBoard() {
        board = new Tile[NUM_ROWS][NUM_COLS];

        // Populate the board with all blank Tiles
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Tile tile = new Tile(TILE_WIDTH, TILE_HEIGHT, this, row, col);
                tile.setTranslateX(col * TILE_WIDTH);
                tile.setTranslateY(row * TILE_HEIGHT);

                board[row][col] = tile;
                getChildren().addAll(tile);
            }
        }
    }

    // Places numbers on Tiles indicating how many adjacent mines
    public void placeNumbers() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (!board[row][col].isMine()) {
                    int number = countMines(row, col);
                    Label label = board[row][col].getLabel();
                    label.setText(String.valueOf(number));
                    setUpNumberColor(label, number);
                    label.setFont(new Font(TILE_WIDTH * 0.7));
                    label.setOpacity(0);
                }
            }
        }
    }

    public void setUpNumberColor(Label label, int number) {
        if (number == 1)
            label.setTextFill(Color.BLUE);
        else if (number == 2)
            label.setTextFill(Color.GREEN);
        else if (number == 3)
            label.setTextFill(Color.RED);
        else if (number == 4)
            label.setTextFill(Color.PURPLE);
        else if (number == 5)
            label.setTextFill(Color.MAROON);
        else if (number == 6)
            label.setTextFill(Color.TURQUOISE);
        else if (number == 7)
            label.setTextFill(Color.BLACK);
        else if (number == 8)
            label.setTextFill(Color.GRAY);
    }

    // Used by placeNumbers()
    public int countMines(int row, int col) {
        int count = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                // Don't count if position is not inbounds
                if (!(row + r >= 0 && row + r < board.length && col + c >= 0 && col + c < board[row + r].length))
                    continue;
                // Don't count "yourself" the center
                if (r == 0 && c == 0)
                    continue;
                else if (board[row + r][col + c].isMine())
                    count++;
            }
        }
        return count;
    }

    // Places n random mines on the board.
    // Precondition: n < NUM_ROWS*NUM_COLS
    public void placeRandomMines(int n, int row, int col) {
        ArrayList<Integer> availableTiles = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS * NUM_COLS; i++)
            availableTiles.add(i);


        // Use i = row*NUM_COLS + col;
        while (n > 0 && availableTiles.size() > 0) {
            int rand = 0;

            if (availableTiles.size() == 1) {
                rand = availableTiles.get(0);
                availableTiles.remove(0);
            } else
                rand = availableTiles.remove((int) (Math.random() * availableTiles.size()));

            // Check first clicked neighborhood
            if (isFirstClickNeighbor(rand, row, col))
                continue;

            // row = rand / NUM_COLS
            // col = rand % NUM_COLS
            board[rand / NUM_COLS][rand % NUM_COLS].setUpMineIcon();
            board[rand / NUM_COLS][rand % NUM_COLS].getMine().setOpacity(0);

            n--;
        }

        // Place any remaining mines as neighbors
        while (n > 0) {
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (n > 0 && inBounds(row + r, col + c)) {
                        if (r == 0 && c == 0)
                            continue;
                        else {
                            System.out.println("n = " + n);

                            board[row + r][col + c].setUpMineIcon();
                            board[row + r][col + c].getMine().setOpacity(0);
                            n--;
                        }
                    }
                }
            }
        }

    }

    // Used by placeRandomMines()
    private int countNeighbors(int row, int col) {
        int count = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (inBounds(row + r, col + c)) {
                    if (r == 0 && c == 0)
                        continue;
                    else
                        count++;
                }
            }
        }
        return count;
    }

    private boolean isFirstClickNeighbor(int rand, int row, int col) {
        int checkRow = rand / NUM_COLS;
        int checkCol = rand % NUM_COLS;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (row + r == checkRow && col + c == checkCol)
                    return true;
            }
        }
        return false;
    }

    // @rotation: takes value from 0-7. 0 is due East. Increases counter clockwise
    // Halt condition is depth rotation > 7
    public void clearBlanks(int row, int col, int rotation) {
        while (rotation < 8) {
            Tile temp = checkRotationBlank(row, col, rotation);
            if (temp == null)
                rotation++;
            else if (!temp.getLabel().getText().equals("0")) {
                temp.getLabel().setOpacity(1);
                temp.setAvailable(false);
                temp.setUpBackground(Color.WHITESMOKE);
                if (temp.getFlag().getOpacity() == 1) {
                    temp.getFlag().setOpacity(0);
                    numMines++;
                }
                rotation++;
            } else {//Tile label is 0
                temp.setAvailable(false);
                temp.setUpBackground(Color.WHITESMOKE);
                if (temp.getFlag().getOpacity() == 1) {
                    temp.getFlag().setOpacity(0);
                    numMines++;
                }
                clearBlanks(temp.getRow(), temp.getCol(), 0);
                // Here iff rotation > 7 one layer deeper
                rotation++;
            }
        }

        // In case it is a single blank
        board[row][col].setUpBackground(Color.WHITESMOKE);
        return;
    }

    // Helper method
    public Tile checkRotationBlank(int row, int col, int rotation) {
        switch (rotation) {
            case 0:
                if (inBounds(row, col + 1) && board[row][col + 1].isAvailable())
                    return board[row][col + 1];
                else return null;
            case 1:
                if (inBounds(row - 1, col + 1) && board[row - 1][col + 1].isAvailable())
                    return board[row - 1][col + 1];
                else return null;
            case 2:
                if (inBounds(row - 1, col) && board[row - 1][col].isAvailable())
                    return board[row - 1][col];
                else return null;
            case 3:
                if (inBounds(row - 1, col - 1) && board[row - 1][col - 1].isAvailable())
                    return board[row - 1][col - 1];
                else return null;
            case 4:
                if (inBounds(row, col - 1) && board[row][col - 1].isAvailable())
                    return board[row][col - 1];
                else return null;
            case 5:
                if (inBounds(row + 1, col - 1) && board[row + 1][col - 1].isAvailable())
                    return board[row + 1][col - 1];
                else return null;
            case 6:
                if (inBounds(row + 1, col) && board[row + 1][col].isAvailable())
                    return board[row + 1][col];
                else return null;
            case 7:
                if (inBounds(row + 1, col + 1) && board[row + 1][col + 1].isAvailable())
                    return board[row + 1][col + 1];
                else return null;
            default:
                return null;
        }
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
    }

    public void showAllMines() {
        for (Tile[] row : board)
            for (Tile tile : row) {
                if (tile.getMine() != null) {
                    tile.getMine().setOpacity(1);
                    tile.getFlag().setOpacity(0);
                }
            }
    }

    public void showAllFlags() {
        for (Tile[] row : board)
            for (Tile tile : row) {
                if (tile.getMine() != null) {
                    tile.getFlag().setOpacity(1);
                }
            }
    }

    public void makeAllUnavailable() {
        for (Tile[] row : board)
            for (Tile tile : row)
                tile.setAvailable(false);
    }

    public boolean isWon() {
        for (Tile[] row : board)
            for (Tile tile : row) {
                if (tile.isMine())
                    continue;
                else if (tile.isAvailable())
                    return false;
            }
        return true;
    }

    public void numMinesPlusPlus() {
        numMines++;
    }

    public void numMinesMinusMinus() {
        numMines--;
    }

    public int getNumMines() {
        return numMines;
    }

    public Runner.Display getDisplay() {
        return display;
    }
}
