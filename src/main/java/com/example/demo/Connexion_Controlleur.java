package com.example.demo;


import com.gluonhq.charm.glisten.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;


public class Connexion_Controlleur {
    @FXML private TextField user;
    @FXML private PasswordField pass;
    @FXML private Button connexion;

    @FXML
    public void initialize() {
        user.setText("user1");
        pass.setText("password123");
        connexion.setOnAction(event -> login());
    }

    private void login() {
        String username = user.getText();
        String password = pass.getText();

        if (JsonHandlerUser.authenticate(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/hello-view.fxml"));
                Parent root = loader.load();
                Menu_Controlleur menuController = loader.getController();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Crim'INUC");
                stage.show();
                Stage currentStage = (Stage) connexion.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Identifiants incorrects.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
