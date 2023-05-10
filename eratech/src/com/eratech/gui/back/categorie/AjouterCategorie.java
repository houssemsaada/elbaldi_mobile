package com.eratech.gui.back.categorie;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Categorie;
import com.eratech.services.CategorieService;
import com.eratech.utils.AlertUtils;
import java.util.ArrayList;

public class AjouterCategorie extends Form {


    TextField nomTF;
    TextField descriptionTF;
    Label nomLabel;
    Label descriptionLabel;


    Button manageButton;

    Form previous;

    public AjouterCategorie(Form previous) {
        super("Ajouter Une catégorie", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez la description");


        manageButton = new Button("Ajouter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                nomLabel, nomTF,
                descriptionLabel, descriptionTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = CategorieService.getInstance().add(
                        new Categorie(


                                nomTF.getText(),
                                descriptionTF.getText()
                        )
                );
                if (responseCode == 200) {
                    AlertUtils.makeNotification("Catégorie ajoutée avec succès");
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de la catégorie. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutCategorie) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }
        

       ArrayList<Categorie> listCategories = CategorieService.getInstance().getAll();
       
         for (Categorie categorie :  listCategories) {
        if (categorie.getNom().equals(nomTF.getText())) {
            Dialog.show("Avertissement", "Le nom de catégorie doit être unique", new Command("Ok"));
            return false;
        }
         }
         
         

        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }
        
        // Vérifier la longueur de la description
    int descriptionLength = descriptionTF.getText().length();
    if (descriptionLength < 5 || descriptionLength > 10) {
        Dialog.show("Avertissement", "La description doit contenir entre 5 et 10 caractères", new Command("Ok"));
        return false;
    }
    

        return true;
    }
    }