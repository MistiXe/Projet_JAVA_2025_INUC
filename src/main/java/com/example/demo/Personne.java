package com.example.demo;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Personne {

    private String prenom;
    private LocalDate date;  // Utiliser LocalDate plutôt que Date
    private EtatAffaire etatAffaire;

    public Personne() {}

    // Constructeur
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Personne(String prenom, LocalDate date, EtatAffaire etatAffaire) {
        this.prenom = prenom;
        this.date = date;
        this.etatAffaire = etatAffaire;
    }

    // Getters et Setters
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EtatAffaire getEtatAffaire() {
        return etatAffaire;
    }

    public void setEtatAffaire(EtatAffaire etatAffaire) {
        this.etatAffaire = etatAffaire;
    }

    // Méthode toString pour une représentation lisible
    @Override
    public String toString() {
        return "Affaire [prenom=" + prenom + ", date=" + date + ", etatAffaire=" + etatAffaire + "]";
    }
}
