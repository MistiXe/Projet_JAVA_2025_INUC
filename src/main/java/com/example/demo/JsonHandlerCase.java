package com.example.demo;

import java.io.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonHandlerCase {
    private static final String RESOURCE_PATH = "/com/example/demo/liste_affaires.json"; // Chemin dans les ressources
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Enregistrement du module JavaTimeModule pour gérer LocalDate
        objectMapper.registerModule(new JavaTimeModule());
        // Désactivation de la fonctionnalité d'écriture des dates sous forme de timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Lire les affaires depuis le fichier JSON dans les ressources
    public static List<Affaires> readPersonsFromJson() {
        try (InputStream inputStream = JsonHandlerCase.class.getResourceAsStream(RESOURCE_PATH)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Fichier JSON introuvable dans les ressources : " + RESOURCE_PATH);
            }
            System.out.println("Lecture du fichier JSON depuis les ressources : " + RESOURCE_PATH);
            List<Affaires> affairesList = objectMapper.readValue(inputStream, new TypeReference<List<Affaires>>() {});
            System.out.println("Données lues : " + affairesList);
            return affairesList;
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            e.printStackTrace();
            return List.of();  // Retourne une liste vide en cas d'erreur
        }
    }

    // Écrire les mises à jour dans un fichier JSON externe
    public static void writePersonsToJson(List<Affaires> affaires) {
        try {
            // Spécification d'un fichier externe pour l'écriture
            File file = new File("liste_affaires.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, affaires);
            System.out.println("Données sauvegardées dans : " + file.getPath());
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(affaires);
            System.out.println("Contenu actuel du JSON :\n" + json);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier JSON : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
