package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;

public class Ajouter_Controlleur {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField ageField;
    @FXML private TextField ficheField;
    @FXML private Button btnAjouter;

    private List<Personne> personList;
    private Personne personneAModifier;

    // Initialise la liste des personnes (appelée depuis HelloController)
    public void setPersonList(List<Personne> personList) {
        this.personList = personList;
    }

    // Définir une personne à modifier
    public void setPersonneAModifier(Personne personne) {
        this.personneAModifier = personne;
        if (personne != null) {
            nomField.setText(personne.getNom());
            prenomField.setText(personne.getPrenom());
            ageField.setText(String.valueOf(personne.getAge()));
            ficheField.setText(String.valueOf(personne.isFiche()));

            btnAjouter.setText("Modifier"); // Changer le texte du bouton
        }
    }

    // Ajouter ou modifier une personne
    @FXML
    private void ajouterPersonne() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        int age = Integer.parseInt(ageField.getText());
        boolean fiche = Boolean.parseBoolean(ficheField.getText());

        if (!nom.isEmpty() && !prenom.isEmpty()) {
            if (personneAModifier == null) {
                // Mode Ajout
                Personne nouvellePersonne = new Personne(nom, prenom, age, fiche);
                personList.add(nouvellePersonne);
            } else {
                // Mode Modification
                personneAModifier.setNom(nom);
                personneAModifier.setPrenom(prenom);
                personneAModifier.setAge(age);
                personneAModifier.setFiche(fiche);
            }
            JsonHandler.writePersonsToJson(personList); // Met à jour le JSON
            fermerFenetre();
        }
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) btnAjouter.getScene().getWindow();
        stage.close();
    }
}
