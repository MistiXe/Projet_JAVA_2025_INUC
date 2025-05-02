package com.example.demo.Patrons;

public class Preuve {
    private String description;
    private int importance; // Par exemple, de 1 (faible) à 5 (critique)

    public Preuve() {
    }

    public Preuve(String description, int importance) {
        this.description = description;
        this.importance = importance;
    }

    //============================================
    // Getters
    //============================================
    public String getDescription() { return description; }
    public int getImportance() { return importance; }

    //============================================
    // Setters
    //============================================
    public void setDescription(String description) { this.description = description; }
    public void setImportance(int importance) { this.importance = importance; }

    // Redéfinition de toString() pour un affichage clair
    @Override
    public String toString() {
        return description + " (Importance: " + importance + ")";
    }
}
