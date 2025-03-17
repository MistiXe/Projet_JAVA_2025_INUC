package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Menu_Controlleur {
    @FXML private TableView<Personne> tableView;
    @FXML private TableColumn<Personne, String> columnPrenom;
    @FXML private TableColumn<Personne, String> columnNom;
    @FXML private TableColumn<Personne, Integer> columnAge;
    @FXML private TableColumn<Personne, Boolean> columnFiche;

    @FXML
    private Label labelNom;
    @FXML
    private Label labelPrenom;
    @FXML
    private Label labelAge;
    @FXML
    private Label labelFiche;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private final ObservableList<Personne> personList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Lier les colonnes aux attributs de Person
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnFiche.setCellValueFactory(new PropertyValueFactory<>("fiche"));



        // Charger les données du JSON
        List<Personne> persons = JsonHandler.readPersonsFromJson();
        if (persons != null) {
            personList.addAll(persons);
        }
        tableView.setItems(personList);

        // Action pour voir les données a droite

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> afficherDetailsPersonne(newValue)
        );
        if(!personList.isEmpty()){
            btnSupprimer.setOnAction(e->supprimerPersonne(personList.get(tableView.getSelectionModel().getSelectedIndex())));
        }



    }

    // Ajouter une nouvelle personne (ex: via un bouton)
    public void addPerson(Personne person) {
        personList.add(person);
        JsonHandler.writePersonsToJson(personList);
    }

    private void afficherDetailsPersonne(Personne personne) {
        if (personne != null) {
            labelNom.setText(personne.getNom());
            labelPrenom.setText(personne.getPrenom());
            labelAge.setText(String.valueOf(personne.getAge()));
            labelFiche.setText(String.valueOf(personne.isFiche()));
        } else {
            labelNom.setText("");
            labelPrenom.setText("");
            labelAge.setText("");
            labelFiche.setText("");
        }
    }


    public void supprimerPersonne(Personne personne) {
        if (personne != null) {
            personList.remove(personne);
            JsonHandler.writePersonsToJson(personList);
        }
    }

    public void ajouterFenetre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
            Parent root = loader.load();

            Ajouter_Controlleur controller = loader.getController();
            controller.setPersonList(personList);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une personne");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            tableView.refresh(); // Rafraîchir le tableau
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ouvrirFenetreModification() {
        Personne selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
                Parent root = loader.load();

                Ajouter_Controlleur controller = loader.getController();
                controller.setPersonList(personList);
                controller.setPersonneAModifier(selectedPerson);

                Stage stage = new Stage();
                stage.setTitle("Modifier une personne");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();

                tableView.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}
