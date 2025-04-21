package com.example.demo.Patrons;

import java.time.LocalDateTime;

public class Message {
    private String auteur;
    private String contenu;
    private LocalDateTime horodatage;

    public Message(String auteur, String contenu, LocalDateTime horodatage) {
        this.auteur = auteur;
        this.contenu = contenu;
        this.horodatage = horodatage;
    }

    public String getAuteur() { return auteur; }
    public String getContenu() { return contenu; }
    public LocalDateTime getHorodatage() { return horodatage; }
}
