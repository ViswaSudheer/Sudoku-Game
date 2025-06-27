module sp.javasudokuprojects {
    requires javafx.controls;
    requires javafx.fxml;


    opens sp.javasudokuprojects to javafx.fxml;
    exports sp.javasudokuprojects;
}