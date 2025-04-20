package com.example.demo.JsonHandlers;

import com.example.demo.Patrons.Personne;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class JsonHandlerPersonne {
    private static final String FILE_PATH = "/com/example/demo/BDD/liste_personnes.json"; // Correction du nom du fichier

    // Lire les personnes depuis le JSON
    public static List<Personne> readPersonsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = JsonHandlerPersonne.class.getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            System.out.println("Fichier non trouvé : " + FILE_PATH);
            return null;
        }

        try {
            return objectMapper.readValue(inputStream, new TypeReference<List<Personne>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sauvegarder la liste des personnes dans un fichier JSON
    public static void writePersonsToJson(List<Personne> personnes) {
        ObjectMapper objectMapper = new ObjectMapper();
        String outputFilePath = "liste_personnes_sauvegarde.json"; // Sauvegarde dans le dossier du projet

        try (OutputStream outputStream = new FileOutputStream(outputFilePath)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, personnes);
            System.out.println("Données sauvegardées dans : " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
