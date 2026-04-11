package com.dannynagel.minesweeper;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    private final double TILE_WIDTH, TILE_HEIGHT;
    private GameBoard gameBoard;
    private Pane mine, flag;
    private Label label;
    private boolean available;
    private final int ROW, COL;

    Tile(double tileWidth, double tileHeight, GameBoard gameBoard, int ROW, int COL) {
        TILE_WIDTH = tileWidth;
        TILE_HEIGHT = tileHeight;
        this.gameBoard = gameBoard;
        available = true;
        this.ROW = ROW;
        this.COL = COL;

        setUpBoarder();
        setUpBackground(Color.LIGHTGRAY);
        setUpFlagIcon();

        label = new Label("");
        getChildren().addAll(label);

        setOnMouseClicked(event -> {

            if (available == false)
                return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (!gameBoard.getDisplay().isStarted()) {
                    gameBoard.placeRandomMines(gameBoard.getNumMines(), ROW, COL);
                    gameBoard.placeNumbers();
                }

                if (isMine()) {
                    gameBoard.getDisplay().stopTimer();
                    setUpBackground(Color.RED);
                    gameBoard.showAllMines();
                    gameBoard.makeAllUnavailable();
                    return;

                } else {
                    gameBoard.getDisplay().startTimer();
                    if (label.getText().equals("0")) {
                        gameBoard.clearBlanks(getRow(), getCol(), 0);
                        gameBoard.getDisplay().setNumMinesText(gameBoard.getNumMines());
                    } else {
                        if (flag.getOpacity() == 1)
                            gameBoard.numMinesPlusPlus();

                        gameBoard.getDisplay().setNumMinesText(gameBoard.getNumMines());
                        label.setOpacity(1);
                    }
                }

                available = false;
                flag.setOpacity(0);
                setUpBackground(Color.WHITESMOKE);
                if (gameBoard.isWon()) {
                    gameBoard.getDisplay().stopTimer();
                    gameBoard.makeAllUnavailable();
                    gameBoard.showAllFlags();
                    gameBoard.getDisplay().setButtonToSunglasses();
                }

            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (flag.getOpacity() == 1)
                    gameBoard.numMinesPlusPlus();
                else
                    gameBoard.numMinesMinusMinus();
                toggleFlagIcon();
                gameBoard.getDisplay().setNumMinesText(gameBoard.getNumMines());
            }
        });
    }

    public void toggleFlagIcon() {
        if (flag.getOpacity() == 0)
            flag.setOpacity(1);
        else flag.setOpacity(0);
    }

    public void setUpMineIcon() {
        mine = Icon.drawMine(TILE_WIDTH, TILE_HEIGHT);
        mine.setOpacity(0);
        getChildren().addAll(mine);
    }

    public void setUpFlagIcon() {
        flag = Icon.drawFlag(TILE_WIDTH, TILE_HEIGHT);
        flag.setOpacity(0);
        flag.toFront();
        getChildren().addAll(flag);
    }

    public void setUpBackground(Color color) {
        BackgroundFill background_fill = new BackgroundFill(color,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        setBackground(background);
    }

    // Creates a black boarder around each tile
    private void setUpBoarder() {
        Rectangle boarder = new Rectangle(getTranslateX(),
                getTranslateY(),
                TILE_WIDTH,
                TILE_WIDTH);
        boarder.setFill(null);
        boarder.setStroke(Color.BLACK);
        getChildren().addAll(boarder);
    }

    public boolean isMine() {
        return mine != null;
    }

    public Pane getMine() {
        return mine;
    }

    public Label getLabel() {
        return label;
    }

    public Pane getFlag() {
        return flag;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getRow() {
        return (int) (getTranslateY() / TILE_HEIGHT);
    }

    public int getCol() {
        return (int) (getTranslateX() / TILE_WIDTH);
    }
}
