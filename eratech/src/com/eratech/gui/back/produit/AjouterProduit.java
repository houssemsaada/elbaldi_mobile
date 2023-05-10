package com.eratech.gui.back.produit;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Categorie;
import com.eratech.entities.Produit;
import com.eratech.services.CategorieService;
import com.eratech.services.ProduitService;
import com.eratech.utils.AlertUtils;

import java.io.IOException;
import java.util.ArrayList;

public class AjouterProduit extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;

    TextField refTF;
    TextField libelleTF;
    TextField descriptionTF;
    TextField imageTF;
    TextField prixVenteTF;
    Label refLabel;
    Label libelleLabel;
    Label descriptionLabel;
    Label imageLabel;
    Label prixVenteLabel;

    ArrayList<Categorie> listCategories;
    PickerComponent categoriePC;
    Categorie selectedCategorie = null;

    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public AjouterProduit(Form previous) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] categorieStrings;
        int categorieIndex;
        categoriePC = PickerComponent.createStrings("").label("Categorie");
        listCategories = CategorieService.getInstance().getAll();
        categorieStrings = new String[listCategories.size()];
        categorieIndex = 0;
        for (Categorie categorie : listCategories) {
            categorieStrings[categorieIndex] = categorie.getNom();
            categorieIndex++;
        }
        if (listCategories.size() > 0) {
            categoriePC.getPicker().setStrings(categorieStrings);
            categoriePC.getPicker().addActionListener(l -> selectedCategorie = listCategories.get(categoriePC.getPicker().getSelectedStringIndex()));
        } else {
            categoriePC.getPicker().setStrings("");
        }

        refLabel = new Label("Réf° : ");
        refLabel.setUIID("labelDefault");
        refTF = new TextField();
        refTF.setHint("Tapez la référence");

        libelleLabel = new Label("Libellé : ");
        libelleLabel.setUIID("labelDefault");
        libelleTF = new TextField();
        libelleTF.setHint("Tapez la libellé");

        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez la description");

        prixVenteLabel = new Label("Prix de vente : ");
        prixVenteLabel.setUIID("labelDefault");
        prixVenteTF = new TextField();
        prixVenteTF.setHint("Tapez le prix de vente");

        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Choisir une image");

        imageIV = new ImageViewer(theme.getImage("profile-pic.jpg").fill(1100, 500));

        manageButton = new Button("Ajouter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                refLabel, refTF,
                libelleLabel, libelleTF,
                descriptionLabel, descriptionTF,
                prixVenteLabel, prixVenteTF,
                categoriePC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = ProduitService.getInstance().add(
                        new Produit(
                                refTF.getText(),
                                libelleTF.getText(),
                                descriptionTF.getText(),
                                selectedImage,
                                Float.parseFloat(prixVenteTF.getText()),
                                selectedCategorie
                        )
                );
                if (responseCode == 200) {
                    AlertUtils.makeNotification("Produit ajouté avec succès");
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de ce produit. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutProduit) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (refTF.getText().equals("")) {
            Dialog.show("Avertissement", "Ref vide", new Command("Ok"));
            return false;
        }
        String reference = refTF.getText();
        if (!reference.startsWith("TUN619")) {
            Dialog.show("Avertissement", "La référence doit commencer par 'TUN619'", new Command("Ok"));
            return false;
        }

        if (libelleTF.getText().equals("")) {
            Dialog.show("Avertissement", "la Libellé est vide", new Command("Ok"));
            return false;
        }
        // Vérifier la longueur de la libelle
        int libelleLength = libelleTF.getText().length();
        if (libelleLength < 5 || libelleLength > 10) {
            Dialog.show("Avertissement", "Le libellé doit contenir entre 5 et 10 caractères", new Command("Ok"));
            return false;
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

        if (prixVenteTF.getText().equals("")) {
            Dialog.show("Avertissement", "PrixVente vide", new Command("Ok"));
            return false;
        }
        try {
            float prixVente = Float.parseFloat(prixVenteTF.getText());
            if (prixVente < 0) {
                Dialog.show("Avertissement", "Le prix de vente ne peut pas être négatif", new Command("Ok"));
                return false;
            }
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixVenteTF.getText() + " n'est pas un nombre valide (prixVente)", new Command("Ok"));
            return false;
        }

        if (selectedCategorie == null) {
            Dialog.show("Avertissement", "Veuillez choisir une catégorie", new Command("Ok"));
            return false;
        }

        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }

        return true;
    }
}
