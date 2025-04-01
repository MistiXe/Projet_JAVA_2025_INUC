package com.example.demo.Patrons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Affaire {
    private LocalDate date;
    private String lieu;
    private String type;
    public enum Status {
        NON_ENGAGEE,
        EN_COURS,
        CLASSEE_SANS_SUITE,
        TRANSFEREE_AU_PARQUET,
        INSTRUCTION_EN_COURS,
        RENVOYEE_DEVANT_TRIBUNAL,
        SUSPENDUE,
        CLOTUREE,
        REOUVERTE
    }
    public Status status;
    private int gravite;
    private String description;
    private List<String> enqueteurs;
    private List<String> suspects;
    private List<String> temoins;

    public Affaire() {
        this.enqueteurs = new ArrayList<>();
        this.suspects = new ArrayList<>();
        this.temoins = new ArrayList<>();
    }

    // Constructeur
    @JsonIgnoreProperties(ignoreUnknown = true)
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

    public LocalDate getDate() {
        return date;
    }
    public String getLieu() { return lieu; }
    public String getType() { return type; }
    public Status getStatus() { return status; }
    public int getGravite() { return gravite; }
    public String getDescription() { return description; }
    public List<String> getEnqueteurs() { return enqueteurs; }
    public List<String> getSuspects() { return suspects; }
    public List<String> getTemoins() { return temoins; }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setLieu(String lieu) {  this.lieu = lieu; }
    public void setType(String type) {  this.type = type; }
    public void setStatus(Status status) { this.status = status; }
    public void setGravite(int gravite) { this.gravite = gravite; }
    public void setDescription(String description) { this.description = description; }
    public void setEnqueteurs(List<String> enqueteurs) { this.enqueteurs = enqueteurs; }
    public void setSuspects(List<String> suspects) { this.suspects = suspects; }
    public void setTemoins(List<String> temoins) { this.temoins = temoins; }
    

    public void ajouterEnqueteur(String enqueteur) {
        this.enqueteurs.add(enqueteur);
    }

    public void supprimerEnqueteur(String enqueteur) {
        this.enqueteurs.remove(enqueteur);
    }

    public void ajouterSuspect(String suspect) {
        this.suspects.add(suspect);
    }

    public void supprimerSuspect(String suspect) {
        this.suspects.remove(suspect);
    }

    public void ajouterTemoin(String temoin) {
        this.temoins.add(temoin);
    }

    public void supprimerTemoin(String temoin) {
        this.temoins.remove(temoin);
    }

}
