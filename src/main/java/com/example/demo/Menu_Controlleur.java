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
    @FXML private TableView<Affaire> tableView;
    @FXML private TableColumn<Affaire, String> columnDate;
    @FXML private TableColumn<Affaire, String> columnLieu;
    @FXML private TableColumn<Affaire, String> columnType;
    @FXML private TableColumn<Affaire, Boolean> columnStatus;
    @FXML private TableColumn<Affaire, Integer> columnGravite;

    @FXML private Button btnSupprimer;

    private final ObservableList<Affaire> listeAffaires = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Lier les colonnes aux attributs de Affaire
        columnLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnGravite.setCellValueFactory(new PropertyValueFactory<>("gravite"));
        // Formater la date dans la colonne
        columnDate.setCellValueFactory(param -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new javafx.beans.property.SimpleStringProperty(param.getValue().getDate().format(formatter));
        });

        // Charger les données du JSON
        List<Affaire> affaires = JsonHandlerCase.readCasesFromJson();
        if (affaires != null) {
            listeAffaires.addAll(affaires);
        }
        tableView.setItems(listeAffaires);

//        // Action pour voir les données à droite
//        tableView.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> afficherDetailsPersonne(newValue)
//        );

        // S'il y a des affaires judiciaires
        if (!listeAffaires.isEmpty()) {
            btnSupprimer.setOnAction(e -> supprimerAffaire(listeAffaires.get(tableView.getSelectionModel().getSelectedIndex())));
        }
    }


//    private void afficherDetailsPersonne(Personne personne) {
//        if (personne != null) {
//            labelPrenom.setText(personne.getPrenom());
//            labelDate.setText(personne.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//            labelFiche.setText(personne.getEtatAffaire().toString()); // Affichez le statut de l'affaire ici
//        } else {
//            labelPrenom.setText("");
//            labelDate.setText("");
//            labelFiche.setText("");
//        }
//    }

    public void supprimerAffaire(Affaire affaire) {
        if (affaire != null) {
            listeAffaires.remove(affaire);
            JsonHandlerCase.writePersonsToJson(listeAffaires);
        }
    }

    public void ajouterFenetre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
            Parent root = loader.load();

            Ajouter_Controlleur controller = loader.getController();
            controller.setAffaireList(listeAffaires);

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
        Affaire selectedAffaire = tableView.getSelectionModel().getSelectedItem();
        if (selectedAffaire != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
                Parent root = loader.load();

                Ajouter_Controlleur controller = loader.getController();
                controller.setAffaireList(listeAffaires);
                controller.setAffaireAModifier(selectedAffaire);

                Stage stage = new Stage();
                stage.setTitle("Modification - affaire " + selectedAffaire.getDate().toString());
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
