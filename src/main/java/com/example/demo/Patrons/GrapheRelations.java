// Classe pour représenter le graphe de relations
package com.example.demo.Patrons;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;

public class GrapheRelations {
    private Pane graphPane;
    private List<Affaire> affaires;
    private List<Personne> personnes;
    private Map<Integer, Double> nodeXPositions = new HashMap<>();
    private Map<Integer, Double> nodeYPositions = new HashMap<>();

    public GrapheRelations(List<Affaire> affaires, List<Personne> personnes) {
        this.affaires = affaires;
        this.personnes = personnes;
        this.graphPane = new Pane();
        graphPane.setMinSize(800, 500);
        graphPane.setStyle("-fx-background-color: #f0f0f0;");
    }

    public Pane genererGraphe() {
        graphPane.getChildren().clear();
        nodeXPositions.clear();
        nodeYPositions.clear();

        // Création des nœuds pour chaque affaire
        Map<Integer, Circle> affaireNodes = new HashMap<>();
        Map<Integer, Circle> personneNodes = new HashMap<>();

        double centerX = graphPane.getMinWidth() / 2;
        double centerY = graphPane.getMinHeight() / 2;
        double radius = Math.min(centerX, centerY) * 0.8;

        // Placer les affaires en cercle
        for (int i = 0; i < affaires.size(); i++) {
            Affaire affaire = affaires.get(i);
            double angle = 2 * Math.PI * i / affaires.size();
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle node = createAffaireNode(affaire, x, y);
            graphPane.getChildren().add(node);

            // Ajouter un texte pour identifier l'affaire
            Text text = new Text(x - 15, y - 15, affaire.getType().substring(0, Math.min(10, affaire.getType().length())));
            graphPane.getChildren().add(text);

            affaireNodes.put(i, node);
            nodeXPositions.put(i, x);
            nodeYPositions.put(i, y);
        }

        // Tracer les liens entre les affaires qui partagent des suspects
        for (int i = 0; i < affaires.size(); i++) {
            for (int j = i + 1; j < affaires.size(); j++) {
                Set<Integer> suspectsCommunsIds = new HashSet<>(affaires.get(i).getSuspects());
                suspectsCommunsIds.retainAll(affaires.get(j).getSuspects());

                if (!suspectsCommunsIds.isEmpty()) {
                    Line line = new Line(
                            nodeXPositions.get(i), nodeYPositions.get(i),
                            nodeXPositions.get(j), nodeYPositions.get(j)
                    );
                    line.setStroke(Color.RED);
                    line.setStrokeWidth(suspectsCommunsIds.size() * 0.5 + 0.5);

                    Tooltip tooltip = new Tooltip("Suspects communs: " + suspectsCommunsIds.size());
                    Tooltip.install(line, tooltip);

                    graphPane.getChildren().add(line);
                }
            }
        }

        return graphPane;
    }

    private Circle createAffaireNode(Affaire affaire, double x, double y) {
        Circle circle = new Circle(x, y, 10);
        circle.setFill(getColorByGravity(affaire.getGravite()));

        Tooltip tooltip = new Tooltip(
                "Date: " + affaire.getDate() + "\n" +
                        "Lieu: " + affaire.getLieu() + "\n" +
                        "Type: " + affaire.getType() + "\n" +
                        "Gravité: " + affaire.getGravite() + "\n" +
                        "Statut: " + affaire.getStatus()
        );
        Tooltip.install(circle, tooltip);

        return circle;
    }

    private Color getColorByGravity(int gravite) {
        if (gravite >= 8) return Color.DARKRED;
        else if (gravite >= 5) return Color.RED;
        else if (gravite >= 3) return Color.ORANGE;
        else return Color.YELLOW;
    }
}