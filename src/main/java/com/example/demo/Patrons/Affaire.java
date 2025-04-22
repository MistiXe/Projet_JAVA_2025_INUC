package com.example.demo.Patrons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Affaire {
    private LocalDate date;
    private String lieu;
    private String type;
    private List<Message> messages = new ArrayList<>();
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
    private List<Integer> enqueteurs;
    private List<Integer> suspects;
    private List<String> preuves;
    private Map<Integer, List<Integer>> temoignages = new HashMap<>();

    public Affaire() {
        this.enqueteurs = new ArrayList<>();
        this.suspects = new ArrayList<>();
        this.preuves = new ArrayList<>();
    }

    public Affaire(LocalDate date, String lieu, String type, Status status, int gravite) {
        this.date = date;
        this.lieu = lieu;
        this.type = type;
        this.status = status;
        this.gravite = gravite;
        this.enqueteurs = new ArrayList<>();
        this.suspects = new ArrayList<>();
        this.preuves = new ArrayList<>();
    }

    //============================================
    // Getters
    //============================================
    public LocalDate getDate() { return date; }
    public String getLieu() { return lieu; }
    public String getType() { return type; }
    public Status getStatus() { return status; }
    public int getGravite() { return gravite; }
    public String getDescription() { return description; }
    public List<Integer> getEnqueteurs() { return enqueteurs; }
    public List<Integer> getSuspects() { return suspects; }
    public List<String> getPreuves() { return preuves; }
    public Map<Integer, List<Integer>> getTemoignages() { return temoignages; }
    public List<Message> getMessages() { return messages; }

    //============================================
    // Setters
    //============================================
    public void setDate(LocalDate date) { this.date = date; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public void setType(String type) { this.type = type; }
    public void setStatus(Status status) { this.status = status; }
    public void setGravite(int gravite) { this.gravite = gravite; }
    public void setDescription(String description) { this.description = description; }
    public void setEnqueteurs(List<Integer> enqueteurs) { this.enqueteurs = enqueteurs; }
    public void setSuspects(List<Integer> suspects) { this.suspects = suspects; }
    public void setPreuves(List<String> preuves) { this.preuves = preuves; }
    public void setTemoignages(Map<Integer, List<Integer>> temoignages) { this.temoignages = temoignages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    //============================================
    // Méthodes d'ajout/suppression
    //============================================
    public void ajouterEnqueteur(Integer enqueteur) { this.enqueteurs.add(enqueteur); }
    public void supprimerEnqueteur(Integer enqueteur) { this.enqueteurs.remove(enqueteur); }

    public void ajouterSuspect(Integer suspect) { this.suspects.add(suspect); }
    public void supprimerSuspect(Integer suspect) { this.suspects.remove(suspect); }

    public void ajouterPreuve(String preuve) { this.preuves.add(preuve); }
    public void supprimerPreuve(String preuve) { this.preuves.remove(preuve); }

    //============================================
    // Méthodes de validation
    //============================================
    public boolean validerTemoignages(List<Personne> personnesConnues) {
        Set<Integer> idsExistants = personnesConnues.stream()
                .map(Personne::getId)
                .collect(Collectors.toSet());

        for (Map.Entry<Integer, List<Integer>> entry : temoignages.entrySet()) {
            int idTemoin = entry.getKey();

            if (!idsExistants.contains(idTemoin)) {
                System.out.println("Erreur : Le témoin avec l'ID " + idTemoin + " n'existe pas.");
                return false;
            }

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