package com.example.demo.Controleur;

import com.example.demo.Controleur.Ajouter_Controlleur;
import com.example.demo.Patrons.Affaire;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;  


public class AjoutControllerTest {
    private Ajouter_Controlleur controlleur;
    private List<Affaire> listeAffaire;

      @BeforeAll
    public static void initJfxRuntime() {
        new JFXPanel(); // Initialise JavaFX Toolkit
        Platform.setImplicitExit(false);
    }

    
    @BeforeEach
    void setUp() {
        controlleur = new Ajouter_Controlleur();
        listeAffaire = new ArrayList<>();

        // Simuler les composants JavaFX
        controlleur.datePicker = new DatePicker();
        controlleur.lieuField = new TextField();
        controlleur.typeField = new TextField();
        controlleur.statusComboBox = new ComboBox<>();
        controlleur.graviteSpinner = new Spinner<>(1, 10, 1);
        controlleur.btnAjouter = new Button();
        controlleur.ajouterViewTitle = new Label();
    }

    @Test
    void testSetAffaireList() {
        controlleur.setAffaireList(listeAffaire);
        assertEquals(listeAffaire, controlleur.listeAffaire);
    }

    @Test
    void testSetAffaireAModifier() {
        Affaire affaire = new Affaire(LocalDate.now(), "Paris", "Vol", Affaire.Status.EN_COURS, 5);
        controlleur.setAffaireAModifier(affaire);

        assertEquals(affaire.getDate(), controlleur.datePicker.getValue());
        assertEquals(affaire.getLieu(), controlleur.lieuField.getText());
        assertEquals(affaire.getType(), controlleur.typeField.getText());
        assertEquals(affaire.getStatus(), controlleur.statusComboBox.getValue());
        assertEquals(affaire.getGravite(), controlleur.graviteSpinner.getValue());
        assertEquals("Modifier", controlleur.btnAjouter.getText());
    }
    
}
