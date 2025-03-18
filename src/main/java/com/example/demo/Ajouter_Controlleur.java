package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Ajouter_Controlleur {

    @FXML private TextField prenomField;
    @FXML private TextField dateField;
    @FXML private TextField ficheField;
    @FXML private Button btnAjouter;

    private List<Affaires> personList;
    private Affaires personneAModifier;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Définir la liste des affaires
    public void setPersonList(List<Affaires> personList) {
        this.personList = personList;
    }

    // Définir la personne à modifier (si applicable)
    public void setPersonneAModifier(Affaires personne) {
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
        String prenom = prenomField.getText().trim();
        if (prenom.isEmpty()) {
            System.err.println("Le prénom ne peut pas être vide !");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateField.getText().trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de format de date. Veuillez entrer une date au format 'yyyy-MM-dd'.");
            return;
        }

        EtatAffaire fiche;
        try {
            fiche = EtatAffaire.valueOf(ficheField.getText().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("État de l'affaire invalide. Exemples : 'EN_COURS', 'FERMEE'.");
            return;
        }

        if (personneAModifier == null) {
            // Mode Ajout
            Affaires nouvelleAffaire = new Affaires(prenom, date, fiche);
            personList.add(nouvelleAffaire);
        } else {
            // Mode Modification
            personneAModifier.setPrenom(prenom);
            personneAModifier.setDate(date);
            personneAModifier.setEtatAffaire(fiche);
        }

        // Sauvegarde des données mises à jour
        JsonHandlerCase.writePersonsToJson(personList);
        fermerFenetre();
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) btnAjouter.getScene().getWindow();
        stage.close();
    }
}
