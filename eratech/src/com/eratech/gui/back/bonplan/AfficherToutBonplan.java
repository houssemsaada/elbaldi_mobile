package com.eratech.gui.back.bonplan;


import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Bonplan;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.BonplanService;
import com.eratech.utils.Statics;

import java.util.ArrayList;

public class AfficherToutBonplan extends BaseForm {

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme");

    public static Bonplan currentBonplan = null;
    Button addBtn;


    public AfficherToutBonplan(Form previous) {
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


        ArrayList<Bonplan> listBonplans = BonplanService.getInstance().getAll();


        if (listBonplans.size() > 0) {
            for (Bonplan bonplan : listBonplans) {
                Component model = makeBonplanModel(bonplan);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentBonplan = null;
            new AjouterBonplan(this).show();
        });

    }

    Label titreLabel, descriptionLabel, typeLabel, imageLabel;

    ImageViewer imageIV;


    private Container makeModelWithoutButtons(Bonplan bonplan) {
        Container bonplanModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        bonplanModel.setUIID("containerRounded");


        titreLabel = new Label("Le titre : " + bonplan.getTitre());
        titreLabel.setUIID("labelDefault");

        descriptionLabel = new Label("La description : " + bonplan.getDescription());
        descriptionLabel.setUIID("labelDefault");

        typeLabel = new Label("Le type : " + bonplan.getType());
        typeLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + bonplan.getImage());
        imageLabel.setUIID("labelDefault");

        if (bonplan.getImage() != null) {
            String url = Statics.BONPLAN_IMAGE_URL + bonplan.getImage();
            Image image = URLImage.createToStorage(
                    EncodedImage.createFromImage(theme.getImage("profile-pic.jpg").fill(1100, 500), false),
                    url,
                    url,
                    URLImage.RESIZE_SCALE
            );
            imageIV = new ImageViewer(image);
        } else {
            imageIV = new ImageViewer(theme.getImage("profile-pic.jpg").fill(1100, 500));
        }
        imageIV.setFocusable(false);

        bonplanModel.addAll(
                imageIV,
                titreLabel, descriptionLabel, typeLabel
        );

        return bonplanModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeBonplanModel(Bonplan bonplan) {

        Container bonplanModel = makeModelWithoutButtons(bonplan);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentBonplan = bonplan;
            new ModifierBonplan(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce bonplan ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = BonplanService.getInstance().delete(bonplan.getId());

                if (responseCode == 200) {
                    currentBonplan = null;
                    dlg.dispose();
                    bonplanModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du bonplan. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        bonplanModel.add(btnsContainer);

        return bonplanModel;
    }

}