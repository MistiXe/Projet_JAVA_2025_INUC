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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Menu_Controlleur {
    @FXML private TableView<Affaires> tableView;
    @FXML private TableColumn<Affaires, String> columnPrenom;
    @FXML private TableColumn<Affaires, String> columnDate;
    @FXML private TableColumn<Affaires, Boolean> columnFiche;

    @FXML
    private Label labelPrenom;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelFiche;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private final ObservableList<Affaires> personList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Lier les colonnes aux attributs de Person
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnFiche.setCellValueFactory(new PropertyValueFactory<>("etatAffaire"));

        // Formater la date dans la colonne
        columnDate.setCellValueFactory(param -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new javafx.beans.property.SimpleStringProperty(param.getValue().getDate().format(formatter));
        });

        // Charger les données du JSON
        List<Affaires> persons = JsonHandlerCase.readPersonsFromJson();
        if (persons != null) {
            personList.addAll(persons);
        }
        tableView.setItems(personList);

        // Action pour voir les données à droite
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> afficherDetailsPersonne(newValue)
        );

        if (!personList.isEmpty()) {
            btnSupprimer.setOnAction(e -> supprimerPersonne(personList.get(tableView.getSelectionModel().getSelectedIndex())));
        }
    }

    // Ajouter une nouvelle affaire (ex: via un bouton)
    public void addPerson(Affaires person) {
        personList.add(person);
        JsonHandlerCase.writePersonsToJson(personList);
    }

    private void afficherDetailsPersonne(Affaires personne) {
        if (personne != null) {
            labelPrenom.setText(personne.getPrenom());
            labelDate.setText(personne.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            labelFiche.setText(personne.getEtatAffaire().toString()); // Affichez le statut de l'affaire ici
        } else {
            labelPrenom.setText("");
            labelDate.setText("");
            labelFiche.setText("");
        }
    }

    public void supprimerPersonne(Affaires personne) {
        if (personne != null) {
            personList.remove(personne);
            JsonHandlerCase.writePersonsToJson(personList);
        }
    }

    public void ajouterFenetre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
            Parent root = loader.load();

            Ajouter_Controlleur controller = loader.getController();
            controller.setPersonList(personList);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une affaire");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            tableView.refresh(); // Rafraîchir le tableau
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ouvrirFenetreModification() {
        Affaires selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
                Parent root = loader.load();

                Ajouter_Controlleur controller = loader.getController();
                controller.setPersonList(personList);
                controller.setPersonneAModifier(selectedPerson);

                Stage stage = new Stage();
                stage.setTitle("Modifier une affaire");
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
