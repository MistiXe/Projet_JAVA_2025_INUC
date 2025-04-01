package com.example.demo.Patrons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Personne {
    private int id;
    private String prenom;
    private String nom;
    private HashSet<String> adresses = new HashSet<>();
    private String email;
    private long telephone;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String genre;
    private String nationalite;
    private boolean casierJudiciaire;

    public enum StatutLegal {
        EN_LIBERTE, EN_PRISON, EN_FUITE, EN_PROBATION, SOUS_SURVEILLANCE,
        SUSPECT, TEMOIN, VICTIME, COMPLICE, INTERDIT_DE_SEJOUR,
        INTERDIT_DE_QUITTER_LE_TERRITOIRE, SOUS_CONTROLE_JUDICIAIRE
    }

    private StatutLegal statutLegal;
    private HashSet<LocalDate> dateArrestation = new HashSet<>();
    private Map<String, Integer> condamnations;
    private Map<String, List<String>> reseauxSociaux;
    private HashSet<String> pseudonymes = new HashSet<>();
    private Map<Personne, String> listeLiens;
    private Map<Affaire, String> listeAffaires;
    private String descriptionPhysique;
    private String photoUrl;
    private String derniereLocalisation;
    private LocalDate dateDerniereActivite;
    private String notes;

    // Gestion des témoignages (clé : id du témoin, valeur : liste des id des témoins)
    private Map<Integer, List<Integer>> temoignages = new HashMap<>();

    // Constructeurs
    public Personne() {}

    public Personne(int id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    public Personne(int id, String prenom, String nom, String adresse, String email, long telephone, LocalDate dateNaissance,
                    String lieuNaissance, String genre, String nationalite) {
        this(id, prenom, nom);
        this.adresses.add(adresse);
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.genre = genre;
        this.nationalite = nationalite;
    }

    public Personne(int id, String prenom, String nom, HashSet<String> adresses, String email, long telephone, LocalDate dateNaissance,
                    String lieuNaissance, String genre, String nationalite) {
        this(id, prenom, nom);
        this.adresses = adresses;
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.genre = genre;
        this.nationalite = nationalite;
    }

    // Gestion des témoignages
    public Map<Integer, List<Integer>> getTemoignages() { return temoignages; }

    public void ajouterTemoignage(Personne temoin, List<Personne> temoignes) {
        temoignages.put(temoin.getId(), temoignes.stream().map(Personne::getId).toList());
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public HashSet<String> getAdresses() { return adresses; }
    public void setAdresses(HashSet<String> adresses) { this.adresses = adresses; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getTelephone() { return telephone; }
    public void setTelephone(long telephone) { this.telephone = telephone; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }

    public boolean isCasierJudiciaire() { return casierJudiciaire; }
    public void setCasierJudiciaire(boolean casierJudiciaire) { this.casierJudiciaire = casierJudiciaire; }

    public StatutLegal getStatutLegal() { return statutLegal; }
    public void setStatutLegal(StatutLegal statutLegal) { this.statutLegal = statutLegal; }

    public HashSet<LocalDate> getDateArrestation() { return dateArrestation; }
    public void setDateArrestation(HashSet<LocalDate> dateArrestation) { this.dateArrestation = dateArrestation; }

    public Map<String, Integer> getCondamnations() { return condamnations; }
    public void setCondamnations(Map<String, Integer> condamnations) { this.condamnations = condamnations; }

    public Map<String, List<String>> getReseauxSociaux() { return reseauxSociaux; }
    public void setReseauxSociaux(Map<String, List<String>> reseauxSociaux) { this.reseauxSociaux = reseauxSociaux; }

    public HashSet<String> getPseudonymes() { return pseudonymes; }
    public void setPseudonymes(HashSet<String> pseudonymes) { this.pseudonymes = pseudonymes; }

    public Map<Personne, String> getListeLiens() { return listeLiens; }
    public void setListeLiens(Map<Personne, String> listeLiens) { this.listeLiens = listeLiens; }

    public Map<Affaire, String> getListeAffaires() { return listeAffaires; }
    public void setListeAffaires(Map<Affaire, String> listeAffaires) { this.listeAffaires = listeAffaires; }

    public String getDescriptionPhysique() { return descriptionPhysique; }
    public void setDescriptionPhysique(String descriptionPhysique) { this.descriptionPhysique = descriptionPhysique; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getDerniereLocalisation() { return derniereLocalisation; }
    public void setDerniereLocalisation(String derniereLocalisation) { this.derniereLocalisation = derniereLocalisation; }

    public LocalDate getDateDerniereActivite() { return dateDerniereActivite; }
    public void setDateDerniereActivite(LocalDate dateDerniereActivite) { this.dateDerniereActivite = dateDerniereActivite; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
