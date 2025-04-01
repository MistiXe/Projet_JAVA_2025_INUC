package com.example.demo.PDFJSON;

import com.example.demo.Patrons.Personne;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonHandlerPersonne {
    private static final String FILE_PATH = "/com/example/demo/liste_affaires.json";

    // Lire les personnes depuis le JSON
    public static List<Personne> readPersonsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Personne>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sauvegarder la liste des personnes dans le JSON
    public static void writePersonsToJson(List<Personne> personnes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), personnes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
