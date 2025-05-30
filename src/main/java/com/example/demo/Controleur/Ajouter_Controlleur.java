package com.example.demo.Controleur;


import com.example.demo.Patrons.Affaire;
import com.example.demo.JsonHandlers.JsonHandlerCase;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class Ajouter_Controlleur {
    @FXML
    public Button btnAjouter;
    @FXML
    public Label ajouterViewTitle;

    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField lieuField;
    @FXML
    public TextField typeField;
    @FXML
    public ComboBox<Affaire.Status> statusComboBox;
    @FXML
    public Spinner<Integer> graviteSpinner;

    public List<Affaire> listeAffaire;
    private Affaire affaireAModifier;


    @FXML
    public void initialize() {
        statusComboBox.getItems().setAll(Affaire.Status.values());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5);
        graviteSpinner.setValueFactory(valueFactory);

        // Gestion manuelle de la validation de l'entrée utilisateur
        TextField editor = graviteSpinner.getEditor();

        editor.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return; // Laisser vide temporairement
            }

            try {
                int value = Integer.parseInt(newValue);
                // Vérifie que la valeur est dans la plage autorisée
                if (value >= 1 && value <= 10) {
                    graviteSpinner.getValueFactory().setValue(value);
                } else {
                    editor.setText(oldValue); // Restaure si hors limites
                }
            } catch (NumberFormatException e) {
                editor.setText(oldValue); // Restaure si non numérique
            }
        });
    }

    // Définir la liste des affaires
    public void setAffaireList(List<Affaire> listeAffaire) {
        this.listeAffaire = listeAffaire;
    }

    // Définir l'affaire à modifier
    public void setAffaireAModifier(Affaire affaire) {
        this.affaireAModifier = affaire;

        datePicker.setValue(affaire.getDate());
        lieuField.setText(affaire.getLieu());
        typeField.setText(affaire.getType());
        statusComboBox.setValue(affaire.getStatus());
        graviteSpinner.getValueFactory().setValue(affaire.getGravite());
        btnAjouter.setText("Modifier");  // Change le bouton pour indiquer modification
        btnAjouter.getStyleClass().clear();
        ajouterViewTitle.setText("Modification d'une affaire");
    }

    @FXML
    private void ajouterPersonne() {
        String lieu = lieuField.getText().trim();
        if (lieu.isEmpty()) {
            showAlert("Erreur de champ", "Le champ 'Lieu de l'affaire' ne peut pas être vide !");
            return;
        }

        String type = typeField.getText().trim();
        if (type.isEmpty()) {
            showAlert("Erreur de champ", "Le champ 'Type d'affaire' ne peut pas être vide !");
            return;
        }

        Affaire.Status status = statusComboBox.getValue();
        int gravite = 0;
        if (graviteSpinner.getValue() == null) {
            showAlert("Erreur de gravité", "Le champ 'Gravité de l'affaire' ne peut pas être vide !");
            return;
        }
        else {
            gravite = graviteSpinner.getValue();
        }

        LocalDate date = datePicker.getValue();
        if (date != null) {
            System.out.println("Date sélectionnée : " + date);
        } else {
            showAlert("Erreur de date", "Le champ 'Date de l'affaire' ne peut pas être vide !");
            return;
        }

        if (affaireAModifier == null) {
            // Mode Ajout
            Affaire nouvelleAffaire = new Affaire(date, lieu, type, status, gravite);
            listeAffaire.add(nouvelleAffaire);
        } else {
            // Mode Modifications
            affaireAModifier.setLieu(lieu);
            affaireAModifier.setDate(date);
            affaireAModifier.setType(type);
            affaireAModifier.setStatus(status);
            affaireAModifier.setGravite(gravite);
        }

        // Sauvegarde des données mises à jour
        JsonHandlerCase.writePersonsToJson(listeAffaire);
        fermerFenetre();
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) btnAjouter.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
