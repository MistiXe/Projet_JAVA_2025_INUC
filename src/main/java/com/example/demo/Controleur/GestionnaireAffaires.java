package com.example.demo.Controleur;

import com.example.demo.Patrons.Affaire;

import java.util.*;
import java.util.stream.Collectors;

public class GestionnaireAffaires {

    private static List<Affaire> listeAffaires = new ArrayList<>();

    private static Map<String, List<Affaire>> affairesParLieu = new HashMap<>();
    private static Map<String, List<Affaire>> affairesParType = new HashMap<>();
    private static EnumMap<Affaire.Status, List<Affaire>> affairesParStatus = new EnumMap<>(Affaire.Status.class);
    private static TreeMap<Integer, List<Affaire>> affairesParGravite = new TreeMap<>();

    // Constructeur vide
    public GestionnaireAffaires() {}

    // Chargement initial des affaires (ex: après lecture JSON)
    public static void chargerAffaires(List<Affaire> affaires) {
        listeAffaires.clear();
        affairesParLieu.clear();
        affairesParType.clear();
        affairesParStatus.clear();
        affairesParGravite.clear();

        for (Affaire affaire : affaires) {
            ajouterAffaire(affaire);
        }
    }

    // Ajouter une affaire et l'indexer dans toutes les structures
    public static void ajouterAffaire(Affaire affaire) {
        if (affaire == null) return;

        listeAffaires.add(affaire);

        // Indexation par lieu
        affairesParLieu.computeIfAbsent(affaire.getLieu(), k -> new ArrayList<>()).add(affaire);

        // Indexation par type
        affairesParType.computeIfAbsent(affaire.getType(), k -> new ArrayList<>()).add(affaire);

        // Indexation par statut
        affairesParStatus.computeIfAbsent(affaire.getStatus(), k -> new ArrayList<>()).add(affaire);

        // Indexation par gravité
        affairesParGravite.computeIfAbsent(affaire.getGravite(), k -> new ArrayList<>()).add(affaire);
    }

    // Supprimer une affaire et mettre à jour toutes les structures
    public void supprimerAffaire(Affaire affaire) {
        if (affaire == null) return;

        listeAffaires.remove(affaire);

        affairesParLieu.getOrDefault(affaire.getLieu(), Collections.emptyList()).remove(affaire);
        affairesParType.getOrDefault(affaire.getType(), Collections.emptyList()).remove(affaire);
        affairesParStatus.getOrDefault(affaire.getStatus(), Collections.emptyList()).remove(affaire);
        affairesParGravite.getOrDefault(affaire.getGravite(), Collections.emptyList()).remove(affaire);
    }

    public static List<Affaire> rechercherAffairesFiltres(String lieuTexte, String typeTexte, Affaire.Status statutRecherche, Integer graviteMin, Integer graviteMax) {
        Set<Affaire> resultat = new HashSet<>(listeAffaires); // Départ avec toutes les affaires

        // 1. Filtrer par statut via EnumMap
        if (statutRecherche != null) {
            System.out.println("statutRecherche: " + statutRecherche);
            resultat.retainAll(rechercherParStatus(statutRecherche));

            System.out.println("Enummap" + resultat);
        }

        // 2. Filtrer par gravité via TreeMap (intervalle)
        if (graviteMin != 0 && graviteMax != 0) {
            System.out.println("GraviteMin: " + graviteMin);
            resultat.retainAll(rechercherParIntervalleGravite(graviteMin, graviteMax));
        }

        // 3. Filtrer par lieu via texte brut
        if (lieuTexte != null && !lieuTexte.isBlank()) {
            System.out.println("LieuTexte: " + lieuTexte);
            resultat = resultat.stream()
                    .filter(a -> a.getLieu() != null && a.getLieu().toLowerCase().contains(lieuTexte.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        // 4. Filtrer par type via texte brut
        if (typeTexte != null && !typeTexte.isBlank()) {
            System.out.println("TypeTexte: " + typeTexte);
            resultat = resultat.stream()
                    .filter(a -> a.getType() != null && a.getType().toLowerCase().contains(typeTexte.toLowerCase()))
                    .collect(Collectors.toSet());
        }


        System.out.println("Final " + resultat);
        return new ArrayList<>(resultat);
    }


    public static List<Affaire> rechercherParStatus(Affaire.Status status) {
        return affairesParStatus.getOrDefault(status, Collections.emptyList());
    }

    public List<Affaire> rechercherParGravite(int gravite) {
        return affairesParGravite.getOrDefault(gravite, Collections.emptyList());
    }

    public static List<Affaire> rechercherParIntervalleGravite(int min, int max) {
        return listeAffaires.stream()
                .filter(affaire -> affaire.getGravite() >= min && affaire.getGravite() <= max)
                .collect(Collectors.toList());
    }


    public List<Affaire> getToutesLesAffaires() {
        return new ArrayList<>(listeAffaires);
    }
}
