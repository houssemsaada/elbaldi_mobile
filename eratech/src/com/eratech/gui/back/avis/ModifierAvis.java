package com.eratech.gui.back.avis;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Avis;
import com.eratech.entities.Bonplan;
import com.eratech.entities.Utilisateur;
import com.eratech.services.AvisService;
import com.eratech.services.BonplanService;
import com.eratech.services.UtilisateurService;

import java.util.ArrayList;

public class ModifierAvis extends Form {


    Avis currentAvis;

    TextField noteTF;
    Label noteLabel;
    PickerComponent dateTF;

    ArrayList<Utilisateur> listUtilisateurs;
    Utilisateur selectedUtilisateur = new Utilisateur(1);
    ArrayList<Bonplan> listBonplans;
    PickerComponent bonplanPC;
    Bonplan selectedBonplan = null;


    Button manageButton;

    Form previous;

    public ModifierAvis(Form previous) {
        super("Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentAvis = AfficherToutAvis.currentAvis;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] utilisateurStrings;
        int utilisateurIndex;
        listUtilisateurs = UtilisateurService.getInstance().getAll();
        utilisateurStrings = new String[listUtilisateurs.size()];
        utilisateurIndex = 0;
        for (Utilisateur utilisateur : listUtilisateurs) {
            utilisateurStrings[utilisateurIndex] = utilisateur.getEmail();
            utilisateurIndex++;
        }
        if (listUtilisateurs.size() > 0) {
        } else {
        }

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


        noteLabel = new Label("Note : ");
        noteLabel.setUIID("labelDefault");
        noteTF = new TextField();
        noteTF.setHint("Tapez le note");


        dateTF = PickerComponent.createDate(null).label("Date");


        noteTF.setText(String.valueOf(currentAvis.getNote()));
        dateTF.getPicker().setDate(currentAvis.getDate());

        selectedUtilisateur = currentAvis.getUtilisateur();
        bonplanPC.getPicker().setSelectedString(currentAvis.getBonplan().getTitre());
        selectedBonplan = currentAvis.getBonplan();


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                noteLabel, noteTF,
                dateTF,
                bonplanPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = AvisService.getInstance().edit(
                        new Avis(
                                currentAvis.getId(),


                                selectedUtilisateur,
                                selectedBonplan,
                                Float.parseFloat(noteTF.getText()),
                                dateTF.getPicker().getDate()

                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Avis modifié avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de avis. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutAvis) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (noteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Note vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(noteTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", noteTF.getText() + " n'est pas un nombre valide (note)", new Command("Ok"));
            return false;
        }


        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la date", new Command("Ok"));
            return false;
        }


        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }

        if (selectedBonplan == null) {
            Dialog.show("Avertissement", "Veuillez choisir un bonplan", new Command("Ok"));
            return false;
        }


        return true;
    }
}