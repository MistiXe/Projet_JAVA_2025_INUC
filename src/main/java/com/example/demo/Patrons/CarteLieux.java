// Classe pour gérer la carte
package com.example.demo.Patrons;

import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;
import java.util.stream.Collectors;

public class CarteLieux {
    private Pane mapPane;
    private final ImageView mapView;
    private final List<Affaire> affaires;
    private final Map<String, Integer> lieuOccurrences = new HashMap<>();

    public CarteLieux(List<Affaire> affaires) {
        this.affaires = affaires;
        this.mapPane = new Pane();

        // Charger une image de fond de carte
        mapView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/demo/map_france.png")));
        mapView.setFitWidth(800);
        mapView.setFitHeight(600);
        mapView.setPreserveRatio(true);

        mapPane.getChildren().add(mapView);

        // Calculer les occurrences pour chaque lieu
        calculerOccurrences();
    }

    private void calculerOccurrences() {
        lieuOccurrences.clear();

        for (Affaire affaire : affaires) {
            String lieu = affaire.getLieu();
            if (lieu != null && !lieu.isEmpty()) {
                lieuOccurrences.put(lieu, lieuOccurrences.getOrDefault(lieu, 0) + 1);
            }
        }
    }

    public Pane genererCarte() {
        mapPane.getChildren().clear();
        mapPane.getChildren().add(mapView);

        // Positions prédéfinies pour certaines villes (à personnaliser)
        Map<String, double[]> positions = new HashMap<>();
        positions.put("paris", new double[]{400, 200});
        positions.put("lyon", new double[]{450, 300});
        positions.put("marseille", new double[]{450, 400});
        positions.put("toulouse", new double[]{300, 400});
        positions.put("lille", new double[]{400, 100});
        positions.put("bordeaux", new double[]{250, 350});
        positions.put("nice", new double[]{550, 380});

        // Ajouter des marqueurs pour chaque lieu avec des occurrences
        for (Map.Entry<String, Integer> entry : lieuOccurrences.entrySet()) {
            String lieu = entry.getKey().toLowerCase();
            int occurrences = entry.getValue();

            // Rechercher le lieu dans les positions connues
            double[] position = null;
            for (Map.Entry<String, double[]> posEntry : positions.entrySet()) {
                if (lieu.contains(posEntry.getKey())) {
                    position = posEntry.getValue();
                    break;
                }
            }

            // Si le lieu n'est pas trouvé, utiliser une position aléatoire
            if (position == null) {
                position = new double[]{
                        100 + Math.random() * 600,
                        100 + Math.random() * 400
                };
            }

            // Création du marqueur
            Circle marker = createMarker(lieu, position[0], position[1], occurrences);
            mapPane.getChildren().add(marker);
        }

        return mapPane;
    }

    private Circle createMarker(String lieu, double x, double y, int occurrences) {
        double radius = Math.min(20, 5 + (occurrences * 3));
        Circle circle = new Circle(x, y, radius);

        // Couleur basée sur le nombre d'occurrences
        if (occurrences >= 5) {
            circle.setFill(Color.rgb(200, 0, 0, 0.7)); // Rouge pour les zones à risque élevé
        } else if (occurrences >= 3) {
            circle.setFill(Color.rgb(200, 100, 0, 0.7)); // Orange pour risque moyen
        } else {
            circle.setFill(Color.rgb(0, 100, 200, 0.7)); // Bleu pour risque faible
        }

        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(1);
        circle.setCursor(Cursor.HAND);

        // Informations au survol
        Tooltip tooltip = new Tooltip(lieu.toUpperCase() + "\n" + occurrences + " affaire(s)");
        Tooltip.install(circle, tooltip);

        // Liste des affaires pour ce lieu
        List<Affaire> affairesLieu = affaires.stream()
                .filter(a -> a.getLieu() != null && a.getLieu().toLowerCase().contains(lieu.toLowerCase()))
                .collect(Collectors.toList());

        circle.setOnMouseClicked(event -> {
            System.out.println("Lieu: " + lieu + " - " + occurrences + " affaires");
            for (Affaire a : affairesLieu) {
                System.out.println(" - " + a.getDate() + ": " + a.getType());
            }
        });

        return circle;
    }
}