    package com.example.demo.Controlleur;
    
    import com.example.demo.PDFJSON.Affaire;
    import com.example.demo.PDFJSON.JsonHandlerCase;
    import com.example.demo.Patrons.Personne;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
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
    import java.time.format.DateTimeFormatter;
    import java.util.List;
    
    
    
    
    public class Menu_Controlleur {
    //    @FXML private TableView<Personne> tableView;
    //    @FXML private TableColumn<Personne, String> columnPrenom;
    //    @FXML private TableColumn<Personne, String> columnDate;
    //    @FXML private TableColumn<Personne, Boolean> columnFiche;
        @FXML private BarChart<String, Number> barChart;
        @FXML private TableView<Affaire> tableView;
        @FXML private TableColumn<Affaire, String> columnDate;
        @FXML private TableColumn<Affaire, String> columnLieu;
        @FXML private TableColumn<Affaire, String> columnType;
        @FXML private TableColumn<Affaire, Boolean> columnStatus;
    
        @FXML private MenuItem convertPDF;
        @FXML private MenuItem printTable;
    
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

        @FXML private Pane graphContainer;
    
        private final ObservableList<Personne> personList = FXCollections.observableArrayList();
        private final ObservableList<Affaire> listeAffaires = FXCollections.observableArrayList();
    
        @FXML
        private void initialize() {




            ImageView pdfIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/demo/format-de-fichier-pdf.png")));
            pdfIcon.setFitWidth(16);
            pdfIcon.setFitHeight(16);
            convertPDF.setGraphic(pdfIcon);

            ImageView printIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/demo/imprimante.png")));
            printIcon.setFitWidth(16);
            printIcon.setFitHeight(16);
            printTable.setGraphic(printIcon);


            // Lier les colonnes aux attributs de Person
            columnLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            // Formater la date dans la colonne
            columnDate.setCellValueFactory(param -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return new javafx.beans.property.SimpleStringProperty(param.getValue().getDate().format(formatter));
            });
    
            // Charger les données du JSON
            List<Affaire> affaires = JsonHandlerCase.readPersonsFromJson();
            if (affaires != null) {
                listeAffaires.addAll(affaires);
            }
            tableView.setItems(listeAffaires);
    
    //        // Action pour voir les données à droite
    //        tableView.getSelectionModel().selectedItemProperty().addListener(
    //                (observable, oldValue, newValue) -> afficherDetailsPersonne(newValue)
    //        );
    
            // S'il n'y a plus d'affaire judiciaire
            if (!listeAffaires.isEmpty()) {
                btnSupprimer.setOnAction(e -> supprimerAffaire(listeAffaires.get(tableView.getSelectionModel().getSelectedIndex())));
            }
        }
    
        // Ajouter une nouvelle affaire (ex: via un bouton)
        public void addPerson(Affaire affaire) {
            listeAffaires.add(affaire);
            JsonHandlerCase.writePersonsToJson(listeAffaires);
        }
    
        private void afficherDetailsPersonne(Personne personne) {
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
                controller.setPersonList(listeAffaires);
    
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

                    // Position initiale
                    float margin = 50;
                    float yStart = 750;
                    float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                    float yPosition = yStart;
                    float rowHeight = 20;
                    float colWidth = tableWidth / 4;

                    // Dessiner le titre
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream.newLineAtOffset(margin + 100, yPosition);
                    contentStream.showText("Compte Rendu de l'Affaire Judiciaire");
                    contentStream.endText();
                    yPosition -= 40;

                    // Colonnes
                    String[] headers = {"Date", "Lieu", "Type", "Statut"};
                    String[] values = {
                            affaireSelectionnee.getDate().toString(),
                            affaireSelectionnee.getLieu(),
                            affaireSelectionnee.getType(),
                            affaireSelectionnee.getStatus().toString()
                    };

                    // Dessiner l'en-tête
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

                    // Dessiner la ligne sous l'en-tête
                    contentStream.moveTo(margin, yPosition);
                    contentStream.lineTo(margin + tableWidth, yPosition);
                    contentStream.stroke();
                    yPosition -= rowHeight;

                    // Ajouter les valeurs
                    for (int i = 0; i < values.length; i++) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + (i * colWidth) + 5, yPosition + 5);
                        contentStream.showText(values[i]);
                        contentStream.endText();
                    }

                    // Dessiner la ligne sous la ligne de données
                    contentStream.moveTo(margin, yPosition);
                    contentStream.lineTo(margin + tableWidth, yPosition);
                    contentStream.stroke();

                    contentStream.close();

                    // Sauvegarde du PDF
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



        @FXML private Button btnImprimer;

        @FXML
        private void imprimerTable() {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                boolean proceed = job.showPrintDialog(tableView.getScene().getWindow()); // Ouvre la boîte de dialogue d'impression
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
            barChart.getData().clear(); // Nettoyer le graphique existant

            // Création des données
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Affaires par type de crime");

            // Ajouter des données fictives (remplace par des données de ta base)
            series.getData().add(new XYChart.Data<>("Vol", 10));
            series.getData().add(new XYChart.Data<>("Fraude", 7));
            series.getData().add(new XYChart.Data<>("Homicide", 4));
            series.getData().add(new XYChart.Data<>("Cybercrime", 6));

            // Ajouter la série au graphique
            barChart.getData().add(series);
        }




        private void showAlert(String titre, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titre);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    //    public void ouvrirFenetreModification() {
    //        Personne selectedPerson = tableView.getSelectionModel().getSelectedItem();
    //        if (selectedPerson != null) {
    //            try {
    //                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ajouter_view.fxml"));
    //                Parent root = loader.load();
    //
    //                Ajouter_Controlleur controller = loader.getController();
    //                controller.setPersonList(personList);
    //                controller.setPersonneAModifier(selectedPerson);
    //
    //                Stage stage = new Stage();
    //                stage.setTitle("Modifier une affaire");
    //                stage.initModality(Modality.APPLICATION_MODAL);
    //                stage.setScene(new Scene(root));
    //                stage.showAndWait();
    //
    //                tableView.refresh();
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    }
