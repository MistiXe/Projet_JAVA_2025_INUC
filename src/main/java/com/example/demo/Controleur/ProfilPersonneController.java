// package com.example.demo;

// import com.example.demo.Patrons.Personne;
// import javafx.fxml.FXML;
// import javafx.scene.control.*;

// public class ProfilPersonneController {

//     @FXML private TextField txtNom;
//     @FXML private TextField txtPrenom;
//     @FXML private TextField txtAge;
//     @FXML private TextField txtEmail;
//     @FXML private TextField txtTelephone;
//     @FXML private TextField txtDateNaissance;
//     @FXML private TextField txtLieuNaissance;
//     @FXML private TextField txtGenre;
//     @FXML private TextField txtNationalite;
//     @FXML private TextField txtCasierJudiciaire;
//     @FXML private ComboBox<Personne.StatutLegal> comboStatutLegal;
//     @FXML private TextArea txtAdresses;
    
//     @FXML private Button btnEdit;
//     @FXML private Button btnSave;
//     @FXML private Button btnDelete;

//     private Personne personne;

//     // Cette méthode est appelée après que le fichier FXML soit chargé
//     public void initialize() {
//         // Initialisation avec un exemple de Personne
//         personne = new Personne(1, "John", "Doe", "123 Rue Exemple", "john.doe@mail.com", 1234567890L,
//                 java.time.LocalDate.of(1990, 5, 15), "Paris", "Homme", "Française");
        
//         // Remplir les champs avec les informations de la personne
//         txtNom.setText(personne.getNom());
//         txtPrenom.setText(personne.getPrenom());
//         txtAge.setText(String.valueOf(personne.getAge()));
//         txtEmail.setText(personne.getEmail());
//         txtTelephone.setText(String.valueOf(personne.getTelephone()));
//         txtDateNaissance.setText(personne.getDateNaissance().toString());
//         txtLieuNaissance.setText(personne.getLieuNaissance());
//         txtGenre.setText(personne.getGenre());
//         txtNationalite.setText(personne.getNationalite());
//         txtCasierJudiciaire.setText(personne.isCasierJudiciaire() ? "Oui" : "Non");
//         comboStatutLegal.setValue(personne.getStatutLegal());
//         txtAdresses.setText(String.join("\n", personne.getAdresses()));
//     }

//     // Gestion des actions des boutons
//     @FXML
//     private void handleEdit() {
//         txtNom.setEditable(true);
//         txtPrenom.setEditable(true);
//         // Autres champs à rendre éditables...
//     }

//     @FXML
//     private void handleSave() {
//         // Sauvegarde des modifications
//         personne.setNom(txtNom.getText());
//         personne.setPrenom(txtPrenom.getText());
//         // Sauvegarder les autres champs...
//     }

//     @FXML
//     private void handleDelete() {
//         // Action de suppression de la personne
//         System.out.println("Personne supprimée");
//     }
// }
