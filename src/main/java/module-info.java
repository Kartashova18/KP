module com.megainf3d.informmodelsystcreator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens com.megainf3d.informmodelsystcreator to javafx.fxml;
    exports com.megainf3d.informmodelsystcreator;
}