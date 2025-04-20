package com.example.demo.JsonHandlers;

import com.example.demo.Patrons.Preuve;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class JsonHandlerPreuve {
    private static final String FILE_PATH = "/com/example/demo/BDD/liste_preuves.json"; // Chemin du fichier JSON

    // Lire les preuves depuis le JSON
    public static List<Preuve> readPreuvesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = JsonHandlerPreuve.class.getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            System.out.println("Fichier non trouvé : " + FILE_PATH);
            return null;
        }

        try {
            return objectMapper.readValue(inputStream, new TypeReference<List<Preuve>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sauvegarder la liste des preuves dans un fichier JSON
    public static void writePreuvesToJson(List<Preuve> preuves) {
        ObjectMapper objectMapper = new ObjectMapper();
        String outputFilePath = "liste_preuves_sauvegarde.json"; // Sauvegarde locale

        try (OutputStream outputStream = new FileOutputStream(outputFilePath)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, preuves);
            System.out.println("Données sauvegardées dans : " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
