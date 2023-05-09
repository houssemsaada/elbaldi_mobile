package com.eratech.gui.front.quiz;


import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Quiz;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.QuizService;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class AfficherToutQuiz extends BaseForm {

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme");

    public static Quiz currentQuiz = null;

    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public AfficherToutQuiz(Resources res) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        Toolbar tb = new Toolbar(true);
        tb.setUIID("CustomToolbar");
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Quiz");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        addGUIs();
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        Container container = new Container();
        container.setPreferredH(250);
        this.add(container);

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
            this.add(new Label("Aucun Quiz disponible"));
        }
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

    Button prendreQuizBtn;
    Container btnsContainer;

    private Component makeQuizModel(Quiz quiz) {

        Container quizModel = makeModelWithoutButtons(quiz);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        prendreQuizBtn = new Button("Jouer Quiz");
        prendreQuizBtn.addActionListener(action -> {
            currentQuiz = quiz;
            new PasserQuiz(this).show();
        });

        btnsContainer.add(BorderLayout.EAST, prendreQuizBtn);

        quizModel.add(btnsContainer);

        return quizModel;
    }


}