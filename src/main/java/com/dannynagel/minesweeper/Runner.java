package com.dannynagel.minesweeper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Runner extends Application {

    private final double TILE_WIDTH = 25;
    private final double TILE_HEIGHT = 25;
    private int numRows = 15;
    private int numCols = 25;
    private int startMines;

    private BorderPane root;
    private GameBoard gameBoard;
    private Display display;

    // digits for the timer
    private int digits;
    private Timeline timer;

    // Used for setting up new scenes
    private Stage stage;
    private Scene scene;

    public Parent setUpRoot(int numRows, int numCols, int startMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.startMines = startMines;

        root = new BorderPane();
        root.setPrefSize(numCols * TILE_WIDTH + 20, numRows * TILE_HEIGHT);
        root.setPadding(new Insets(0, 10, 10, 10));

        display = new Display(numCols * TILE_WIDTH);
        gameBoard = new GameBoard(numRows, numCols, TILE_WIDTH, TILE_HEIGHT, startMines, display);

        root.setBottom(gameBoard);
        root.setCenter(display);
        setUpMenu();

        return root;
    }

    private void setUpMenu() {
        Menu fileMenu = new Menu("Game");
        MenuItem beginner = new MenuItem("Beginner");
        MenuItem intermediate = new MenuItem("Intermediate");
        MenuItem expert = new MenuItem("Expert");
        MenuItem customize = new MenuItem("Customize...");

        beginner.setOnAction(event -> {
            timer.stop();
            scene = new Scene(setUpRoot(10, 10, 10));
            stage.setScene(scene);
        });
        intermediate.setOnAction(event -> {
            timer.stop();
            scene = new Scene(setUpRoot(16, 16, 40));
            stage.setScene(scene);
        });
        expert.setOnAction(event -> {
            timer.stop();
            scene = new Scene(setUpRoot(16, 30, 99));
            stage.setScene(scene);
        });
        customize.setOnAction(event -> {
            CustomizeMenu cm = new CustomizeMenu();
            cm.openCustomizeWindow();
        });

        fileMenu.getItems().addAll(beginner, intermediate, expert, new SeparatorMenuItem(), customize);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);

        root.setTop(menuBar);
    }


    //  ******* Display Class ***********************
    public class Display extends StackPane {
        private Label numMines, time;
        private Button face;
        //      private Timeline timer;
        private boolean timerStarted;

        Display(double width) {
            setPrefSize(width, 80);
            setPadding(new Insets(10, 10, 10, 10));

            BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGRAY,
                    CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(background_fill);
            setBackground(background);

            timerStarted = false;

            setUpNumMines();
            setUpTime();
            setUpFace();

            getChildren().addAll(numMines, face, time);
        }

        private void setUpNumMines() {
            numMines = new Label();

            setNumMinesText(startMines);
            numMines.setFont(new Font(40));
            numMines.setTextFill(Color.RED);
            numMines.setBackground(new Background(new BackgroundFill(Color.BLACK,
                    CornerRadii.EMPTY, Insets.EMPTY)));
            numMines.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1))));

            setAlignment(numMines, Pos.CENTER_LEFT);
        }

        public void setNumMinesText(int num) {
            String temp = "000" + num;
            numMines.setText(temp.substring(temp.length() - 3));
        }

        private void setUpTime() {
            digits = 0;
            time = new Label();

            time.setText("000");
            time.setFont(new Font(40));
            time.setTextFill(Color.RED);
            time.setBackground(new Background(new BackgroundFill(Color.BLACK,
                    CornerRadii.EMPTY, Insets.EMPTY)));
            time.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1))));

            timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (digits >= 999) {
                    timer.stop();
                    event.consume();
                }
                digitsPlusPlus();
                String temp = "000" + digits;
                time.setText(temp.substring(temp.length() - 3));
            }));
            timer.setCycleCount(Timeline.INDEFINITE);

            setAlignment(time, Pos.CENTER_RIGHT);
        }

        // Helper method for setUpTime()
        private void digitsPlusPlus() {
            digits++;
        }

        public boolean isStarted() {
            return timerStarted;
        }

        public void startTimer() {
            if (timerStarted == true)
                return;
            timer.play();
            timerStarted = true;
        }

        public void stopTimer() {
            timer.stop();
        }

        private void setUpFace() {
            face = new Button();
            face.setOnAction(event -> {
                        timer.stop();
                        scene = new Scene(setUpRoot(numRows, numCols, startMines));
                        stage.setScene(scene);
                    }
            );

            setAlignment(face, Pos.CENTER);


            face.setGraphic(Icon.drawFace());
        }

        public void setButtonToSunglasses() {
            face.setGraphic(Icon.drawSunglasses());
        }

    }
    // ******* End of Display Class **************


    // ****** CustomizeMenu Class **************

    public class CustomizeMenu {

        public void openCustomizeWindow() {
            Stage customizeWindow = new Stage();
            customizeWindow.initModality(Modality.APPLICATION_MODAL);
            customizeWindow.setTitle("Customize");

            customizeWindow.setMinWidth(240);

            Text numRows = new Text("   Rows:");
            numRows.setFont(new Font(20));
            Text numCols = new Text("   Columns:");
            numCols.setFont(new Font(20));
            Text startMines = new Text("   Mines:");
            startMines.setFont(new Font(20));

            TextField textFieldRows = new TextField();
            textFieldRows.setMaxWidth(40);
            TextField textFieldCols = new TextField();
            textFieldCols.setMaxWidth(40);
            TextField textFieldMines = new TextField();
            textFieldMines.setMaxWidth(40);

            HBox rowBox = new HBox();
            rowBox.setSpacing(61);
            rowBox.getChildren().addAll(numRows, textFieldRows);

            HBox colBox = new HBox();
            colBox.setSpacing(30);
            colBox.getChildren().addAll(numCols, textFieldCols);

            HBox minesBox = new HBox();
            minesBox.setSpacing(55);
            minesBox.getChildren().addAll(startMines, textFieldMines);

            Button newGame = new Button("New Game");
            newGame.setAlignment(Pos.BOTTOM_CENTER);
            newGame.setOnAction(event -> {
                if (isValidInput(textFieldRows.getText(), textFieldCols.getText(), textFieldMines.getText())) {
                    int tempRows = Integer.parseInt(textFieldRows.getText());
                    int tempCols = Integer.parseInt(textFieldCols.getText());
                    int tempMines = Integer.parseInt(textFieldMines.getText());

                    timer.stop();
                    scene = new Scene(setUpRoot(tempRows, tempCols, tempMines));
                    stage.setScene(scene);
                    customizeWindow.close();
                } else {
                    Stage wrongInput = new Stage();
                    wrongInput.initModality(Modality.APPLICATION_MODAL);
                    Text text = new Text("Valid integer input ranges are:\n\n" +
                            "0  <   Rows    <= 30\n" +
                            "10 <=  Columns <= 60\n" +
                            "0  <   Mines   <  Rows * Columns");
                    text.setFont(Font.font("Consolas", 16));
                    Button okay = new Button("Got it");
                    okay.setOnAction(event1 -> wrongInput.close());
                    VBox layout = new VBox();
                    layout.setSpacing(20);
                    layout.setAlignment(Pos.CENTER);
                    layout.setPadding(new Insets(5, 5, 5, 5));
                    layout.getChildren().addAll(text, okay);
                    wrongInput.setScene(new Scene(layout));
                    wrongInput.showAndWait();
                }
            });

            VBox root = new VBox(10);
            root.setPadding(new Insets(5, 5, 5, 5));
            root.getChildren().addAll(rowBox, colBox, minesBox, newGame);

            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root);
            customizeWindow.setScene(scene);
            customizeWindow.showAndWait();
        }

        private boolean isValidInput(String strRows, String strCols, String strMines) {
            if (!(isInteger(strRows) && isInteger(strCols) && isInteger(strMines)))
                return false;
            else {
                int tempRows = Integer.parseInt(strRows);
                int tempCols = Integer.parseInt(strCols);
                int tempMines = Integer.parseInt(strMines);

                return tempRows > 0 && tempRows <= 30 &&
                        tempCols >= 10 && tempCols <= 60 &&
                        tempMines < tempRows * tempCols;
            }
        }

        private boolean isInteger(String input) {
            try {
                int text = Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
    // ****** End CustomizeMenu Class ****************


    public void start(Stage primaryStage) {
        stage = primaryStage;
        scene = new Scene(setUpRoot(16, 16, 40));
        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
