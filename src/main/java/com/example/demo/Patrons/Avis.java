package com.example.demo.Patrons;

import java.time.LocalDateTime;

public class Avis {
    private String auteur;
    private int note;
    private String commentaire;
    private LocalDateTime date;

    public Avis(String auteur, int note, String commentaire, LocalDateTime date) {
        this.auteur = auteur;
        this.note = note;
        this.commentaire = commentaire;
        this.date = date;
    }

    public String getAuteur() { return auteur; }
    public int getNote() { return note; }
    public String getCommentaire() { return commentaire; }
    public LocalDateTime getDate() { return date; }
}
