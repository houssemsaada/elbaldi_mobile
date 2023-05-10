/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eratech.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.eratech.entities.commande;
import com.eratech.entities.panier;
import com.eratech.gui.utilisateur.SessionManager;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

/**
 *
 * @author houss
 */
public class commandeService {

    public ArrayList<commande> commandes;

    public static commandeService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private commandeService() {
        req = new ConnectionRequest();
    }

    public static commandeService getInstance() {
        if (instance == null) {
            instance = new commandeService();
        }
        return instance;
    }

    public boolean addcommande(commande t) {

        String adresse = t.getAdresse();
        String etat = "En attente";
        Float total = t.getTotal();
        Date datecmd = t.getDate_cmd();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(datecmd);

        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL2 + "commande/addcommandeJSON?" + "adresse=" + adresse + "&etat=" + etat + "&total=" + total + "&DateCmd=" + dateString + "&IdPanier=" + SessionManager.getId();

        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean supprimercommande(commande t) {

        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL2 + "commande/deletecommandeJSON/" + t.getId_cmd();

        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<commande> parseCommandes(String jsonText) throws ParseException {
        try {
            commandes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                commande c = new commande();
                float id = Float.parseFloat(obj.get("idCmd").toString());
                c.setId_cmd((int) id);
                c.setEtat(obj.get("etat").toString());
                String dateString = obj.get("dateCmd").toString(); // Get the date string from the JSON object
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Specify the format of the date string
                Date date = (Date) format.parse(dateString); // Parse the date string to a java.util.Date object
                c.setDate_cmd(date);
                c.setTotal(Float.parseFloat(obj.get("total").toString()));
                c.setAdresse(obj.get("adresse").toString());
                commandes.add(c);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return commandes;
    }

    public ArrayList<commande> getAllCommandes() {
        String url = Statics.BASE_URL2 + "commande/Allcommandes";
        ConnectionRequest req = new ConnectionRequest(url);

        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {

                    System.out.println(req.getResponseData());

                    commandes = parseCommandes(new String(req.getResponseData()));

                } catch (ParseException ex) {
                    System.out.println("error in getting all commandes");
                }
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commandes;
    }
}
