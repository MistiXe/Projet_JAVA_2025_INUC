import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Vue {
    public static void main(String[] args) {
        // Liste de personnes
        List<Personne> personnes = new ArrayList<>();

        try {
            // Lecture du fichier CSV
            BufferedReader reader = new BufferedReader(new FileReader("personnes.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String nom = parts[0].trim();
                    String prenom = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());

                    // Extraire les affaires (les autres éléments du CSV après l'âge)
                    List<String> affaires = new ArrayList<>();
                    for (int i = 3; i < parts.length; i++) {
                        affaires.add(parts[i].trim());
                    }

                    Personne personne = new Personne(nom, prenom, age, affaires);
                    personnes.add(personne);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Conversion de la liste en tableau pour le modèle de la JTable
        Personne[] personnesArray = personnes.toArray(new Personne[0]);

        // Création du modèle de table
        PersonneTableModel model = new PersonneTableModel(personnesArray);

        // Création de la JTable
        JTable table = new JTable(model);

        // Création de la fenêtre et ajout de la JTable
        JFrame frame = new JFrame("Liste des Personnes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
