package com.example.demo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Personne {
    private String prenom;
    private String nom;
    private HashSet<String> adresses;
    private String email;
    private int telephone;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String genre;
    private String nationalite;
    private boolean casierJudiciaire;
    public enum StatutLegal {
        EN_LIBERTE,
        EN_PRISON,
        EN_FUITE,
        EN_PROBATION,
        SOUS_SURVEILLANCE,
        SUSPECT,
        TEMOIN,
        VICTIME,
        COMPLICE,
        INTERDIT_DE_SEJOUR,
        INTERDIT_DE_QUITTER_LE_TERRITOIRE,
        SOUS_CONTROLE_JUDICIAIRE
    }
    private StatutLegal statutLegal;
    private HashSet<LocalDate> dateArrestation;
    private Map<String, Integer> condamnations; // Clé : condamnation, Valeur : nombre de fois
    private Map<String, List<String>> reseauxSociaux;
    private HashSet<String> pseudonymes;
    private Map<Personne, String> listeLiens; // Clé : Personne, Valeur : Type de lien
    private Map<Affaire, String> listeAffaires; // Clé : Affaire, Valeur : Rôle dans l'affaire
    private String descriptionPhysique;
    private String photoUrl;
    private String derniereLocalisation;
    private LocalDate dateDerniereActivite;
    private String notes;


    public Personne() {}

    // Constructeur
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Personne(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Personne(String prenom, String nom, String adresse, String email, int telephone, LocalDate dateNaissance, String lieuNaissance,
                    String genre, String nationalite) {
        this.prenom = prenom;
        this.nom = nom;
        this.adresses = new HashSet<String>();
        this.adresses.add(adresse);
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.genre = genre;
        this.nationalite = nationalite;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Personne(String prenom, String nom, HashSet<String> adresses, String email, int telephone, LocalDate dateNaissance, String lieuNaissance,
                    String genre, String nationalite) {
        this.prenom = prenom;
        this.nom = nom;
        this.adresses = adresses;
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.genre = genre;
        this.nationalite = nationalite;
    }

    // Getters et Setters
    public String getPrenom() { return this.prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNom() { return this.nom; }
    public void setNom(String nom) { this.nom = nom; }

    public HashSet<String> getAdresses() { return this.adresses; }
    public void setAdresses(HashSet<String> adresses) { this.adresses = adresses; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public int getTelephone() { return this.telephone; }
    public void setTelephone(int telephone) { this.telephone = telephone; }

    public LocalDate getDateNaissance() { return this.dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getLieuNaissance() { return this.lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public String getGenre() { return this.genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getNationalite() { return this.nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }

    public boolean isCasierJudiciaire() { return this.casierJudiciaire; }
    public void setCasierJudiciaire(boolean casierJudiciaire) { this.casierJudiciaire = casierJudiciaire; }

    public StatutLegal getStatutLegal() { return this.statutLegal; }
    public void setStatutLegal(StatutLegal statutLegal) { this.statutLegal = statutLegal; }

    public HashSet<LocalDate> getDateArrestation() { return this.dateArrestation; }
    public void setDateArrestation(HashSet<LocalDate> dateArrestation) { this.dateArrestation = dateArrestation; }

    public Map<String, Integer> getCondamnations() { return this.condamnations; }
    public void setCondamnations(Map<String, Integer> condamnations) { this.condamnations = condamnations; }

    public Map<String, List<String>> getReseauxSociaux() { return this.reseauxSociaux; }
    public void setReseauxSociaux(Map<String, List<String>> reseauxSociaux) { this.reseauxSociaux = reseauxSociaux; }

    public HashSet<String> getPseudonymes() { return this.pseudonymes; }
    public void setPseudonymes(HashSet<String> pseudonymes) { this.pseudonymes = pseudonymes; }

    public Map<Personne, String> getListeLiens() { return this.listeLiens; }
    public void setListeLiens(Map<Personne, String> listeLiens) { this.listeLiens = listeLiens; }

    public Map<Affaire, String> getListeAffaires() { return this.listeAffaires; }
    public void setListeAffaires(Map<Affaire, String> listeAffaires) { this.listeAffaires = listeAffaires; }

    public String getDescriptionPhysique() { return this.descriptionPhysique; }
    public void setDescriptionPhysique(String descriptionPhysique) { this.descriptionPhysique = descriptionPhysique; }

    public String getPhotoUrl() { return this.photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getDerniereLocalisation() { return this.derniereLocalisation; }
    public void setDerniereLocalisation(String derniereLocalisation) { this.derniereLocalisation = derniereLocalisation; }

    public LocalDate getDateDerniereActivite() { return this.dateDerniereActivite; }
    public void setDateDerniereActivite(LocalDate dateDerniereActivite) { this.dateDerniereActivite = dateDerniereActivite; }

    public String getNotes() { return this.notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
