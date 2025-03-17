package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Personne {
    private String prenom;
    private String nom;
    private int age;
    private boolean fiche;

    public Personne() {} // Obligatoire pour Jackson

    public Personne(String prenom, String nom, int age, boolean fiche) {
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.fiche = fiche;
    }

    // Getters et Setters
    @JsonProperty("prenom")
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    @JsonProperty("nom")
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    @JsonProperty("age")
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @JsonProperty("fiche")
    public boolean isFiche() { return fiche; }
    public void setFiche(boolean fiche) { this.fiche = fiche; }
}
