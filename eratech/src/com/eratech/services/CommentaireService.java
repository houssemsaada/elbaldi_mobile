package com.eratech.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.eratech.entities.Commentaire;
import com.eratech.entities.Produit;
import com.eratech.entities.Utilisateur;
import com.eratech.gui.utilisateur.SessionManager;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentaireService {

    public static CommentaireService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commentaire> listCommentaires;


    private CommentaireService() {
        cr = new ConnectionRequest();
    }

    public static CommentaireService getInstance() {
        if (instance == null) {
            instance = new CommentaireService();
        }
        return instance;
    }

    public ArrayList<Commentaire> getAll() {
        listCommentaires = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commentaire");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommentaires = getList();
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

        return listCommentaires;
    }

    private ArrayList<Commentaire> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commentaire commentaire = new Commentaire(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("contenu"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("date")),
                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur")),
                        makeProduit((Map<String, Object>) obj.get("produit"))

                );

                listCommentaires.add(commentaire);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listCommentaires;
    }

    public Utilisateur makeUtilisateur(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(SessionManager.getId());
        utilisateur.setEmail(SessionManager.getEmail());
        utilisateur.setUsername(SessionManager.getUserName());
        System.out.println(utilisateur.toString());
        return utilisateur;
    }

    public Produit makeProduit(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setRef(obj.get("ref").toString());
        produit.setLibelle((String) obj.get("libelle"));
        return produit;
    }

    public int add(Commentaire commentaire) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/commentaire/add");

        cr.addArgument("contenu", commentaire.getContenu());
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(commentaire.getDate()));
        cr.addArgument("utilisateur", String.valueOf(SessionManager.getId()));
        cr.addArgument("produit", String.valueOf(commentaire.getProduit().getRef()));


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

    public int edit(Commentaire commentaire) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/commentaire/edit");
        cr.addArgument("id", String.valueOf(commentaire.getId()));

        cr.addArgument("contenu", commentaire.getContenu());
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(commentaire.getDate()));
        cr.addArgument("utilisateur", String.valueOf(commentaire.getUtilisateur().getId()));
        cr.addArgument("produit", String.valueOf(commentaire.getProduit().getRef()));


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

    public int delete(int commentaireId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commentaire/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(commentaireId));

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
