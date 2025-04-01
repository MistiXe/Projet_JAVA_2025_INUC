package com.example.demo.PDFJSON;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemoignageDesirializer extends JsonDeserializer<Map<Integer, List<Integer>>> {

    @Override
    public Map<Integer, List<Integer>> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ArrayNode arrayNode = p.getCodec().readTree(p);
        Map<Integer, List<Integer>> temoignages = new HashMap<>();

        // Transformez le tableau en une Map. Vous devrez peut-être adapter cette partie à votre format.
        for (int i = 0; i < arrayNode.size(); i++) {
            // Exemple pour des données qui sont dans un tableau d'ID de témoins
            temoignages.put(i, List.of(arrayNode.get(i).asInt()));  // Adapter selon la logique de votre JSON
        }

        return temoignages;
    }
}