import javax.swing.table.AbstractTableModel;

public class PersonneTableModel extends AbstractTableModel {

    private Personne[] personnes;
    private String[] colonnes = {"Nom", "Prénom", "Âge", "Affaires"};

    public PersonneTableModel(Personne[] personnes) {
        this.personnes = personnes;
    }

    @Override
    public int getRowCount() {
        return personnes.length;
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Personne personne = personnes[rowIndex];
        switch (columnIndex) {
            case 0: 
                return personne.getNom();
            case 1: 
                return personne.getPrenom();
            case 2: 
                return personne.getAge();
            case 3: 
                // Retourne une chaîne des affaires séparées par des virgules
                return String.join(", ", personne.getListes_d_affaires());
            default: 
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }
}
