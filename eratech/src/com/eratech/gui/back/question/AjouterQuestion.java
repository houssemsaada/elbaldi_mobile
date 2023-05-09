package com.eratech.gui.back.question;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Question;
import com.eratech.entities.Quiz;
import com.eratech.services.QuestionService;
import com.eratech.services.QuizService;

import java.util.ArrayList;

public class AjouterQuestion extends Form {


    TextField difficulteTF;
    TextField questionnTF;
    TextField reponse1TF;
    TextField reponse2TF;
    TextField reponse3TF;
    TextField solutionTF;
    Label difficulteLabel;
    Label questionnLabel;
    Label reponse1Label;
    Label reponse2Label;
    Label reponse3Label;
    Label solutionLabel;


    ArrayList<Quiz> listQuizs;
    PickerComponent quizPC;
    Quiz selectedQuiz = null;


    Button manageButton;

    Form previous;

    public AjouterQuestion(Form previous) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] quizStrings;
        int quizIndex;
        quizPC = PickerComponent.createStrings("").label("Quiz");
        listQuizs = QuizService.getInstance().getAll();
        quizStrings = new String[listQuizs.size()];
        quizIndex = 0;
        for (Quiz quiz : listQuizs) {
            quizStrings[quizIndex] = quiz.getNom();
            quizIndex++;
        }
        if (listQuizs.size() > 0) {
            quizPC.getPicker().setStrings(quizStrings);
            quizPC.getPicker().addActionListener(l -> selectedQuiz = listQuizs.get(quizPC.getPicker().getSelectedStringIndex()));
        } else {
            quizPC.getPicker().setStrings("");
        }


        difficulteLabel = new Label("Difficulte : ");
        difficulteLabel.setUIID("labelDefault");
        difficulteTF = new TextField();
        difficulteTF.setHint("Tapez le difficulte");


        questionnLabel = new Label("Questionn : ");
        questionnLabel.setUIID("labelDefault");
        questionnTF = new TextField();
        questionnTF.setHint("Tapez le questionn");


        reponse1Label = new Label("Reponse1 : ");
        reponse1Label.setUIID("labelDefault");
        reponse1TF = new TextField();
        reponse1TF.setHint("Tapez le reponse1");


        reponse2Label = new Label("Reponse2 : ");
        reponse2Label.setUIID("labelDefault");
        reponse2TF = new TextField();
        reponse2TF.setHint("Tapez le reponse2");


        reponse3Label = new Label("Reponse3 : ");
        reponse3Label.setUIID("labelDefault");
        reponse3TF = new TextField();
        reponse3TF.setHint("Tapez le reponse3");


        solutionLabel = new Label("Solution : ");
        solutionLabel.setUIID("labelDefault");
        solutionTF = new TextField();
        solutionTF.setHint("Tapez le solution");


        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                difficulteLabel, difficulteTF,
                questionnLabel, questionnTF,
                reponse1Label, reponse1TF,
                reponse2Label, reponse2TF,
                reponse3Label, reponse3TF,
                solutionLabel, solutionTF,
                quizPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = QuestionService.getInstance().add(
                        new Question(


                                selectedQuiz,
                                difficulteTF.getText(),
                                questionnTF.getText(),
                                reponse1TF.getText(),
                                reponse2TF.getText(),
                                reponse3TF.getText(),
                                solutionTF.getText()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Question ajouté avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de question. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutQuestion) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (difficulteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Difficulte vide", new Command("Ok"));
            return false;
        }


        if (questionnTF.getText().equals("")) {
            Dialog.show("Avertissement", "Questionn vide", new Command("Ok"));
            return false;
        }


        if (reponse1TF.getText().equals("")) {
            Dialog.show("Avertissement", "Reponse1 vide", new Command("Ok"));
            return false;
        }


        if (reponse2TF.getText().equals("")) {
            Dialog.show("Avertissement", "Reponse2 vide", new Command("Ok"));
            return false;
        }


        if (reponse3TF.getText().equals("")) {
            Dialog.show("Avertissement", "Reponse3 vide", new Command("Ok"));
            return false;
        }


        if (solutionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Solution vide", new Command("Ok"));
            return false;
        }


        if (selectedQuiz == null) {
            Dialog.show("Avertissement", "Veuillez choisir un quiz", new Command("Ok"));
            return false;
        }
 
        ArrayList<Question> listQuestions = QuestionService.getInstance().getAll();
         for (Question question :  listQuestions) {
        if (question.getQuestionn().equals(questionnTF.getText())) {
            Dialog.show("Avertissement", "Cette question existe déja", new Command("Ok"));
            return false;
        }}


        return true;
    }
}