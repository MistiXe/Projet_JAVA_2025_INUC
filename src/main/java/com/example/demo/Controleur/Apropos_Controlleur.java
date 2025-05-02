package com.example.demo.Controleur;

import com.example.demo.Patrons.Personne;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Apropos_Controlleur {
    // Déclaration des éléments FXML
    @FXML private Button closeWindow;

    // Cette méthode est appelée après que le fichier FXML soit chargé
    public void initialize() {
    }

    // Action pour revenir à la vue précédente
    @FXML
    private void handleRetourAction() {
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }
}
