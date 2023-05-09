package com.eratech.gui.back.commentaire;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Commentaire;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.CommentaireService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutCommentaire extends BaseForm {

    Form previous;

    public static Commentaire currentCommentaire = null;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;


    public AfficherToutCommentaire(Form previous) {
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
        this.add(addBtn);


        ArrayList<Commentaire> listCommentaires = CommentaireService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher commentaire par Contenu");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Commentaire commentaire : listCommentaires) {
                if (commentaire.getContenu().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeCommentaireModel(commentaire);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listCommentaires.size() > 0) {
            for (Commentaire commentaire : listCommentaires) {
                Component model = makeCommentaireModel(commentaire);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCommentaire = null;
            new AjouterCommentaire(this).show();
        });

    }

    Label contenuLabel, dateLabel, utilisateurLabel, produitLabel;


    private Container makeModelWithoutButtons(Commentaire commentaire) {
        Container commentaireModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commentaireModel.setUIID("containerRounded");


        contenuLabel = new Label("Contenu : " + commentaire.getContenu());
        contenuLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(commentaire.getDate()));
        dateLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + commentaire.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + commentaire.getProduit());
        produitLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + commentaire.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + commentaire.getProduit().getLibelle());
        produitLabel.setUIID("labelDefault");


        commentaireModel.addAll(

                contenuLabel, dateLabel, utilisateurLabel, produitLabel
        );

        return commentaireModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeCommentaireModel(Commentaire commentaire) {

        Container commentaireModel = makeModelWithoutButtons(commentaire);

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