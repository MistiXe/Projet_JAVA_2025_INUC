package com.example.demo.Patrons;

import com.example.demo.PDFJSON.TemoignageDesirializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Map d'ID d'affaire à une liste d'ID de témoins (relation clé étrangère)
    @JsonDeserialize(using = TemoignageDesirializer.class)
    private Map<Integer, List<Integer>> temoignages;

    public Affaire() {
        this.enqueteurs = new ArrayList<>();
        this.suspects = new ArrayList<>();
        this.temoins = new ArrayList<>();
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
    }

    // Getters et Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getLieu() { return lieu; }
    public String getType() { return type; }
    public Status getStatus() { return status; }
    public int getGravite() { return gravite; }

    public String getDescription() { return description; }
    public List<String> getEnqueteurs() { return enqueteurs; }
    public List<String> getSuspects() { return suspects; }
    public List<String> getTemoins() { return temoins; }

    // Getter et Setter pour temoignages (Map d'IDs)
    public Map<Integer, List<Integer>> getTemoignages() { return temoignages; }
    public void setTemoignages(Map<Integer, List<Integer>> temoignages) { this.temoignages = temoignages; }

    // Méthode pour ajouter un témoin à l'affaire
    public void ajouterTemoignage(int idAffaire, int idTemoin) {
        temoignages.computeIfAbsent(idAffaire, k -> new ArrayList<>()).add(idTemoin);
    }

    public void setLieu(String lieu) {  this.lieu = lieu; }
    public void setType(String type) {  this.type = type; }
    public void setStatus(Status status) { this.status = status; }
    public void setGravite(int gravite) { this.gravite = gravite; }
    public void setDescription(String description) { this.description = description; }
    public void setEnqueteurs(List<String> enqueteurs) { this.enqueteurs = enqueteurs; }
    public void setSuspects(List<String> suspects) { this.suspects = suspects; }
    public void setTemoins(List<String> temoins) { this.temoins = temoins; }
    

    public void ajouterEnqueteur(String enqueteur) { this.enqueteurs.add(enqueteur); }
    public void supprimerEnqueteur(String enqueteur) { this.enqueteurs.remove(enqueteur);  }

    public void ajouterSuspect(String suspect) { this.suspects.add(suspect); }
    public void supprimerSuspect(String suspect) { this.suspects.remove(suspect); }

    public void ajouterTemoin(String temoin) { this.temoins.add(temoin); }
    public void supprimerTemoin(String temoin) { this.temoins.remove(temoin); }
}
