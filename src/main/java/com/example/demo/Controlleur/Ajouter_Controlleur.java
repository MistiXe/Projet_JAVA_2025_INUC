package com.example.demo.Controlleur;

import com.example.demo.PDFJSON.Affaire;
import com.example.demo.PDFJSON.JsonHandlerCase;
import com.example.demo.Patrons.Personne;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ajouter_Controlleur {

    @FXML private TextField prenomField;
    @FXML private TextField dateField;
    @FXML private TextField ficheField;
    @FXML private Button btnAjouter;

    @FXML private DatePicker datePicker;
    @FXML private TextField lieuField;
    @FXML private TextField typeField;
    @FXML private ComboBox<Affaire.Status> statusComboBox;
    @FXML private Spinner<Integer> graviteSpinner;

    private List<Personne> personList;
    private List<Affaire> listeAffaire;
    private Personne personneAModifier;

    private Affaire affaireAModifier;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Définir la liste des affaires
    public void setPersonList(List<Affaire> listeAffaire) {
        this.listeAffaire = listeAffaire;
    }

    // Définir la personne à modifier (si applicable)
    public void setPersonneAModifier(Personne personne) {
        this.personneAModifier = personne;
        if (personne != null) {
            prenomField.setText(personne.getPrenom());
            dateField.setText(personne.getDate().format(DATE_FORMATTER)); // Formatage en texte
            ficheField.setText(personne.getEtatAffaire().toString());  // Affichage de l'état
            btnAjouter.setText("Modifier");  // Change le bouton pour indiquer modification
        }
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
//        try {
//            date = LocalDate.parse(datePicker.getText().trim(), DATE_FORMATTER);
//        } catch (DateTimeParseException e) {
//            showAlert("Erreur de format de date", "Veuillez entrer une date au format 'yyyy-MM-dd'");
//            return;
//        }

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
