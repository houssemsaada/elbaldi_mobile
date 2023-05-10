package com.eratech.gui.front.produit;


import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Commentaire;
import com.eratech.entities.Produit;
import com.eratech.gui.front.commentaire.AjouterCommentaire;
import com.eratech.gui.front.commentaire.ModifierCommentaire;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.CommentaireService;
import com.eratech.services.ProduitService;
import com.eratech.utils.Statics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutProduit extends BaseForm {

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme");

    public static Produit currentProduit = null;
    public static Commentaire currentCommentaire;

    TextField searchTF;
    ArrayList<Component> componentModels;

    ArrayList<Produit> listProduits;


    public AfficherToutProduit(Resources res, ArrayList<Produit> produits) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        this.listProduits = produits;

        Toolbar tb = new Toolbar(true);
        tb.setUIID("CustomToolbar");
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Produit");
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

    ArrayList<Commentaire> listCommentaires;

    private void addGUIs() {
        listCommentaires = CommentaireService.getInstance().getAll();

        Container container = new Container();
        container.setPreferredH(250);
        this.add(container);


        if (listProduits == null) listProduits = ProduitService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher produit par Libellé");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Produit produit : listProduits) {
                if (produit.getLibelle().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeProduitModel(produit);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listProduits.size() > 0) {
            for (Produit produit : listProduits) {
                Component model = makeProduitModel(produit);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
    }

    Label refLabel, libelleLabel, descriptionLabel, imageLabel, prixVenteLabel, categorieLabel;

    ImageViewer imageIV;


    private Container makeModelWithoutButtons(Produit produit) {
        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");


        refLabel = new Label("Réf° : " + produit.getRef());
        refLabel.setUIID("labelDefault");

        libelleLabel = new Label("Libellé : " + produit.getLibelle());
        libelleLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + produit.getDescription());
        descriptionLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + produit.getImage());
        imageLabel.setUIID("labelDefault");

        prixVenteLabel = new Label("Prix de Vente : " + produit.getPrixVente() + " DT");
        prixVenteLabel.setUIID("labelDefault");

        categorieLabel = new Label("Catégorie : " + produit.getCategorie());
        categorieLabel.setUIID("labelDefault");

        categorieLabel = new Label("Catégorie : " + produit.getCategorie().getNom());
        categorieLabel.setUIID("labelDefault");

        if (produit.getImage() != null) {
            String url = Statics.PRODUIT_IMAGE_URL + produit.getImage();
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


        Container commentsContainer = new Container(BoxLayout.y());

        Label commentsLabel = new Label("Commentaires : ");
        commentsLabel.setUIID("labelDefault");
        for (Commentaire commentaire : listCommentaires) {
            if (commentaire.getProduit().getRef().equalsIgnoreCase(produit.getRef())) {
                commentsContainer.add(makeCommentaireModel(commentaire));
            }
        }

        produitModel.addAll(
                imageIV,
                refLabel, libelleLabel, descriptionLabel, prixVenteLabel, categorieLabel, commentsLabel, commentsContainer
        );

        return produitModel;
    }

    private Component makeProduitModel(Produit produit) {
        Container container = makeModelWithoutButtons(produit);

        Container btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        Button addBtn = new Button("Ajouter un commentaire");
        addBtn.addActionListener(action -> {
            currentCommentaire = null;
            new AjouterCommentaire(this, produit).show();
        });

        btnsContainer.add(BorderLayout.CENTER, addBtn);
        container.add(btnsContainer);

        return container;
    }


    private Container makeCommentaireModelWithoutButtons(Commentaire commentaire) {

        Label contenuLabel, dateLabel, utilisateurLabel, produitLabel;

        Container commentaireModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commentaireModel.setUIID("containerRounded");


        contenuLabel = new Label("Contenu : " + commentaire.getContenu());
        contenuLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(commentaire.getDate()));
        dateLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + commentaire.getUtilisateur().toString());
        utilisateurLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + commentaire.getProduit());
        produitLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + commentaire.getUtilisateur().getUsername());
        utilisateurLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + commentaire.getProduit().getLibelle());
        produitLabel.setUIID("labelDefault");


        commentaireModel.addAll(
                contenuLabel, dateLabel, utilisateurLabel, produitLabel
        );

        return commentaireModel;
    }


    private Component makeCommentaireModel(Commentaire commentaire) {

        Button editBtn, deleteBtn;
        Container btnsContainer;

        Container commentaireModel = makeCommentaireModelWithoutButtons(commentaire);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.addActionListener(action -> {
            currentCommentaire = commentaire;
            new ModifierCommentaire(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commentaire ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommentaireService.getInstance().delete(commentaire.getId());

                if (responseCode == 200) {
                    currentCommentaire = null;
                    dlg.dispose();
                    commentaireModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commentaire. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        commentaireModel.add(btnsContainer);

        return commentaireModel;
    }

}