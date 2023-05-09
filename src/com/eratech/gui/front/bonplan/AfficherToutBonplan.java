package com.eratech.gui.front.bonplan;


import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Avis;
import com.eratech.entities.Bonplan;
import com.eratech.gui.front.avis.AjouterAvis;
import com.eratech.gui.front.reservation.AjouterReservation;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.AvisService;
import com.eratech.services.BonplanService;
import com.eratech.utils.Statics;

import java.util.ArrayList;

public class AfficherToutBonplan extends BaseForm {

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme");

    public static Bonplan currentBonplan = null;


    public AfficherToutBonplan(Resources res) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        Toolbar tb = new Toolbar(true);
        tb.setUIID("CustomToolbar");
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Bonplan");
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

    }

    Label titreLabel, descriptionLabel, typeLabel, imageLabel;

    ImageViewer imageIV;


    private Container makeModelWithoutButtons(Bonplan bonplan) {
        Container bonplanModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        bonplanModel.setUIID("containerRounded");


        titreLabel = new Label("Titre : " + bonplan.getTitre());
        titreLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + bonplan.getDescription());
        descriptionLabel.setUIID("labelDefault");

        typeLabel = new Label("Type : " + bonplan.getType());
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


        ArrayList<Avis> allAvisList = AvisService.getInstance().getAll();
        ArrayList<Avis> thisPostAvisList = new ArrayList<>();

        for (Avis avis : allAvisList) {
            if (avis.getBonplan().getId() == bonplan.getId()) {
                thisPostAvisList.add(avis);
            }
        }

        float notesSum = 0;

        for (Avis avis : thisPostAvisList) {
            notesSum += avis.getNote();
        }

        Label noteLabel = new Label("Note : " + notesSum / thisPostAvisList.size());
        noteLabel.setUIID("labelDefault");

        Button[] stars = {new Button(), new Button(), new Button(), new Button(), new Button()};
        Container starsContainer = new Container();
        starsContainer.add(noteLabel);
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            stars[indexEtoile].setPreferredH(100);
            stars[indexEtoile].setPreferredW(100);

            if (indexEtoile < (notesSum / thisPostAvisList.size()) - 0.99) {
                stars[indexEtoile].setUIID("starButton");
            } else {
                stars[indexEtoile].setUIID("starButtonOutlined");
            }

            stars[indexEtoile].setFocusable(false);
            starsContainer.add(stars[indexEtoile]);
        }

        bonplanModel.addAll(
                imageIV,
                titreLabel, descriptionLabel, typeLabel, starsContainer
        );

        return bonplanModel;
    }

    Button addReservationButton, addReviewButton;
    Container btnsContainer;

    private Component makeBonplanModel(Bonplan bonplan) {

        Container bonplanModel = makeModelWithoutButtons(bonplan);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        addReservationButton = new Button("Réservez");
        addReservationButton.addActionListener(action -> {
            currentBonplan = bonplan;
            new AjouterReservation(this).show();
        });

        addReviewButton = new Button("Ajouter avis");
        addReviewButton.addActionListener(action -> {
            currentBonplan = bonplan;
            new AjouterAvis(this).show();
        });

        btnsContainer.add(BorderLayout.WEST, addReservationButton);
        btnsContainer.add(BorderLayout.EAST, addReviewButton);


        bonplanModel.add(btnsContainer);

        return bonplanModel;
    }

}