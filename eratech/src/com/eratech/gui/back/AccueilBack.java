package com.eratech.gui.back;

import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.gui.Login;
import com.eratech.gui.uikit.SignInForm;
import com.eratech.gui.utilisateur.SessionManager;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Resources res ; 
    Label label;
    Form previous;
    public static Form accueilForm;

public AccueilBack(Resources res) {
    super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
    this.accueilForm = this;
    this.res = res;
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
        Button logoutbtn = new Button();
                logoutbtn.setMaterialIcon(FontImage.MATERIAL_ARROW_BACK_IOS);

          logoutbtn.addActionListener(action -> {
            new SignInForm(theme).show();
            SessionManager.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
            System.out.println(SessionManager.getEmail());
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
//        userContainer.add(BorderLayout.WEST, logoutbtn);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeProduitsButton(),
                makeCategoriesButton(),
                makeQuestionsButton(),
                makeQuizsButton(),
                 makeBonplansButton(),
                makeReservationsButton(),
                 makeCommandesButton(),
                 makeEventButton(),
                makelogoutButton()
                 

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
     private Button makeEventButton() {
        Button button = new Button("Evenement");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.eratech.gui.event.AfficherEventBack(this).show());
        return button;
    }
       private Button makelogoutButton() {
        Button button = new Button("Logout");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action ->  new com.eratech.gui.utilisateur.SignInForm(theme).show());
            SessionManager.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
        return button;
    }
           
}
