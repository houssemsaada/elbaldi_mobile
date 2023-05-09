package com.eratech.gui.front.categorie;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.eratech.MainApp;
import com.eratech.entities.Categorie;
import com.eratech.entities.Produit;
import com.eratech.gui.front.produit.AfficherToutProduit;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.CategorieService;
import com.eratech.services.ProduitService;

import java.util.ArrayList;
import java.util.List;

public class AfficherToutCategorie extends BaseForm {

    Form previous;

    public static Categorie currentCategorie = null;

    TextField searchTF;
    ArrayList<Component> componentModels;


    public AfficherToutCategorie(Resources res) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        Toolbar tb = new Toolbar(true);
        tb.setUIID("CustomToolbar");
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Categorie");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }

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

        ArrayList<Categorie> listCategories = CategorieService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher catégorie par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Categorie categorie : listCategories) {
                if (categorie.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeCategorieModel(categorie);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listCategories.size() > 0) {
            for (Categorie categorie : listCategories) {
                Component model = makeCategorieModel(categorie);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
    }

    Label nomLabel, descriptionLabel;


    private Container makeModelWithoutButtons(Categorie categorie) {
        Container categorieModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        categorieModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + categorie.getNom());
        nomLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + categorie.getDescription());
        descriptionLabel.setUIID("labelDefault");


        categorieModel.addAll(

                nomLabel, descriptionLabel
        );

        return categorieModel;
    }

    Button displayProduitsBtn;
    Container btnsContainer;

    private Component makeCategorieModel(Categorie categorie) {

        Container categorieModel = makeModelWithoutButtons(categorie);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        displayProduitsBtn = new Button("Afficher produits");
        displayProduitsBtn.addActionListener(action -> {
            ArrayList<Produit> produitArrayList = new ArrayList<>();

            for (Produit produit : ProduitService.getInstance().getAll()) {
                if (produit.getCategorie().getId() == categorie.getId()){
                    produitArrayList.add(produit);
                }
            }

            new AfficherToutProduit(MainApp.res, produitArrayList).show();
        });

        btnsContainer.add(BorderLayout.CENTER, displayProduitsBtn);


        categorieModel.add(btnsContainer);

        return categorieModel;
    }

}