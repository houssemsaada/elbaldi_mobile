package com.eratech.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.eratech.entities.Avis;
import com.eratech.entities.Bonplan;
import com.eratech.entities.Utilisateur;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AvisService {

    public static AvisService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Avis> listAviss;


    private AvisService() {
        cr = new ConnectionRequest();
    }

    public static AvisService getInstance() {
        if (instance == null) {
            instance = new AvisService();
        }
        return instance;
    }

    public ArrayList<Avis> getAll() {
        listAviss = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/avis");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listAviss = getList();
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

        return listAviss;
    }

    private ArrayList<Avis> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Avis avis = new Avis(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur")),
                        makeBonplan((Map<String, Object>) obj.get("bonplan")),
                        Float.parseFloat(obj.get("note").toString()),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("date"))

                );

                listAviss.add(avis);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listAviss;
    }

    public Utilisateur makeUtilisateur(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId((int) Float.parseFloat(obj.get("id").toString()));
        utilisateur.setEmail((String) obj.get("email"));
        return utilisateur;
    }

    public Bonplan makeBonplan(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Bonplan bonplan = new Bonplan();
        bonplan.setId((int) Float.parseFloat(obj.get("id").toString()));
        bonplan.setTitre((String) obj.get("titre"));
        return bonplan;
    }

    public int add(Avis avis) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/avis/add");

        cr.addArgument("utilisateur", String.valueOf(avis.getUtilisateur().getId()));
        cr.addArgument("bonplan", String.valueOf(avis.getBonplan().getId()));
        cr.addArgument("note", String.valueOf(avis.getNote()));
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(avis.getDate()));


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

    public int edit(Avis avis) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/avis/edit");
        cr.addArgument("id", String.valueOf(avis.getId()));

        cr.addArgument("utilisateur", String.valueOf(avis.getUtilisateur().getId()));
        cr.addArgument("bonplan", String.valueOf(avis.getBonplan().getId()));
        cr.addArgument("note", String.valueOf(avis.getNote()));
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(avis.getDate()));


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

    public int delete(int avisId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/avis/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(avisId));

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
