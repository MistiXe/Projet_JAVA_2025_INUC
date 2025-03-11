package INUC2025.MVC;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Personne {
    private int id; // Ajout de l'ID
    private String nom;
    private String prénom;
    private int age;
    private List<String> listes_d_affaires;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @JsonProperty("Nom")  // Associe le champ JSON "Nom" avec la propriété Java "nom"
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    @JsonProperty("Prénom")  // Associe le champ JSON "Prénom" avec la propriété Java "prénom"
    public String getPrénom() { return prénom; }
    public void setPrénom(String prénom) { this.prénom = prénom; }
    @JsonProperty("Age")
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    @JsonProperty("Listes d'affaires")
    public List<String> getListes_d_affaires() { return listes_d_affaires; }
    public void setListes_d_affaires(List<String> listes_d_affaires) { this.listes_d_affaires = listes_d_affaires; }
}
