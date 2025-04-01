package com.example.demo.PDFJSON;

import com.example.demo.Patrons.Affaire;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonHandlerCase {
    private static final String RESOURCE_PATH = "/com/example/demo/liste_affaires.json"; // Lecture depuis les ressources
    private static final String OUTPUT_FILE = "liste_affaires.json"; // Fichier pour sauvegarde
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Enregistrement du module pour gérer LocalDate correctement
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Lecture des affaires depuis un fichier JSON dans les ressources ou depuis le fichier externe si modifié.
     * @return Liste d'Affaires ou une liste vide en cas d'erreur.
     */
    public static List<Affaire> readCasesFromJson() {
        List<Affaire> affaires;
        // Vérifier si le fichier externe existe
        if (Files.exists(Paths.get(OUTPUT_FILE))) {
            // Si le fichier existe, lire depuis ce fichier
            try (InputStream inputStream = new FileInputStream(OUTPUT_FILE)) {
                affaires = objectMapper.readValue(inputStream, new TypeReference<List<Affaire>>() {});
            } catch (IOException e) {
                affaires = List.of();
            }
        } else {
            // Sinon, lire depuis les ressources (version par défaut)
            try (InputStream inputStream = JsonHandlerCase.class.getResourceAsStream(RESOURCE_PATH)) {
                if (inputStream == null) {
                    affaires = List.of();
                } else {
                   
                    affaires = objectMapper.readValue(inputStream, new TypeReference<List<Affaire>>() {});
                }
            } catch (IOException e) {
                affaires = List.of();
            }
        }
        return affaires;
    }

    /**
     * Écriture de la liste des affaires dans un fichier externe.
     * @param affaires Liste des affaires à sauvegarder.
     */
    public static void writePersonsToJson(List<Affaire> affaires) {
        try {
            // Vérifier si le fichier existe, sinon le créer
            if (!Files.exists(Paths.get(OUTPUT_FILE))) {
                Files.createFile(Paths.get(OUTPUT_FILE));
            }

            // Écriture dans le fichier
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(OUTPUT_FILE), affaires);
    

            // Affichage du contenu JSON dans la console (utile pour le débogage)
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(affaires);
            

        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}
