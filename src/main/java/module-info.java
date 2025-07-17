module com.ashraful {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ashraful to javafx.fxml;
    exports com.ashraful;
}
