package com.example.demo.Controleur;

import com.example.demo.JsonHandlers.JsonHandlerUser;
import javafx.scene.control.TextField;
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
    @FXML
    public TextField user;
    @FXML
    public PasswordField pass;
    @FXML
    public Button connexion;

    // Champ pour faciliter les tests unitaires
    public String messageErreur = "";

    @FXML
    public void initialize() {
        user.setText("user1");
        pass.setText("password123");
        connexion.setOnAction(event -> login());
    }

    public void login() {
        String username = user.getText();
        String password = pass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageErreur = "Identifiants incorrects.";
            showAlert(Alert.AlertType.ERROR, "Erreur", messageErreur);
            return;
        }

        if (JsonHandlerUser.authenticate(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Vues/main_view.fxml"));
                Parent root = loader.load();

                Menu_Controlleur menuController = loader.getController();
                if (menuController == null) {
                    System.out.println("⚠ ERREUR: Menu_Controlleur est NULL !");
                }

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                // Maximiser la fenêtre
                stage.setMaximized(true);
                stage.setTitle("Crim'INUC");
                stage.show();

                // Fermer la fenêtre actuelle
                Stage currentStage = (Stage) connexion.getScene().getWindow();
                currentStage.close();
                messageErreur = "";

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            messageErreur = "Identifiants incorrects.";
            showAlert(Alert.AlertType.ERROR, "Erreur", messageErreur);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    // Tests    

     /** Méthode utilitaire pour TestFX et pour ton Application main(): */
     public static void launchApp(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(
            Connexion_Controlleur.class.getResource("/com/example/demo/Vues/connexion_view.fxml")
        );
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
