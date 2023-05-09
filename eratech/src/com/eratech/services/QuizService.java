package com.eratech.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.eratech.entities.Quiz;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizService {

    public static QuizService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Quiz> listQuizs;


    private QuizService() {
        cr = new ConnectionRequest();
    }

    public static QuizService getInstance() {
        if (instance == null) {
            instance = new QuizService();
        }
        return instance;
    }

    public ArrayList<Quiz> getAll() {
        listQuizs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/quiz");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listQuizs = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listQuizs;
    }

    private ArrayList<Quiz> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Quiz quiz = new Quiz(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("nom"),
                        (String) obj.get("difficulte"),
                        (String) obj.get("image")
                );

                listQuizs.add(quiz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listQuizs;
    }

    public int add(Quiz quiz) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Quiz.jpg");


        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/quiz/add");
        try {
            cr.addData("file", quiz.getImage(), "image/jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        cr.addArgumentNoEncoding("image", quiz.getImage());

        cr.addArgumentNoEncoding("nom", quiz.getNom());
        cr.addArgumentNoEncoding("difficulte", quiz.getDifficulte());
        cr.addArgumentNoEncoding("image", quiz.getImage());


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int edit(Quiz quiz, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Quiz.jpg");

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/quiz/edit");
        cr.addArgumentNoEncoding("id", String.valueOf(quiz.getId()));

        if (imageEdited) {
            try {
                cr.addData("file", quiz.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", quiz.getImage());
        }

        cr.addArgumentNoEncoding("nom", quiz.getNom());
        cr.addArgumentNoEncoding("difficulte", quiz.getDifficulte());
        cr.addArgumentNoEncoding("image", quiz.getImage());


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int quizId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/quiz/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(quizId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
