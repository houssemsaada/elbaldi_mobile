package com.eratech.gui.back.avis;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Avis;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.AvisService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutAvis extends BaseForm {

    Form previous;

    public static Avis currentAvis = null;
    Button addBtn;


    public AfficherToutAvis(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {


        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Avis> listAviss = AvisService.getInstance().getAll();


        if (listAviss.size() > 0) {
            for (Avis avis : listAviss) {
                Component model = makeAvisModel(avis);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentAvis = null;
            new AjouterAvis(this).show();
        });

    }

    Label utilisateurLabel, bonplanLabel, noteLabel, dateLabel;


    private Container makeModelWithoutButtons(Avis avis) {
        Container avisModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        avisModel.setUIID("containerRounded");


        utilisateurLabel = new Label("Utilisateur : " + avis.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        bonplanLabel = new Label("Bonplan : " + avis.getBonplan());
        bonplanLabel.setUIID("labelDefault");

        noteLabel = new Label("Note : " + avis.getNote());
        noteLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(avis.getDate()));
        dateLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + avis.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");

        bonplanLabel = new Label("Bonplan : " + avis.getBonplan().getTitre());
        bonplanLabel.setUIID("labelDefault");


        avisModel.addAll(

                utilisateurLabel, bonplanLabel, noteLabel, dateLabel
        );

        return avisModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeAvisModel(Avis avis) {

        Container avisModel = makeModelWithoutButtons(avis);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentAvis = avis;
            new ModifierAvis(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce avis ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = AvisService.getInstance().delete(avis.getId());

                if (responseCode == 200) {
                    currentAvis = null;
                    dlg.dispose();
                    avisModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du avis. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        avisModel.add(btnsContainer);

        return avisModel;
    }

}