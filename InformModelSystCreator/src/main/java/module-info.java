module com.megainf3d.informmodelsystcreator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.xml.bind;

    opens com.megainf3d.informmodelsystcreator to javafx.fxml;
    opens com.megainf3d.informmodelsystcreator.Tables to java.xml.bind;
    exports com.megainf3d.informmodelsystcreator;
    exports com.megainf3d.informmodelsystcreator.Adapters;
}