package INUC2025.MVC;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonneTableModel extends AbstractTableModel {

    private List<Personne> personnes;
    private final String[] colonnes = {"id", "Nom", "Prénom", "Âge", "Affaires"};

    public PersonneTableModel(List<Personne> personnes) {
        this.personnes = personnes;
    }

    @Override
    public int getRowCount() {
        return (personnes != null) ? personnes.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (personnes == null || rowIndex >= personnes.size()) {
            return null;
        }

        Personne personne = personnes.get(rowIndex);
        switch (columnIndex) {
            case 0: return personne.getId();
            case 1: return personne.getNom();
            case 2: return personne.getPrénom();
            case 3: return personne.getAge();
            case 4: return String.join(", ", personne.getListes_d_affaires());
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    public void setPersonnes(List<Personne> nouvellesPersonnes) {
        this.personnes = nouvellesPersonnes;
        fireTableDataChanged(); // Rafraîchir la table
    }
}
