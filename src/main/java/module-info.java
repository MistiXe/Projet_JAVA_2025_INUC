module com.example.demo {
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.datatype.jsr310;

    requires com.fasterxml.jackson.databind;
    requires com.gluonhq.charm.glisten;
    requires jbcrypt;
    requires java.desktop;
    requires org.apache.pdfbox;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.PDFJSON;
    opens com.example.demo.PDFJSON to javafx.fxml;
    exports com.example.demo.Patrons;
    opens com.example.demo.Patrons to javafx.fxml;
    exports com.example.demo.Controlleur;
    opens com.example.demo.Controlleur to javafx.fxml;
    exports com.example.demo.Modèle;
    opens com.example.demo.Modèle to javafx.fxml;
}