package com.eratech.entities;

import com.eratech.utils.Statics;

public class Question implements Comparable<Question> {

    private int id;
    private Quiz quiz;
    private String difficulte;
    private String questionn;
    private String reponse1;
    private String reponse2;
    private String reponse3;
    private String solution;

    public Question() {
    }

    public Question(int id, Quiz quiz, String difficulte, String questionn, String reponse1, String reponse2, String reponse3, String solution) {
        this.id = id;
        this.quiz = quiz;
        this.difficulte = difficulte;
        this.questionn = questionn;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse3 = reponse3;
        this.solution = solution;
    }

    public Question(Quiz quiz, String difficulte, String questionn, String reponse1, String reponse2, String reponse3, String solution) {
        this.quiz = quiz;
        this.difficulte = difficulte;
        this.questionn = questionn;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse3 = reponse3;
        this.solution = solution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getQuestionn() {
        return questionn;
    }

    public void setQuestionn(String questionn) {
        this.questionn = questionn;
    }

    public String getReponse1() {
        return reponse1;
    }

    public void setReponse1(String reponse1) {
        this.reponse1 = reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public void setReponse2(String reponse2) {
        this.reponse2 = reponse2;
    }

    public String getReponse3() {
        return reponse3;
    }

    public void setReponse3(String reponse3) {
        this.reponse3 = reponse3;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }


    @Override
    public String toString() {
        return "Question : " +
                "id=" + id
                + "\\n Quiz=" + quiz
                + "\\n Difficulte=" + difficulte
                + "\\n Questionn=" + questionn
                + "\\n Reponse1=" + reponse1
                + "\\n Reponse2=" + reponse2
                + "\\n Reponse3=" + reponse3
                + "\\n Solution=" + solution
                ;
    }

    @Override
    public int compareTo(Question question) {
        switch (Statics.compareVar) {
            case "Quiz":
                return this.getQuiz().getNom().compareTo(question.getQuiz().getNom());
            case "Difficulte":
                return this.getDifficulte().compareTo(question.getDifficulte());
            case "Questionn":
                return this.getQuestionn().compareTo(question.getQuestionn());
            case "Reponse1":
                return this.getReponse1().compareTo(question.getReponse1());
            case "Reponse2":
                return this.getReponse2().compareTo(question.getReponse2());
            case "Reponse3":
                return this.getReponse3().compareTo(question.getReponse3());
            case "Solution":
                return this.getSolution().compareTo(question.getSolution());

            default:
                return 0;
        }
    }

}