package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class Ajouter_Controlleur {
    @FXML private Button btnAjouter;
    @FXML private Label ajouterViewTitle;

    @FXML private DatePicker datePicker;
    @FXML private TextField lieuField;
    @FXML private TextField typeField;
    @FXML private ComboBox<Affaire.Status> statusComboBox;
    @FXML private Spinner<Integer> graviteSpinner;

    private List<Affaire> listeAffaire;
    private Affaire affaireAModifier;

    @FXML
    public void initialize() {
        statusComboBox.getItems().setAll(Affaire.Status.values());
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
        ajouterViewTitle.setText("Modifier une affaire");
    }

    @FXML
    private void ajouterPersonne() {
        String lieu = lieuField.getText().trim();
        if (lieu.isEmpty()) {
            showAlert("Erreur de champ", "Le champ 'lieu' ne peut pas être vide !");

            return;
        }

        String type = typeField.getText().trim();
        if (type.isEmpty()) {
            showAlert("Erreur de champ", "Le champ 'type' ne peut pas être vide !");
            return;
        }

        Affaire.Status status = statusComboBox.getValue();
        int gravite = graviteSpinner.getValue();

        LocalDate date = datePicker.getValue();
        if (date != null) {
            System.out.println("Date sélectionnée : " + date);
        } else {
            showAlert("Erreur de date", "Aucune date sélectionné");
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
