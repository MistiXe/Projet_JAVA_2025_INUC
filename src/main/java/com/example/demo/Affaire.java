package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

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

    public Affaire() {}

    // Constructeur
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Affaire(LocalDate date, String lieu, String type, Status status, int gravite) {
        this.date = date;
        this.lieu = lieu;
        this.type = type;
        this.status = status;
        this.gravite = gravite;
    }

    public LocalDate getDate() {
        return date;
    }
    public String getLieu() { return lieu; }
    public String getType() { return type; }
    public Status getStatus() { return status; }
    public int getGravite() { return gravite; }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setLieu(String lieu) {  this.lieu = lieu; }
    public void setType(String type) {  this.type = type; }
    public void setStatus(Status status) { this.status = status; }
    public void setGravite(int gravite) { this.gravite = gravite; }
}
