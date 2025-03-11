package INUC2025.MVC;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Vue extends JFrame {
    private JTable table;
    private PersonneTableModel tableModel;

    public Vue(List<Personne> personnes) {
        setTitle("Liste des Personnes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tableModel = new PersonneTableModel(List.of(personnes.toArray(new Personne[0])));
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        List<Personne> personnes = JsonReader.lirePersonnesDepuisJson("src/INUC2025/MVC/BaseApplication.json");
        if (personnes != null) {
            new Vue(personnes);
        } else {
            System.out.println("Erreur lors du chargement du fichier JSON.");
        }
    }



}
