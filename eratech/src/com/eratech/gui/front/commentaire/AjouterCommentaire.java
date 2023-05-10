package com.eratech.gui.front.commentaire;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Commentaire;
import com.eratech.entities.Produit;
import com.eratech.entities.Utilisateur;
import com.eratech.gui.front.produit.AfficherToutProduit;
import com.eratech.services.CommentaireService;
import com.eratech.services.ProduitService;
import com.eratech.services.UtilisateurService;
import com.eratech.utils.AlertUtils;

import java.util.ArrayList;
import java.util.Date;

public class AjouterCommentaire extends Form {


    TextField contenuTF;
    Label contenuLabel;

    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;
    ArrayList<Produit> listProduits;
    PickerComponent produitPC;
    Produit selectedProduit = null;


    Button manageButton;

    Form previous;

    public AjouterCommentaire(Form previous, Produit produit) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        this.selectedProduit = produit;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] utilisateurStrings;
        int utilisateurIndex;
        utilisateurPC = PickerComponent.createStrings("").label("Utilisateur");
        listUtilisateurs = UtilisateurService.getInstance().getAll();
        utilisateurStrings = new String[listUtilisateurs.size()];
        utilisateurIndex = 0;
        for (Utilisateur utilisateur : listUtilisateurs) {
            utilisateurStrings[utilisateurIndex] = utilisateur.getEmail();
            utilisateurIndex++;
        }
        if (listUtilisateurs.size() > 0) {
            utilisateurPC.getPicker().setStrings(utilisateurStrings);
            utilisateurPC.getPicker().addActionListener(l -> selectedUtilisateur = listUtilisateurs.get(utilisateurPC.getPicker().getSelectedStringIndex()));
        } else {
            utilisateurPC.getPicker().setStrings("");
        }

        String[] produitStrings;
        int produitIndex;
        produitPC = PickerComponent.createStrings("").label("Produit");
        listProduits = ProduitService.getInstance().getAll();
        produitStrings = new String[listProduits.size()];
        produitIndex = 0;
        for (Produit produit : listProduits) {
            produitStrings[produitIndex] = produit.getLibelle();
            produitIndex++;
        }
        if (listProduits.size() > 0) {
            produitPC.getPicker().setStrings(produitStrings);
            produitPC.getPicker().addActionListener(l -> selectedProduit = listProduits.get(produitPC.getPicker().getSelectedStringIndex()));
        } else {
            produitPC.getPicker().setStrings("");
        }


        if (selectedProduit != null) {
            produitPC.getPicker().setSelectedString(selectedProduit.toString());
        }


        contenuLabel = new Label("Contenu : ");
        contenuLabel.setUIID("labelDefault");
        contenuTF = new TextField();
        contenuTF.setHint("Tapez le contenu");


        manageButton = new Button("Ajouter");
        

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                contenuLabel, contenuTF,
                 produitPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = CommentaireService.getInstance().add(
                        new Commentaire(
                                contenuTF.getText(),
                                new Date(),
                              
                                selectedProduit
                        )
                );
                if (responseCode == 200) {
                    AlertUtils.makeNotification(" Votre commentaire est ajouté avec succès ");
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de commentaire. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutProduit) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (contenuTF.getText().equals("")) {
            Dialog.show("Avertissement", "Contenu vide", new Command("Ok"));
            return false;
        }

//        if (selectedUtilisateur == null) {
//            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
//            return false;
//        }

        if (selectedProduit == null) {
            Dialog.show("Avertissement", "Veuillez choisir un produit", new Command("Ok"));
            return false;
        }


        return true;
    }
}