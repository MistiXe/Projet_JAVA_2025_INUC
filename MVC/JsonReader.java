package INUC2025.MVC;

import INUC2025.MVC.Personne;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    public static List<Personne> lirePersonnesDepuisJson(String cheminFichier) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(cheminFichier), new TypeReference<List<Personne>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
