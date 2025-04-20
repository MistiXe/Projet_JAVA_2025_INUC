package com.example.demo.Patrons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionnaireCommentaires {
    // Utilisation d'une structure arborescente pour les commentaires
    // (commentaires et réponses)
    private Map<Integer, TreeNode<Commentaire>> commentairesParAffaire;

    public GestionnaireCommentaires() {
        this.commentairesParAffaire = new HashMap<>();
    }

    // Ajouter un commentaire racine à une affaire
    public void ajouterCommentaire(Commentaire commentaire) {
        int affaireId = commentaire.getAffaireId();

        if (!commentairesParAffaire.containsKey(affaireId)) {
            commentairesParAffaire.put(affaireId, new TreeNode<>(null));
        }

        TreeNode<Commentaire> root = commentairesParAffaire.get(affaireId);
        root.addChild(new TreeNode<>(commentaire));
    }

    // Ajouter une réponse à un commentaire existant
    public void ajouterReponse(Commentaire commentaireParent, Commentaire reponse) {
        int affaireId = commentaireParent.getAffaireId();

        if (!commentairesParAffaire.containsKey(affaireId)) {
            return;
        }

        TreeNode<Commentaire> root = commentairesParAffaire.get(affaireId);
        TreeNode<Commentaire> nodeParent = trouverCommentaire(root, commentaireParent);

        if (nodeParent != null) {
            nodeParent.addChild(new TreeNode<>(reponse));
        }
    }

    // Trouver un commentaire dans l'arbre
    private TreeNode<Commentaire> trouverCommentaire(TreeNode<Commentaire> node, Commentaire commentaire) {
        if (node.getData() != null && node.getData().equals(commentaire)) {
            return node;
        }

        for (TreeNode<Commentaire> child : node.getChildren()) {
            TreeNode<Commentaire> found = trouverCommentaire(child, commentaire);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    // Récupérer tous les commentaires d'une affaire sous forme de liste plate
    public List<Commentaire> getCommentairesAffaire(int affaireId) {
        List<Commentaire> commentaires = new ArrayList<>();

        if (commentairesParAffaire.containsKey(affaireId)) {
            TreeNode<Commentaire> root = commentairesParAffaire.get(affaireId);
            collecterCommentaires(root, commentaires);
        }

        return commentaires;
    }

    private void collecterCommentaires(TreeNode<Commentaire> node, List<Commentaire> liste) {
        if (node.getData() != null) {
            liste.add(node.getData());
        }

        for (TreeNode<Commentaire> child : node.getChildren()) {
            collecterCommentaires(child, liste);
        }
    }
}