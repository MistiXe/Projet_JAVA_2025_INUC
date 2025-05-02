package com.example.demo.Controleur;

import com.example.demo.Patrons.Affaire;
import com.example.demo.Patrons.Personne;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest {

    private Menu_Controlleur menuController;

    @BeforeAll
    static void initJavaFX() {
        // Initialise le toolkit JavaFX une seule fois
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        // Utilise le constructeur de test (à ajouter dans Menu_Controlleur)
        menuController = new Menu_Controlleur(true);
    }

    /**
     * Utility to run actions on the JavaFX Application Thread and wait for completion.
     */
    private void runOnFxThreadAndWait(Runnable action) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    void testAfficherProfilPersonne() throws Exception {
        // Prépare une personne avec un statut non-null
        Personne personne = new Personne(
                1, "John", "Doe", "john.doe@example.com",
                1234567890L, "Paris", "Homme", "Française",
                false, Personne.StatutLegal.SUSPECT, "Desc", null, null, null
        );

        // Exécuter l'ouverture de la fenêtre sur le thread JavaFX
        runOnFxThreadAndWait(() -> {
            assertDoesNotThrow(() -> menuController.ouvrirFenetreProfilPersonne(personne));
        });
    }

    @Test
    void testRechercheAffaireParLieu() {
        // Créer et ajouter des affaires
        Affaire affaire1 = new Affaire(LocalDate.now(), "Paris", "Vol", Affaire.Status.EN_COURS, 5);
        Affaire affaire2 = new Affaire(LocalDate.now(), "Lyon", "Fraude", Affaire.Status.CLOTUREE, 3);
        menuController.getListeAffaires().addAll(affaire1, affaire2);

        // Simuler une recherche
        TextField searchField = new TextField("Paris");
        var result = menuController.getListeAffaires().filtered(
                a -> a.getLieu().equalsIgnoreCase(searchField.getText())
        );

        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getLieu());
    }

    @Test
    void testFermerApplication() throws Exception {
        runOnFxThreadAndWait(() -> {
            Stage stage = new Stage();
            menuController.setStage(stage);
            assertDoesNotThrow(() -> menuController.fermerApplication());
        });
    }
}
