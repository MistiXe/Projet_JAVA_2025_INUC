package com.example.demo.Controleur;

import com.example.demo.Patrons.Personne;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashSet;

public class ProfilPersonneController {

    // Déclaration des éléments FXML
    @FXML private Label idLabel;
    @FXML private Label prenomLabel;
    @FXML private Label nomLabel;
    @FXML private Label adressesLabel;
    @FXML private Label emailLabel;
    @FXML private Label telephoneLabel;
    @FXML private Label lieuNaissanceLabel;
    @FXML private Label genreLabel;
    @FXML private Label nationaliteLabel;
    @FXML private Label casierJudiciaireLabel;
    @FXML private Label statutLegalLabel;
    @FXML private Label pseudonymesLabel;
    @FXML private Label descriptionPhysiqueLabel;
    @FXML private Label photoUrlLabel;
    @FXML private Label derniereLocalisationLabel;
    @FXML private Label notesLabel;

    // Attribut Personne qui contiendra les données à afficher
    private Personne personne;

    // Cette méthode est appelée après que le fichier FXML soit chargé
    public void initialize() {
        if (personne != null) {
            System.out.println(personne.getId() + personne.getPrenom() + personne.getNom());
            // Remplir les labels avec les informations de la personne
            idLabel.setText(String.valueOf(personne.getId()));
            prenomLabel.setText(personne.getPrenom());
            nomLabel.setText(personne.getNom());
            adressesLabel.setText(String.join("\n", personne.getAdresses()));
            emailLabel.setText(personne.getEmail());
            telephoneLabel.setText(String.valueOf(personne.getTelephone()));
            lieuNaissanceLabel.setText(personne.getLieuNaissance());
            genreLabel.setText(personne.getGenre());
            nationaliteLabel.setText(personne.getNationalite());
            casierJudiciaireLabel.setText(personne.isCasierJudiciaire() ? "Oui" : "Non");
            statutLegalLabel.setText(personne.getStatutLegal().toString());
            pseudonymesLabel.setText(String.join("\n", personne.getPseudonymes()));
            descriptionPhysiqueLabel.setText(personne.getDescriptionPhysique());
            photoUrlLabel.setText(personne.getPhotoUrl());
            derniereLocalisationLabel.setText(personne.getDerniereLocalisation());
            notesLabel.setText(personne.getNotes());
        }
    }

    // Méthode pour définir la personne à afficher
    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    // Action pour revenir à la vue précédente
    @FXML
    private void handleRetourAction() {
        // Logique pour revenir à la vue précédente
        System.out.println("Retour à la vue précédente");
    }
}
