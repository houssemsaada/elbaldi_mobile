package com.eratech.gui.front.avis;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.MainApp;
import com.eratech.entities.Avis;
import com.eratech.entities.Bonplan;
import com.eratech.entities.Utilisateur;
import com.eratech.gui.front.bonplan.AfficherToutBonplan;
import com.eratech.services.AvisService;
import com.eratech.services.BonplanService;
import com.eratech.services.UtilisateurService;

import java.util.ArrayList;
import java.util.Date;

public class AjouterAvis extends Form {

    int nbEtoiles = 0;

    Label noteLabel;
    Utilisateur selectedUtilisateur = new Utilisateur(1);
    ArrayList<Bonplan> listBonplans;
    PickerComponent bonplanPC;
    Bonplan selectedBonplan = null;


    Button[] stars = {new Button(), new Button(), new Button(), new Button(), new Button()};


    Button manageButton;

    Form previous;

    public AjouterAvis(Form previous) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        String[] bonplanStrings;
        int bonplanIndex;
        bonplanPC = PickerComponent.createStrings("").label("Bonplan");
        listBonplans = BonplanService.getInstance().getAll();
        bonplanStrings = new String[listBonplans.size()];
        bonplanIndex = 0;
        for (Bonplan bonplan : listBonplans) {
            bonplanStrings[bonplanIndex] = bonplan.getTitre();
            bonplanIndex++;
        }
        if (listBonplans.size() > 0) {
            bonplanPC.getPicker().setStrings(bonplanStrings);
            bonplanPC.getPicker().addActionListener(l -> selectedBonplan = listBonplans.get(bonplanPC.getPicker().getSelectedStringIndex()));
        } else {
            bonplanPC.getPicker().setStrings("");
        }


        Container starsContainer;

        if (AfficherToutBonplan.currentBonplan != null){
            selectedBonplan = AfficherToutBonplan.currentBonplan;
            bonplanPC.getPicker().setSelectedString(AfficherToutBonplan.currentBonplan.getTitre());
        }

        noteLabel = new Label("Note : ");
        noteLabel.setUIID("defaultLabel");
        starsContainer = new Container();
        starsContainer.add(noteLabel);
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            int i = indexEtoile;
            stars[i].addActionListener(action -> {
                setStars(i + 1);
            });
            stars[i].setPreferredH(100);
            stars[i].setPreferredW(100);
            stars[i].setUIID("starButtonOutlined");
            stars[i].setFocusable(false);
            starsContainer.add(stars[i]);
        }



        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                starsContainer,
                 bonplanPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = AvisService.getInstance().add(
                        new Avis(
                                selectedUtilisateur,
                                selectedBonplan,
                                nbEtoiles,
                                new Date()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Avis ajouté avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de avis. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void setStars(int nb) {
        for (int i = 0; i < 5; i++) {
            if (i < nb) {
                stars[i].setUIID("starButton");
            } else {
                stars[i].setUIID("starButtonOutlined");
            }
        }
        nbEtoiles = nb;
        this.refreshTheme();
    }

    private void showBackAndRefresh() {
        ((AfficherToutBonplan) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }

        if (selectedBonplan == null) {
            Dialog.show("Avertissement", "Veuillez choisir un bonplan", new Command("Ok"));
            return false;
        }

        if (nbEtoiles == 0) {
            Dialog.show("Alerte", "Veuillez donner votre avis", new Command("Ok"));
            return false;
        }

        return true;
    }
}