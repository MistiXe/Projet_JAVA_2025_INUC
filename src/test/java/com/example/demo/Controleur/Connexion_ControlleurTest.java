package com.example.demo.Controleur;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class Connexion_ControlleurTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // On lance l'écran de connexion, pas le main_view
        Connexion_Controlleur.launchApp(stage);
    }

    @Test
    public void testFieldsAreVisible() {
        // Vérifie que les trois nœuds existent et sont visibles
        verifyThat("#user", isVisible());
        verifyThat("#pass", isVisible());
        verifyThat("#connexion", isVisible());
    }

}
