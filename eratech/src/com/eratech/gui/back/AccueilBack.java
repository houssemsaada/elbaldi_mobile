package com.eratech.gui.back;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.gui.Login;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;
    Form previous;
    public static Form accueilForm;

    public AccueilBack(Form previous) {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        accueilForm = this;
        addGUIs();
    }

    private void addGUIs() {
        label = new Label("Espace administrateur"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeProduitsButton(),
                makeCategoriesButton(),
                makeQuestionsButton(),
                makeQuizsButton(),
                 makeBonplansButton(),
                makeReservationsButton(),
                 makeCommandesButton()

                 

        );

        this.add(menuContainer);
    }

    private Button makeQuestionsButton() {
        Button button = new Button("Questions");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.back.question.AfficherToutQuestion(this).show());
        return button;
    }

    private Button makeQuizsButton() {
        Button button = new Button("Quizs");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.back.quiz.AfficherToutQuiz(this).show());
        return button;
    }
      private Button makeProduitsButton() {
        Button button = new Button("Produits");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.back.produit.AfficherToutProduit(this).show());
        return button;
    }


    private Button makeCategoriesButton() {
        Button button = new Button("Catégories");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.back.categorie.AfficherToutCategorie(this).show());
        return button;
    }
    
       private Button makeBonplansButton() {
        Button button = new Button("Bonplans");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.back.bonplan.AfficherToutBonplan(this).show());
        return button;
    }

    private Button makeReservationsButton() {
        Button button = new Button("Reservations");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.back.reservation.AfficherToutReservation(this).show());
        return button;
    }
     private Button makeCommandesButton() {
        Button button = new Button("Commandes");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.commande.AfficherCommandeForm(this).show());
        return button;
    }

}
