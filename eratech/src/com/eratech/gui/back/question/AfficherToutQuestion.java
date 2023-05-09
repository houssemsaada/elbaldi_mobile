package com.eratech.gui.back.question;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Question;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.QuestionService;
import com.eratech.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class AfficherToutQuestion extends BaseForm {

    Form previous;

    public static Question currentQuestion = null;
    Button addBtn;


    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public AfficherToutQuestion(Form previous) {
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


        ArrayList<Question> listQuestions = QuestionService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Quiz", "Difficulte", "Questionn", "Reponse1", "Reponse2", "Reponse3", "Solution").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listQuestions);
            for (Question question : listQuestions) {
                Component model = makeQuestionModel(question);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listQuestions.size() > 0) {
            for (Question question : listQuestions) {
                Component model = makeQuestionModel(question);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentQuestion = null;
            new AjouterQuestion(this).show();
        });

    }

    Label quizLabel, difficulteLabel, questionnLabel, reponse1Label, reponse2Label, reponse3Label, solutionLabel;


    private Container makeModelWithoutButtons(Question question) {
        Container questionModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        questionModel.setUIID("containerRounded");


        quizLabel = new Label("Quiz : " + question.getQuiz());
        quizLabel.setUIID("labelDefault");

        difficulteLabel = new Label("Difficulte : " + question.getDifficulte());
        difficulteLabel.setUIID("labelDefault");

        questionnLabel = new Label("Questionn : " + question.getQuestionn());
        questionnLabel.setUIID("labelDefault");

        reponse1Label = new Label("Reponse1 : " + question.getReponse1());
        reponse1Label.setUIID("labelDefault");

        reponse2Label = new Label("Reponse2 : " + question.getReponse2());
        reponse2Label.setUIID("labelDefault");

        reponse3Label = new Label("Reponse3 : " + question.getReponse3());
        reponse3Label.setUIID("labelDefault");

        solutionLabel = new Label("Solution : " + question.getSolution());
        solutionLabel.setUIID("labelDefault");

        quizLabel = new Label("Quiz : " + question.getQuiz().getNom());
        quizLabel.setUIID("labelDefault");


        questionModel.addAll(

                quizLabel, difficulteLabel, questionnLabel, reponse1Label, reponse2Label, reponse3Label, solutionLabel
        );

        return questionModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeQuestionModel(Question question) {

        Container questionModel = makeModelWithoutButtons(question);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentQuestion = question;
            new ModifierQuestion(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce question ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = QuestionService.getInstance().delete(question.getId());

                if (responseCode == 200) {
                    currentQuestion = null;
                    dlg.dispose();
                    questionModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du question. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        questionModel.add(btnsContainer);

        return questionModel;
    }

}