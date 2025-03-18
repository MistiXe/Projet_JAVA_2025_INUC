module com.example.demo {
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    requires com.fasterxml.jackson.databind;
    requires com.gluonhq.charm.glisten;
    requires jbcrypt;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}