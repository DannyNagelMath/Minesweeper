module com.mysweeperfx.mysweeperfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mysweeperfx.mysweeperfx to javafx.fxml;
    exports com.mysweeperfx.mysweeperfx;
}