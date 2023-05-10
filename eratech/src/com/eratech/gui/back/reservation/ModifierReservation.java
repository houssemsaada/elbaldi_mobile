package com.eratech.gui.back.reservation;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Bonplan;
import com.eratech.entities.Reservation;
import com.eratech.entities.Utilisateur;
import com.eratech.services.BonplanService;
import com.eratech.services.ReservationService;
import com.eratech.services.UtilisateurService;

import java.util.ArrayList;

public class ModifierReservation extends Form {


    Reservation currentReservation;

    TextField nombrePersonnesTF;
    Label nombrePersonnesLabel;
    PickerComponent dateTF;

    ArrayList<Utilisateur> listUtilisateurs;

    Utilisateur selectedUtilisateur = new Utilisateur(1);

    ArrayList<Bonplan> listBonplans;
    Bonplan selectedBonplan = null;
    PickerComponent statutPC;

    Button manageButton;

    Form previous;

    public ModifierReservation(Form previous) {
        super("Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentReservation = AfficherToutReservation.currentReservation;

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
        listBonplans = BonplanService.getInstance().getAll();
        bonplanStrings = new String[listBonplans.size()];
        bonplanIndex = 0;
        for (Bonplan bonplan : listBonplans) {
            bonplanStrings[bonplanIndex] = bonplan.getTitre();
            bonplanIndex++;
        }


        nombrePersonnesLabel = new Label("NombrePersonnes : ");
        nombrePersonnesLabel.setUIID("labelDefault");
        nombrePersonnesTF = new TextField();
        nombrePersonnesTF.setHint("Tapez le nombrePersonnes");


        dateTF = PickerComponent.createDate(null).label("Date");

        String[] statutStrings = new String[2];
        statutStrings[0] = "Confirmé";
        statutStrings[1] = "Refusé";


        statutPC = PickerComponent.createStrings(statutStrings).label("Statut : ");


        nombrePersonnesTF.setText(String.valueOf(currentReservation.getNombrePersonnes()));
        dateTF.getPicker().setDate(currentReservation.getDate());

        selectedUtilisateur = currentReservation.getUtilisateur();
        selectedBonplan = currentReservation.getBonplan();


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                nombrePersonnesLabel, nombrePersonnesTF,
                dateTF,
                statutPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = ReservationService.getInstance().edit(
                        new Reservation(
                                currentReservation.getId(),
                                selectedUtilisateur,
                                selectedBonplan,
                                (int) Float.parseFloat(nombrePersonnesTF.getText()),
                                dateTF.getPicker().getDate(),
                                statutPC.getPicker().getText()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Reservation modifié avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutReservation) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (nombrePersonnesTF.getText().equals("")) {
            Dialog.show("Avertissement", "NombrePersonnes vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nombrePersonnesTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nombrePersonnesTF.getText() + " n'est pas un nombre valide (nombrePersonnes)", new Command("Ok"));
            return false;
        }

        if (Float.parseFloat(nombrePersonnesTF.getText()) < 0) {
            Dialog.show("Avertissement", " Nb personne doit etre sup a 0", new Command("Ok"));
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
        if (statutPC.getPicker().getSelectedString().isEmpty()) {
            Dialog.show("Avertissement", "Veuillez choisir statut", new Command("Ok"));
            return false;
        }


        return true;
    }
}