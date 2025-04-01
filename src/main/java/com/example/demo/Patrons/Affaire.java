package com.example.demo.Patrons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Affaire {
    private LocalDate date;
    private String lieu;
    private String type;
    public enum Status {
        NON_ENGAGEE("Non engagée"),
        EN_COURS("En cours"),
        CLASSEE_SANS_SUITE("Classée sans suite"),
        TRANSFEREE_AU_PARQUET("Transférée au parquet"),
        INSTRUCTION_EN_COURS("Instruction en cours"),
        RENVOYEE_DEVANT_TRIBUNAL("Renvoyée devant tribunal"),
        SUSPENDUE("Suspendue"),
        CLOTUREE("Clôturée"),
        REOUVERTE("Rouverte");
    
        private final String statusString;
    
        Status(String status) {
            statusString = status;
        }

        @Override
        public String toString() {
            return statusString;
        }
    }
    private Status status;
    private int gravite;
    private String description;
    private List<String> enqueteurs;
    private List<String> suspects;
    private List<String> temoins;
    private List<String> preuves;

    // Map d'ID d'affaire à une liste d'ID de témoins (relation clé étrangère)
    @JsonDeserialize(using = TemoignageDesirializer.class)
    private Map<Integer, List<Integer>> temoignages;

    public Affaire() {
        this.enqueteurs = new ArrayList<>();
        this.suspects = new ArrayList<>();
        this.temoins = new ArrayList<>();
        this.preuves = new ArrayList<>();
    }

    // Constructeur
    public Affaire(LocalDate date, String lieu, String type, Status status, int gravite) {
        this.date = date;
        this.lieu = lieu;
        this.type = type;
        this.status = status;
        this.gravite = gravite;
        this.enqueteurs = new ArrayList<>();
        this.suspects = new ArrayList<>();
        this.temoins = new ArrayList<>();
        this.preuves = new ArrayList<>();
    }

    // Getters et Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public int getGravite() { return gravite; }
    public void setGravite(int gravite) { this.gravite = gravite; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<String> getEnqueteurs() { return enqueteurs; }
    public void setEnqueteurs(List<String> enqueteurs) { this.enqueteurs = enqueteurs; }
    
    public List<String> getSuspects() { return suspects; }
    public void setSuspects(List<String> suspects) { this.suspects = suspects; }
    
    public List<String> getTemoins() { return temoins; }
    public void setTemoins(List<String> temoins) { this.temoins = temoins; }
  
    public List<String> getPreuves() { return preuves; }
    public void setPreuves(List<String> preuves) { this.preuves = preuves; } 
    
    public Map<Integer, List<Integer>> getTemoignages() { return temoignages; }
    public void setTemoignages(Map<Integer, List<Integer>> temoignages) { this.temoignages = temoignages; }
  
    public void ajouterEnqueteur(String enqueteur) { this.enqueteurs.add(enqueteur); }
    public void supprimerEnqueteur(String enqueteur) { this.enqueteurs.remove(enqueteur);  }

    public void ajouterSuspect(String suspect) { this.suspects.add(suspect); }
    public void supprimerSuspect(String suspect) { this.suspects.remove(suspect); }

    public void ajouterTemoin(String temoin) { this.temoins.add(temoin); }
    public void supprimerTemoin(String temoin) { this.temoins.remove(temoin); }

    public void ajouterPreuves(String preuves) { this.preuves.add(preuves); }
    public void supprimerPreuves(String preuves) { this.preuves.remove(preuves); }


    public boolean validerTemoignages(List<Personne> personnesConnues) {
        // Récupérer la liste des IDs des personnes existantes
        Set<Integer> idsExistants = personnesConnues.stream()
                .map(Personne::getId)
                .collect(Collectors.toSet());
    
        // Vérifier chaque témoin et les personnes sur lesquelles ils témoignent
        for (Map.Entry<Integer, List<Integer>> entry : temoignages.entrySet()) {
            int idTemoin = entry.getKey();
            
            // Vérifier si le témoin existe
            if (!idsExistants.contains(idTemoin)) {
                System.out.println("Erreur : Le témoin avec l'ID " + idTemoin + " n'existe pas.");
                return false;
            }
    
            // Vérifier que toutes les personnes témoignées existent
            for (Integer idTemoigne : entry.getValue()) {
                if (!idsExistants.contains(idTemoigne)) {
                    System.out.println("Erreur : La personne témoignée avec l'ID " + idTemoigne + " n'existe pas.");
                    return false;
                }
            }
        }
    
        System.out.println("Validation des témoignages réussie !");
        return true;
    }
}