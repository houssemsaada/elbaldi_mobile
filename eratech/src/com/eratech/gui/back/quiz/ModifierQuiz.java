package com.eratech.gui.back.quiz;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Quiz;
import com.eratech.services.QuizService;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;

public class ModifierQuiz extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Quiz currentQuiz;

    TextField nomTF;
  ComboBox<String> difficulteTF;
    Label nomLabel;
    Label difficulteLabel;
    Label imageLabel;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public ModifierQuiz(Form previous) {
        super("Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentQuiz = AfficherToutQuiz.currentQuiz;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


         difficulteLabel = new Label("Difficulte : ");
        difficulteLabel.setUIID("labelDefault");
        String[] difficulteOptions = {"Facile", "Moyenne", "Difficile"};
        difficulteTF = new ComboBox<>(difficulteOptions); // Utilisation de ComboBox

        // Récupérer la valeur sélectionnée dans le ComboBox
        String difficulteSelectionnee = (String) difficulteTF.getSelectedItem();

       


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        nomTF.setText(currentQuiz.getNom());
       difficulteTF.setSelectedItem(currentQuiz.getDifficulte());



        if (currentQuiz.getImage() != null) {
            selectedImage = currentQuiz.getImage();
            String url = Statics.QUIZ_IMAGE_URL + currentQuiz.getImage();
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


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                nomLabel, nomTF,
                difficulteLabel, difficulteTF,
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
                String difficulteSelectionnee = (String) difficulteTF.getSelectedItem();
                int responseCode = QuizService.getInstance().edit(
                        new Quiz(
                                currentQuiz.getId(),
                                nomTF.getText(),
                                 difficulteSelectionnee,
                                selectedImage

                        ), imageEdited
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Quiz modifié avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de quiz. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutQuiz) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }


       if (difficulteTF.getSelectedItem() == null || difficulteTF.getSelectedItem().toString().equals(""))   {
            Dialog.show("Avertissement", "Difficulte vide", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


ArrayList<Quiz> listQuizs = QuizService.getInstance().getAll();
for (Quiz quiz : listQuizs) {
     if (quiz.getNom().equals(nomTF.getText()) && quiz.getId() != currentQuiz.getId()) {
        Dialog.show("Avertissement", "Le nom doit être unique", new Command("Ok"));
        return false;
    }
}

   
        return true;
    }
}
