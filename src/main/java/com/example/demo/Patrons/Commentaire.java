// Classe pour représenter un commentaire
package com.example.demo.Patrons;

import java.time.LocalDateTime;

public class Commentaire {
    private String auteur;
    private String contenu;
    private LocalDateTime dateHeure;
    private int affaireId; // Pour lier le commentaire à une affaire spécifique

    public Commentaire() {
        this.dateHeure = LocalDateTime.now();
    }

    public Commentaire(String auteur, String contenu, int affaireId) {
        this.auteur = auteur;
        this.contenu = contenu;
        this.affaireId = affaireId;
        this.dateHeure = LocalDateTime.now();
    }

    // Getters et Setters
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public int getAffaireId() { return affaireId; }
    public void setAffaireId(int affaireId) { this.affaireId = affaireId; }

    @Override
    public String toString() {
        return "[" + dateHeure.toLocalDate() + " " + dateHeure.toLocalTime().withNano(0) + "] " + auteur + ": " + contenu;
    }
}