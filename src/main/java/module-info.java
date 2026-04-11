module com.dannynagel.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dannynagel.minesweeper to javafx.fxml;
    exports com.dannynagel.minesweeper;
}