package com.eratech.gui.back.quiz;


import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Quiz;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.QuizService;
import com.eratech.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class AfficherToutQuiz extends BaseForm {

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme");

    public static Quiz currentQuiz = null;
    Button addBtn;


    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public AfficherToutQuiz(Form previous) {
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


        ArrayList<Quiz> listQuizs = QuizService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Nom", "Difficulte").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listQuizs);
            for (Quiz quiz : listQuizs) {
                Component model = makeQuizModel(quiz);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listQuizs.size() > 0) {
            for (Quiz quiz : listQuizs) {
                Component model = makeQuizModel(quiz);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentQuiz = null;
            new AjouterQuiz(this).show();
        });

    }

    Label nomLabel, difficulteLabel, imageLabel;

    ImageViewer imageIV;


    private Container makeModelWithoutButtons(Quiz quiz) {
        Container quizModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        quizModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + quiz.getNom());
        nomLabel.setUIID("labelDefault");

        difficulteLabel = new Label("Difficulte : " + quiz.getDifficulte());
        difficulteLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + quiz.getImage());
        imageLabel.setUIID("labelDefault");

        if (quiz.getImage() != null) {
            String url = Statics.QUIZ_IMAGE_URL + quiz.getImage();
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

        quizModel.addAll(
                imageIV,
                nomLabel, difficulteLabel
        );

        return quizModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeQuizModel(Quiz quiz) {

        Container quizModel = makeModelWithoutButtons(quiz);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentQuiz = quiz;
            new ModifierQuiz(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce quiz ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = QuizService.getInstance().delete(quiz.getId());

                if (responseCode == 200) {
                    currentQuiz = null;
                    dlg.dispose();
                    quizModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du quiz. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        quizModel.add(btnsContainer);

        return quizModel;
    }

}