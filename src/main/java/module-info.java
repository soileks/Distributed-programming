module org.example.palochkiwithinterface {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.rmi;
    requires java.desktop;

    opens org.example.palochkiwithinterface to javafx.fxml;
    exports org.example.palochkiwithinterface;
}