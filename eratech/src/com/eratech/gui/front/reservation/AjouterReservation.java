package com.eratech.gui.front.reservation;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Bonplan;
import com.eratech.entities.Reservation;
import com.eratech.entities.Utilisateur;
import com.eratech.gui.front.bonplan.AfficherToutBonplan;
import com.eratech.gui.utilisateur.SessionManager;
import com.eratech.services.BonplanService;
import com.eratech.services.ReservationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class AjouterReservation extends Form {

    TextField nombrePersonnesTF;
    Label nombrePersonnesLabel;

    PickerComponent dateTF;
    Utilisateur selectedUtilisateur = new Utilisateur(SessionManager.getId());
    ArrayList<Bonplan> listBonplans;
    PickerComponent bonplanPC;
    Bonplan selectedBonplan = null;


    Button manageButton;

    Form previous;

    public AjouterReservation(Form previous) {
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

        if (AfficherToutBonplan.currentBonplan != null) {
            selectedBonplan = AfficherToutBonplan.currentBonplan;
            bonplanPC.getPicker().setSelectedString(AfficherToutBonplan.currentBonplan.getTitre());
        }


        nombrePersonnesLabel = new Label("NombrePersonnes : ");
        nombrePersonnesLabel.setUIID("labelDefault");
        nombrePersonnesTF = new TextField();
        nombrePersonnesTF.setHint("Tapez le nombrePersonnes");

        dateTF = PickerComponent.createDate(null).label("Date");

        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                nombrePersonnesLabel, nombrePersonnesTF,
                dateTF,
                bonplanPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = ReservationService.getInstance().add(
                        new Reservation(
                                selectedUtilisateur,
                                selectedBonplan,
                                (int) Float.parseFloat(nombrePersonnesTF.getText()),
                                dateTF.getPicker().getDate(),
                                ""
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Réservation ajoutée avec succès!", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutBonplan) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {
        
     
        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez remplir les champs", new Command("Ok"));
            return false;
        }

   
    

//=======================================================================================================================================================================        
        if (nombrePersonnesTF.getText().equals("")) {
            Dialog.show("Avertissement", "le champ nombre de personnes est vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nombrePersonnesTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nombrePersonnesTF.getText() + " n'est pas un nombre valide (nombrePersonnes)", new Command("Ok"));
            return false;
        }

        if (Float.parseFloat(nombrePersonnesTF.getText()) < 0) {
            Dialog.show("Avertissement", " Le nombre de personnes doit être supérieur à zéro", new Command("Ok"));
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
        
        
       Calendar now = Calendar.getInstance(); // Obtient la date actuelle
       Calendar selectedDate = Calendar.getInstance();
       selectedDate.setTime(dateTF.getPicker().getDate());
       if (selectedDate.before(now)) { // Vérifie si la date sélectionnée est antérieure à la date actuelle
        Dialog.show("Avertissement", "La date sélectionnée ne doit pas être antérieure à la date actuelle", new Command("Ok"));
        return false;
       }
        return true;
    }
}