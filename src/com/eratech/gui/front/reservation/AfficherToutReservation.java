package com.eratech.gui.front.reservation;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Reservation;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.ReservationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutReservation extends BaseForm {

    Form previous;

    public static Reservation currentReservation = null;
    Button addBtn;

    Calendar calendar;

    public AfficherToutReservation(Resources res) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        Toolbar tb = new Toolbar(true);
        tb.setUIID("CustomToolbar");
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Reservation");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        addGUIs();
        addActions();


    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {

        Container container = new Container();
        container.setPreferredH(250);
        this.add(container);

        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Reservation> listReservations = ReservationService.getInstance().getAll();


        if (listReservations.size() > 0) {
            for (Reservation reservation : listReservations) {
                Component model = makeReservationModel(reservation);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentReservation = null;
            new AjouterReservation(this).show();
        });

    }

    Label utilisateurLabel, bonplanLabel, nombrePersonnesLabel, dateLabel, statutLabel;


    private Container makeModelWithoutButtons(Reservation reservation) {
        Container reservationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reservationModel.setUIID("containerRounded");


        utilisateurLabel = new Label("Utilisateur : " + reservation.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        bonplanLabel = new Label("Bonplan : " + reservation.getBonplan());
        bonplanLabel.setUIID("labelDefault");

        nombrePersonnesLabel = new Label("NombrePersonnes : " + reservation.getNombrePersonnes());
        nombrePersonnesLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(reservation.getDate()));
        dateLabel.setUIID("labelDefault");

        statutLabel = new Label("Statut : " + reservation.getStatut());
        statutLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + reservation.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");

        bonplanLabel = new Label("Bonplan : " + reservation.getBonplan().getTitre());
        bonplanLabel.setUIID("labelDefault");

        if (reservation.getDate() != null) {
            calendar = new Calendar();
            calendar.setFocusable(false);
            calendar.setDate(reservation.getDate());
            calendar.highlightDate(reservation.getDate(), "dateStart");
        }

        reservationModel.addAll(
                utilisateurLabel, bonplanLabel, nombrePersonnesLabel, dateLabel, statutLabel
        );

        if (reservation.getDate() != null) {
            reservationModel.add(calendar);
        }

        return reservationModel;
    }

    Button deleteBtn;
    Container btnsContainer;

    private Component makeReservationModel(Reservation reservation) {

        Container reservationModel = makeModelWithoutButtons(reservation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reservation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ReservationService.getInstance().delete(reservation.getId());

                if (responseCode == 200) {
                    currentReservation = null;
                    dlg.dispose();
                    reservationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        reservationModel.add(btnsContainer);

        return reservationModel;
    }

}