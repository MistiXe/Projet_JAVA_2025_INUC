package com.example.demo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonHandler {
    private static final String RESOURCE_PATH = "/com/example/demo/bdd.json";
    private static final String FILE_PATH = System.getProperty("user.home") + "/bdd.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Copier le fichier JSON de resources vers un endroit modifiable s'il n'existe pas encore
    private static void copyResourceToFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (InputStream inputStream = JsonHandler.class.getResourceAsStream(RESOURCE_PATH)) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("Fichier JSON introuvable dans les ressources : " + RESOURCE_PATH);
                }
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Fichier JSON copié dans : " + FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Lire les personnes depuis le fichier JSON copié
    public static List<Personne> readPersonsFromJson() {
        copyResourceToFile(); // Assure que le fichier existe avant de lire
        try (InputStream inputStream = new FileInputStream(FILE_PATH)) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Personne>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Écrire les mises à jour dans le fichier JSON copié
    public static void writePersonsToJson(List<Personne> persons) {
        try {
            File file = new File(FILE_PATH);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, persons);
            System.out.println("Données sauvegardées dans : " + FILE_PATH);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons);
            System.out.println("Contenu actuel du JSON :\n" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
