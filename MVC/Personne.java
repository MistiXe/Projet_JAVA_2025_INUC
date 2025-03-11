import java.util.List;

public class Personne {
    private String Nom;
    private String Prénom;
    private int Age;
    private List<String> Listes_d_affaires;

    // Getters et Setters
    public String getNom() { return Nom; }
    public void setNom(String nom) { Nom = nom; }

    public String getPrenom() { return Prénom; }
    public void setPrénom(String prénom) { Prénom = prénom; }

    public int getAge() { return Age; }
    public void setAge(int age) { Age = age; }

    public List<String> getListes_d_affaires() { return Listes_d_affaires; }
    public void setListes_d_affaires(List<String> listes_d_affaires) { Listes_d_affaires = listes_d_affaires; }
}
