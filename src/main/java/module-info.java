module com.ashraful {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.swing;
    requires java.desktop;

    exports com.ashraful;
    opens com.ashraful to javafx.fxml;
}
