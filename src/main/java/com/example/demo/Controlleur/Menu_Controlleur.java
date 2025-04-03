package com.example.demo.Controlleur;

import com.example.demo.PDFJSON.JsonHandlerPreuve;
import com.example.demo.Patrons.Affaire;
import com.example.demo.Patrons.Personne;
import com.example.demo.Patrons.Preuve;
import com.example.demo.PDFJSON.JsonHandlerCase;
import com.example.demo.PDFJSON.JsonHandlerPersonne;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu_Controlleur {
    //============================================
    // Déclarations FXML
    //============================================
    @FXML private BarChart<String, Number> barChart;
    @FXML private TableView<Affaire> tableView;
    @FXML private TableColumn<Affaire, String> columnDate;
    @FXML private TableColumn<Affaire, String> columnLieu;
    @FXML private TableColumn<Affaire, String> columnType;
    @FXML private TableColumn<Affaire, Boolean> columnStatus;
    @FXML private TableColumn<Affaire, Integer> columnGravite;

    @FXML private TextArea detailDescription;
    @FXML private TextArea detailEtat;

    @FXML private ListView<Personne> detailEnqueteurs;
    @FXML private ListView<Personne> detailSuspects;
    @FXML private ListView<Personne> detailTemoins;
    @FXML private ListView<Personne> detailPersonneSuspectees;
    @FXML private ListView<Preuve> detailPreuves;

    @FXML private MenuItem convertPDF;
    @FXML private MenuItem printTable;
    @FXML private Pane graphContainer;
    @FXML private Label labelSuspecteePar;
    @FXML private Button btnSupprimer;
    @FXML private Button btnImprimer;

    @FXML private TabPane tabPane;
    @FXML private Tab tabGraph;
    @FXML private Tab tabCollab;

    @FXML private TextField searchEnqueteurAffaire;
    @FXML private TextField searchSuspectAffaire;
    @FXML private TextField searchTemoinAffaire;
    @FXML private TextField searchPersonneSuspecteeAffaire;
    @FXML private TextField searchPreuvesAffaire;

    //============================================
    // Variables d'instance
    //============================================
    private Affaire currentAffaire;
    private Map<Integer,List<Integer>> currentTemoigagne;
    private final ObservableList<Affaire> listeAffaires = FXCollections.observableArrayList();
    private final List<Personne> listePersonnes = JsonHandlerPersonne.readPersonsFromJson();
    private final List<Preuve> listePreuves = JsonHandlerPreuve.readPreuvesFromJson();

    @FXML private ObservableList<Personne> enqueteursList = FXCollections.observableArrayList();
    @FXML private ObservableList<Personne> suspectsList = FXCollections.observableArrayList();
    @FXML private ObservableList<Personne> temoinsList = FXCollections.observableArrayList();
    @FXML private ObservableList<Personne> suspecteesList = FXCollections.observableArrayList();
    @FXML private ObservableList<Preuve> preuvesList = FXCollections.observableArrayList();

    // Création des FilteredList pour chaque liste de personnes
    private FilteredList<Personne> filteredEnqueteurs = new FilteredList<>(enqueteursList, p -> true);
    private FilteredList<Personne> filteredSuspects = new FilteredList<>(suspectsList, p -> true);
    private FilteredList<Personne> filteredTemoins = new FilteredList<>(temoinsList, p -> true);
    private FilteredList<Personne> filteredPersonnesSuspectees = new FilteredList<>(suspecteesList, p -> true);
    private FilteredList<Preuve> filteredPreuves = new FilteredList<>(preuvesList, p -> true);

    //============================================
    // Méthodes d'initialisation
    //============================================
    @FXML
    private void initialize() {
        initialiserIconesMenu();
        configurerTableView();
        configurerEcouteurs();
        chargerDonnees();
        configurerSelection();
    }

    private void initialiserIconesMenu() {
        ImageView pdfIcon = new ImageView(
                new Image(getClass().getResourceAsStream("/com/example/demo/format-de-fichier-pdf.png")));
        pdfIcon.setFitWidth(16);
        pdfIcon.setFitHeight(16);
        convertPDF.setGraphic(pdfIcon);

        ImageView printIcon = new ImageView(
                new Image(getClass().getResourceAsStream("/com/example/demo/imprimante.png")));
        printIcon.setFitWidth(16);
        printIcon.setFitHeight(16);
        printTable.setGraphic(printIcon);
    }

    private void configurerTableView() {
        columnLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnGravite.setCellValueFactory(new PropertyValueFactory<>("gravite"));
        columnDate.setCellValueFactory(param -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new SimpleStringProperty(param.getValue().getDate().format(formatter));
        });
    }

    private void configurerEcouteurs() {
        detailDescription.textProperty().addListener((obs, oldText, newText) -> {
            if (currentAffaire != null && !newText.equals(oldText)) {
                currentAffaire.setDescription(newText);
                sauvegarderDonnees();
            }
        });

        detailTemoins.setOnMouseClicked(event -> {
            Personne selectedItem = detailTemoins.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 2) {
                // Permet d'afficher la vue d'une carte d'une personne seulement si la personne existe (aucun double clique dans une listview vide pris en compte)
                if (selectedItem != null) {
                    System.out.println("Double-clic détecté sur : " + selectedItem);
                }
            }
        });

        // Écouteurs pour les champs de recherche dans le détail d'une affaire (suspects, témoins, enquêteurs...)
        setupSearchFilter(searchEnqueteurAffaire, filteredEnqueteurs);
        setupSearchFilter(searchSuspectAffaire, filteredSuspects);
        setupSearchFilter(searchTemoinAffaire, filteredTemoins);
        setupSearchFilter(searchPersonneSuspecteeAffaire, filteredPersonnesSuspectees);

        // Ajout d'un listener sur le champ de recherche
        searchPreuvesAffaire.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPreuves.setPredicate(preuve -> {
                // Si le champ est vide, afficher toute la liste
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Conversion du texte en minuscule pour éviter la casse
                String lowerCaseFilter = newValue.toLowerCase();

                // Vérification si la description contient le texte saisi
                return preuve.getDescription().toLowerCase().contains(lowerCaseFilter);
            });
        });

    }

    private void chargerDonnees() {
        List<Affaire> affaires = JsonHandlerCase.readCasesFromJson();
        if (affaires != null) {
            listeAffaires.addAll(affaires);
        }
        tableView.setItems(listeAffaires);
    }

    private void configurerSelection() {
        // Mode de sélection : unique
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Liens entre les listes 'java' et les éléments FXML
        detailEnqueteurs.setItems(filteredEnqueteurs);
        detailSuspects.setItems(filteredSuspects);
        detailTemoins.setItems(filteredTemoins);
        detailPersonneSuspectees.setItems(filteredPersonnesSuspectees);
        detailPreuves.setItems(filteredPreuves);

        // Permet de supprimer une affaire de la liste des affaires
        if (!listeAffaires.isEmpty()) {
            btnSupprimer.setOnAction(e -> supprimerAffaire(listeAffaires.get(tableView.getSelectionModel().getSelectedIndex())));
        }


        // Permet d'afficher les personnes suspectées par le témoin sélectionné
        detailTemoins.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    afficherPersonneSuspectee(newValue);
                });


        // Affiche le détail de l'affaire sélectionnée
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentAffaire = newSelection;

            afficherDescription();
            afficherDetailsEtat();
            afficherEnqueteurs();
            afficherSuspects();
            afficherPreuves();

            currentTemoigagne = currentAffaire.getTemoignages();
            afficherTemoins(currentTemoigagne);
        });
    }

    //============================================
    // Méthodes d'affichage
    //============================================
    private void afficherEnqueteurs() {
        enqueteursList.clear();

        if (currentAffaire != null) {
            List<Integer> enqueteursId = currentAffaire.getEnqueteurs();
            List<Personne> enqueteurs = listIDToListPersonne(enqueteursId);
            enqueteursList.setAll(enqueteurs);
        }
    }

    private void afficherSuspects() {
        suspectsList.clear();

        if (currentAffaire != null) {
            List<Integer> suspectsId = currentAffaire.getSuspects();
            List<Personne> suspects = listIDToListPersonne(suspectsId);
            suspectsList.setAll(suspects);
        }
    }

    private void afficherPreuves() {
        preuvesList.clear();

        if (currentAffaire != null) {
            List<String> preuvesId = currentAffaire.getPreuves();
            List<Preuve> preuves = listIDToListPreuve(preuvesId);
            preuvesList.setAll(preuves);
        }
    }

    private void afficherDescription() {
        if (currentAffaire.getDescription() != null) {
            detailDescription.setText(currentAffaire.getDescription());
        } else {
            detailDescription.setText("");
            detailDescription.setPromptText("Aucune description disponible pour cette affaire.");
        }
    }

    private void afficherDetailsEtat() {
        if (currentAffaire.getStatus() != null) {
            detailEtat.setText(currentAffaire.getStatus().toString());
        } else {
            detailEtat.setText("");
            detailEtat.setPromptText("Aucun état n'est renseigné pour cette affaire.");
        }
    }

    private void afficherTemoins(Map<Integer, List<Integer>> temoignages) {
        temoinsList.clear();
        
        if (temoignages != null) {
            for (Map.Entry<Integer, List<Integer>> entry : temoignages.entrySet()) {
                Integer idTemoin = entry.getKey();
                Personne temoin = trouverPersonneParId(idTemoin);

                temoinsList.add(temoin);
            }
        } else {
            System.out.println("Aucun témoignage disponible.");
        }
    }

    private void afficherPersonneSuspectee(Personne temoin) {
        suspecteesList.clear();

        if (temoin != null) {
            int temoinId = temoin.getId();
            List<Integer> suspectsIds = currentTemoigagne.get(temoinId);

            for (Integer suspectId : suspectsIds) {
                Personne suspect = trouverPersonneParId(suspectId);
                if (suspect != null) {
                    suspecteesList.add(suspect);
                }
            }
        }
    }

    //============================================
    // Méthodes utilitaires
    //============================================
    private void sauvegarderDonnees() {
        new Thread(() -> {
            try {
                JsonHandlerCase.writePersonsToJson(listeAffaires);
            } catch (Exception e) {
                Platform.runLater(() ->
                        showAlert("Erreur", "Échec sauvegarde : " + e.getMessage())
                );
            }
        }).start();
    }

    private void clearLists() {
        enqueteursList.clear();
        suspectsList.clear();
        temoinsList.clear();
        suspecteesList.clear();
        preuvesList.clear();
    }

    private List<Personne> listIDToListPersonne(List<Integer> listId) {
        List<Personne> personnes = new ArrayList<Personne>();

        for (Integer personneId : listId) {
            Personne personne = trouverPersonneParId(personneId);

            personnes.add(personne);
        }

        return personnes;
    }

    private List<Preuve> listIDToListPreuve(List<String> listId) {
        List<Preuve> preuves = new ArrayList<>();

        for (String preuveDescription : listId) {
            Preuve preuve = trouverPreuveParDescription(preuveDescription);
            if (preuve != null) {
                preuves.add(preuve);
            }
        }

        return preuves;
    }


    private Personne trouverPersonneParId(int id) {
        for (Personne p : listePersonnes) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    private Preuve trouverPreuveParDescription(String description) {
        for (Preuve p : listePreuves) {
            if (p.getDescription().equalsIgnoreCase(description)) {
                return p;
            }
        }
        return null; // Retourne null si la preuve n'est pas trouvée
    }


    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupSearchFilter(TextField searchField, FilteredList<Personne> filteredList) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(personne -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase().trim();
                String fullName = personne.getNom().toLowerCase() + " " + personne.getPrenom().toLowerCase();
                return personne.getNom().toLowerCase().contains(lowerCaseFilter) ||
                        personne.getPrenom().toLowerCase().contains(lowerCaseFilter) ||
                        fullName.contains(lowerCaseFilter);
            });
        });
    }


    //============================================
    // Méthodes d'action
    //============================================
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

            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void convertirEnPDF() {
        Affaire affaireSelectionnee = tableView.getSelectionModel().getSelectedItem();
        if (affaireSelectionnee != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

                float margin = 50;
                float yStart = 750;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 20;
                float colWidth = tableWidth / 4;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(margin + 100, yPosition);
                contentStream.showText("Compte Rendu de l'Affaire Judiciaire");
                contentStream.endText();
                yPosition -= 40;

                String[] headers = { "Date", "Lieu", "Type", "Statut" };
                String[] values = {
                        affaireSelectionnee.getDate().toString(),
                        affaireSelectionnee.getLieu(),
                        affaireSelectionnee.getType(),
                        affaireSelectionnee.getStatus().toString()
                };

                contentStream.setLineWidth(1);
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
                yPosition -= rowHeight;

                for (int i = 0; i < headers.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + (i * colWidth) + 5, yPosition + 5);
                    contentStream.showText(headers[i]);
                    contentStream.endText();
                }

                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
                yPosition -= rowHeight;

                for (int i = 0; i < values.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + (i * colWidth) + 5, yPosition + 5);
                    contentStream.showText(values[i]);
                    contentStream.endText();
                }

                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
                contentStream.close();

                String cheminSortie = "compte_rendu_affaire " + affaireSelectionnee.getDate() + ".pdf";
                document.save(cheminSortie);
                document.close();

                showAlert("Succès", "Le PDF de l'affaire sélectionnée a été généré avec succès !");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur est survenue lors de la génération du PDF.");
            }
        } else {
            showAlert("Alerte", "Veuillez sélectionner une affaire avant de convertir en PDF.");
        }
    }

    @FXML
    private void imprimerTable() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean proceed = job.showPrintDialog(tableView.getScene().getWindow());
            if (proceed) {
                boolean success = job.printPage(tableView);
                if (success) {
                    job.endJob();
                    showAlert("Succès", "Impression terminée avec succès !");
                } else {
                    showAlert("Erreur", "Erreur lors de l'impression.");
                }
            }
        }
    }

    @FXML
    public void genererGraphique() {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Affaires par type de crime");

        series.getData().add(new XYChart.Data<>("Vol", 10));
        series.getData().add(new XYChart.Data<>("Fraude", 7));
        series.getData().add(new XYChart.Data<>("Homicide", 4));
        series.getData().add(new XYChart.Data<>("Cybercrime", 6));

        barChart.getData().add(series);
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

    //============================================
    // Méthodes de gestion des onglets
    //============================================
    @FXML
    private void afficherTabPane() {
        tabPane.setManaged(true);
        tabPane.setVisible(true);
    }

    @FXML
    private void cacherTabPane() {
        tabPane.setManaged(false);
        tabPane.setVisible(false);
    }

    @FXML
    private void afficherGraphique() {
        afficherTabPane();
        tabPane.getSelectionModel().select(0);
    }

    @FXML
    private void afficherCollaboration() {
        afficherTabPane();
        tabPane.getSelectionModel().select(1);
    }

    @FXML
    private void toggleGraphique() {
        if (tabPane.isVisible() && tabPane.getSelectionModel().getSelectedItem() == tabGraph) {
            cacherTabPane();
        } else {
            afficherGraphique();
        }
    }

    @FXML
    private void toggleCollaboration() {
        if (tabPane.isVisible() && tabPane.getSelectionModel().getSelectedItem() == tabCollab) {
            cacherTabPane();
        } else {
            afficherCollaboration();
        }
    }
}