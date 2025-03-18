package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Ajouter_Controlleur {

    @FXML private TextField prenomField;
    @FXML private TextField dateField;
    @FXML private TextField ficheField;
    @FXML private Button btnAjouter;

    private List<Affaires> personList;
    private Affaires personneAModifier;

    // Définir la liste des affaires
    public void setPersonList(List<Affaires> personList) {
        this.personList = personList;
    }

    // Définir la personne à modifier (si applicable)
    public void setPersonneAModifier(Affaires personne) {
        this.personneAModifier = personne;
        if (personne != null) {
            prenomField.setText(personne.getPrenom());
            // Formatage de la date en chaîne de caractères
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(personne.getDate()));
            ficheField.setText(personne.getEtatAffaire().toString());  // Affiche l'état de l'affaire
            btnAjouter.setText("Modifier");  // Changer le bouton en mode "Modifier"
        }
    }

    @FXML
    private void ajouterPersonne() {
        String prenom = prenomField.getText().trim();
        if (prenom.isEmpty()) {
            System.out.println("Le prénom ne peut pas être vide !");
            return; // Empêche l'ajout si le prénom est vide
        }

        Date date = null;
        try {
            // Tentative de conversion de la chaîne en date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateField.getText().trim());
        } catch (ParseException e) {
            System.out.println("Erreur de format de date. Veuillez entrer une date au format 'yyyy-MM-dd'.");
            return; // Empêche l'ajout si la date est incorrecte
        }

        EtatAffaire fiche;
        try {
            // Tentative de conversion de l'état de l'affaire en enum
            fiche = EtatAffaire.valueOf(ficheField.getText().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("État de l'affaire invalide. Veuillez entrer un état valide. Exemple : 'EN_COURS', 'FERMEE'.");
            return; // Empêche l'ajout si l'état de l'affaire est invalide
        }

        // Si les champs sont valides, ajouter ou modifier l'affaire
        if (personneAModifier == null) {
            // Mode Ajout
            Affaires nouvellePersonne = new Affaires(prenom, date, fiche);
            personList.add(nouvellePersonne);
        } else {
            // Mode Modification
            personneAModifier.setPrenom(prenom);
            personneAModifier.setDate(date);
            personneAModifier.setEtatAffaire(fiche);
        }

        // Sauvegarder les données mises à jour dans le fichier JSON
        JsonHandlerCase.writePersonsToJson(personList);
        fermerFenetre();  // Fermer la fenêtre après l'ajout ou la modification
    }

    @FXML
    private void fermerFenetre() {
        // Fermer la fenêtre
        Stage stage = (Stage) btnAjouter.getScene().getWindow();
        stage.close();
    }
}
