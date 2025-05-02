package com.example.demo.Patrons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Personne {
    private int id;
    private String prenom;
    private String nom;
    private HashSet<String> adresses = new HashSet<>();
    private String email;
    private long telephone;
    private String lieuNaissance;
    private String genre;
    private String nationalite;
    private boolean casierJudiciaire;
    private StatutLegal statutLegal;
    private HashSet<String> pseudonymes = new HashSet<>();
    private String descriptionPhysique;
    private String photoUrl;
    private String derniereLocalisation;
    private String notes;

    public enum StatutLegal {
        EN_LIBERTE, EN_PRISON, EN_FUITE, EN_PROBATION, SOUS_SURVEILLANCE,
        SUSPECT, TEMOIN, VICTIME, COMPLICE, INTERDIT_DE_SEJOUR,
        INTERDIT_DE_QUITTER_LE_TERRITOIRE, SOUS_CONTROLE_JUDICIAIRE
    }

    public Personne() {
    }

    public Personne(int id, String prenom, String nom, String email, long telephone, String lieuNaissance, String genre,
                    String nationalite, boolean casierJudiciaire, StatutLegal statutLegal, String descriptionPhysique,
                    String photoUrl, String derniereLocalisation, String notes) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.lieuNaissance = lieuNaissance;
        this.genre = genre;
        this.nationalite = nationalite;
        this.casierJudiciaire = casierJudiciaire;
        this.statutLegal = statutLegal;
        this.descriptionPhysique = descriptionPhysique;
        this.photoUrl = photoUrl;
        this.derniereLocalisation = derniereLocalisation;
        this.notes = notes;
    }

    //============================================
    // Getters
    //============================================
    public int getId() {return this.id;}
    public String getPrenom() {return this.prenom;}
    public String getNom() {return this.nom;}
    public HashSet<String> getAdresses() { return adresses; }
    public String getEmail() { return email; }
    public long getTelephone() { return telephone; }
    public String getLieuNaissance() { return lieuNaissance; }
    public String getGenre() { return genre; }
    public String getNationalite() { return nationalite; }
    public boolean isCasierJudiciaire() { return casierJudiciaire; }
    public StatutLegal getStatutLegal() { return statutLegal; }
    public HashSet<String> getPseudonymes() { return pseudonymes; }
    public String getDescriptionPhysique() { return descriptionPhysique; }
    public String getPhotoUrl() { return photoUrl; }
    public String getDerniereLocalisation() { return derniereLocalisation; }
    public String getNotes() { return notes; }

    //============================================
    // Setters
    //============================================
    public void setId(int id) {this.id = id;}
    public void setAdresses(HashSet<String> adresses) { this.adresses = adresses; }
    public void setEmail(String email) { this.email = email; }
    public void setTelephone(long telephone) { this.telephone = telephone; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    public void setCasierJudiciaire(boolean casierJudiciaire) { this.casierJudiciaire = casierJudiciaire; }
    public void setStatutLegal(StatutLegal statutLegal) { this.statutLegal = statutLegal; }
    public void setPseudonymes(HashSet<String> pseudonymes) { this.pseudonymes = pseudonymes; }
    public void setDescriptionPhysique(String descriptionPhysique) { this.descriptionPhysique = descriptionPhysique; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public void setDerniereLocalisation(String derniereLocalisation) { this.derniereLocalisation = derniereLocalisation; }
    public void setNotes(String notes) { this.notes = notes; }

    //============================================
    // Méthodes d'ajout/suppression
    //============================================
    public void ajouterAdresse(String adresse) { this.adresses.add(adresse); }
    public void supprimerAdresse(String adresse) { this.adresses.remove(adresse); }

    public void ajouterPseudonyme(String pseudonyme) { this.pseudonymes.add(pseudonyme); }
    public void supprimerPseudonyme(String pseudonyme) { this.pseudonymes.remove(pseudonyme); }

    //============================================
    // Méthode pour afficher les informations
    //============================================
    public void afficherDetails() {
        System.out.println("Personne [Email: " + email + ", Téléphone: " + telephone);
        System.out.println("Lieu de Naissance: " + lieuNaissance + ", Genre: " + genre + ", Nationalité: " + nationalite);
        System.out.println("Casier Judiciaire: " + casierJudiciaire + ", Statut Légal: " + statutLegal);
        System.out.println("Pseudonymes: " + String.join(", ", pseudonymes));
        System.out.println("Description Physique: " + descriptionPhysique);
        System.out.println("Photo URL: " + photoUrl);
        System.out.println("Dernière Localisation: " + derniereLocalisation);
        System.out.println("Notes: " + notes);
    }

    @Override
    public String toString() {
        return this.prenom + " " + this.nom;
    }

    // Méthode de validation d'une personne
    public boolean validerPersonne() {
        if (email == null || email.isEmpty() || telephone <= 0 || lieuNaissance == null || lieuNaissance.isEmpty()) {
            System.out.println("Erreur : Données invalides pour la personne.");
            return false;
        }
        System.out.println("Personne validée avec succès !");
        return true;
    }
}
