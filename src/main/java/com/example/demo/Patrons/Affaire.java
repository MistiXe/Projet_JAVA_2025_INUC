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
    private Status status;
    private int gravite;
    private String description;

    // Map d'ID d'affaire à une liste d'ID de témoins (relation clé étrangère)
    @JsonDeserialize(using = TemoignageDesirializer.class)
    private Map<Integer, List<Integer>> temoignages;

    public Affaire() {}

    // Constructeur
    public Affaire(LocalDate date, String lieu, String type, Status status, int gravite) {
        this.date = date;
        this.lieu = lieu;
        this.type = type;
        this.status = status;
        this.gravite = gravite;
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

    // Getter et Setter pour temoignages (Map d'IDs)
    public Map<Integer, List<Integer>> getTemoignages() { return temoignages; }
    public void setTemoignages(Map<Integer, List<Integer>> temoignages) { this.temoignages = temoignages; }

    // Méthode pour ajouter un témoin à l'affaire
    public void ajouterTemoignage(int idAffaire, int idTemoin) {
        temoignages.computeIfAbsent(idAffaire, k -> new ArrayList<>()).add(idTemoin);
    }
}
