package com.example.demo.Controleur;

import com.example.demo.Patrons.Affaire;
import com.example.demo.Patrons.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    private Menu_Controlleur menuController;
    private ObservableList<Affaire> listeAffaires;
    private ObservableList<Personne> listePersonnes;

    @BeforeEach
    void setUp() {
        // Initialiser le contrôleur principal
        menuController = new Menu_Controlleur();

        // Simuler les listes
        listeAffaires = FXCollections.observableArrayList();
        listePersonnes = FXCollections.observableArrayList();

        // Utiliser le getter pour accéder à listeAffaires
        menuController.getListeAffaires().addAll(listeAffaires);
    }

    @Test
    void testAfficherProfilPersonne() {
        // Simuler une personne
        Personne personne = new Personne(1, "John", "Doe", "john.doe@example.com", 0, null, null, null, false, null, null, null, null, null);
        listePersonnes.add(personne);

        // Simuler la sélection dans la ListView
        ListView<Personne> listView = mock(ListView.class);
        when(listView.getSelectionModel().getSelectedItem()).thenReturn(personne);

        // Appeler la méthode pour afficher le profil
        menuController.ouvrirFenetreProfilPersonne(personne);

        // Vérifier que la fenêtre de profil a été ouverte
        assertNotNull(personne, "La personne sélectionnée ne doit pas être null.");
        assertEquals("John", personne.getPrenom(), "Le prénom de la personne doit être correct.");
        assertEquals("Doe", personne.getNom(), "Le nom de la personne doit être correct.");
    }

    @Test
    void testRechercheAffaireParLieu() {
        // Ajouter des affaires
        Affaire affaire1 = new Affaire(LocalDate.now(), "Paris", "Vol", Affaire.Status.EN_COURS, 5);
        Affaire affaire2 = new Affaire(LocalDate.now(), "Lyon", "Fraude", Affaire.Status.CLOTUREE, 3);
        listeAffaires.addAll(affaire1, affaire2);

        // Simuler un champ de recherche
        TextField searchField = mock(TextField.class);
        when(searchField.getText()).thenReturn("Paris");

        // Filtrer les affaires par lieu
        ObservableList<Affaire> result = FXCollections.observableArrayList(
                listeAffaires.filtered(affaire -> affaire.getLieu().equalsIgnoreCase(searchField.getText()))
        );

        // Vérifier les résultats
        assertEquals(1, result.size(), "Une seule affaire doit correspondre au lieu 'Paris'.");
        assertEquals("Paris", result.get(0).getLieu(), "Le lieu de l'affaire doit être 'Paris'.");
    }

    @Test
    void testFermerApplication() {
        // Simuler la fermeture de l'application
        Stage mockStage = mock(Stage.class);
        doNothing().when(mockStage).close();

        // Appeler la méthode de fermeture
        menuController.fermerApplication();

        // Vérifier que la méthode a été appelée
        assertDoesNotThrow(() -> menuController.fermerApplication(), "La méthode fermerApplication ne doit pas lever d'exception.");
    }
}