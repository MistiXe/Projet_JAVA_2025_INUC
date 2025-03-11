import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Vue {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Lire le fichier JSON
            File file = new File("BaseApplication.json");
            Personne[] personnes = mapper.readValue(file, Personne[].class);

            // Créer une JTable
            JTable table = new JTable(new PersonneTableModel(personnes));
            // Ajouter la JTable à une fenêtre (JFrame)
            JFrame frame = new JFrame();
            frame.add(new JScrollPane(table));
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
