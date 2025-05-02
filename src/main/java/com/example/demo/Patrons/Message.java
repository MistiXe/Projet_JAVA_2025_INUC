package com.example.demo.Patrons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String auteur;
    private String contenu;
    private LocalDateTime horodatage;

    // Constructeur par défaut requis pour la désérialisation JSON
    public Message() {}

    // Constructeur utile pour créer manuellement un message
    public Message(String auteur, String contenu, LocalDateTime horodatage) {
        this.auteur = auteur;
        this.contenu = contenu;
        this.horodatage = horodatage;
    }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getHorodatage() { return horodatage; }
    public void setHorodatage(LocalDateTime horodatage) { this.horodatage = horodatage; }
}
