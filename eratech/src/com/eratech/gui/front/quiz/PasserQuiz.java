package com.eratech.gui.front.quiz;


import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.eratech.entities.Question;
import com.eratech.entities.Quiz;
import com.eratech.services.QuestionService;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PasserQuiz extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");

    Quiz currentQuiz;

    Label nomLabel;

    ImageViewer imageIV;

    Form previous;
    Map<String, ButtonGroup> reponses = new HashMap<>();
    int list;
    
    public PasserQuiz(Form previous) {
        super("Quiz", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentQuiz = AfficherToutQuiz.currentQuiz;

        addGUIs();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    ArrayList<Question> listQuestions;
    Button submitButton;

    private void addGUIs() {
        nomLabel = new Label("Quizz : " + currentQuiz.getNom());
        nomLabel.setUIID("labelDefault");

        listQuestions = QuestionService.getInstance().getAll();
        


        if (currentQuiz.getImage() != null) {
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


        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        submitButton = new Button("Voir le score");
        submitButton.addActionListener(actionEvent -> submit());

        container.addAll(
                imageIV,
                nomLabel
        );
   
        for (Question question : listQuestions) {
            if (question.getQuiz().getId() == currentQuiz.getId()) {
                container.add(makeQuestionModel(question));
                list=list+1;
            }
        }

        container.add(submitButton);
        this.addAll(container);
    }

    private Component makeQuestionModel(Question question) {

        Container questionModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        questionModel.setUIID("containerRounded");

        SpanLabel questionnLabel = new SpanLabel("Question : " + question.getQuestionn());
        questionnLabel.setUIID("labelDefault");

        RadioButton choice1 = new RadioButton(question.getReponse1());
        RadioButton choice2 = new RadioButton(question.getReponse2());
        RadioButton choice3 = new RadioButton(question.getReponse3());
        Container choice1Container = new Container();
        choice1Container.add(choice1);

        ButtonGroup group = new ButtonGroup();

        group.add(choice1);
        group.add(choice2);
        group.add(choice3);

        reponses.put(question.getSolution(), group);
        questionModel.addAll(questionnLabel, choice1Container, choice2, choice3);

        return questionModel;
    }

    int score = 0;

    private void submit() {
        score = 0;

        for (Map.Entry<String, ButtonGroup> entry : reponses.entrySet()) {
            if (entry.getValue().getSelected() == null) {
                Dialog.show("Erreur", " Attention!! Vous devez répondre a toutes les questions", new Command("Ok"));
                return;
            }

            String solution = entry.getKey();
            String reponseText = entry.getValue().getSelected().getText();
            if (solution.equalsIgnoreCase(reponseText)) {
                score++;
            }
        }

        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager le resultat");
        btnPartager.setTextPosition(LEFT);

        Image image;
        if (currentQuiz.getImage() != null) {
            String url = Statics.QUIZ_IMAGE_URL + currentQuiz.getImage();
            image = URLImage.createToStorage(
                    EncodedImage.createFromImage(theme.getImage("profile-pic.jpg").fill(1100, 500), false),
                    url,
                    url,
                    URLImage.RESIZE_SCALE
            );
        } else {
            image = theme.getImage("profile-pic.jpg").fill(1100, 500);
        }
        String path = null;
        if (image != null) {
            path = FileSystemStorage.getInstance().getAppHomePath() + "myImage.png";
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(path)) {
                ImageIO.getImageIO().save(image, os, ImageIO.FORMAT_PNG, 1.0f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        btnPartager.setImageToShare(path, "image/png");
        if (score <2) {
            btnPartager.setTextToShare(currentQuiz.toString() + "\n Ce quiz a été échoué avec un score de " + score + "/" + list);
        } else {
            btnPartager.setTextToShare(currentQuiz.toString() + "\n Ce quiz a été passé avec succes avec un score de " + score + "/" + list);
            
        }

        this.removeAll();
        this.revalidate();
        this.add(btnPartager);
        this.revalidate();
        this.refreshTheme();

        if (score <2) {
            Dialog.show("Echec", "Vous avez échoué le quiz avec un score de " + score + "/" + list, new Command("Ok"));
        } else {
           Dialog.show("Félicitation", "Vous avez passé le quiz avec un score de " + score + "/" + list, new Command("Ok"));
            
        }

    }
}