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
import com.eratech.entities.Event;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

/**
 *
 * @author houss
 */
public class EventService {

    public ArrayList<Event> commandes;

    public static EventService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private EventService() {
        req = new ConnectionRequest();
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public boolean addcommande(Event t) {

        String title_event = t.getTitle_event();
        String description_event = t.getDescription_event();
        String organisation = t.getOrganisation();
        Date time_event = t.getTime_event();

        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL2 + "event/newmobile?" + "title_event=" + title_event + "&description_event=" + description_event + "&organisation=" + organisation + "&time_event=" + time_event;

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

    public ArrayList<Event> parseCommandes(String jsonText) throws ParseException {
        try {
            commandes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Event c = new Event();
                float id = Float.parseFloat(obj.get("idEvent").toString());
                c.setId((int) id);
                c.setTitle_event(obj.get("titleEvent").toString());
                String dateString = obj.get("timeEvent").toString(); // Get the date string from the JSON object
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Specify the format of the date string
                Date date = (Date) format.parse(dateString); // Parse the date string to a java.util.Date object
                c.setTime_event(date);
                c.setOrganisation(obj.get("Organisation").toString());
                c.setDescription_event(obj.get("descriptionEvent").toString());

                commandes.add(c);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return commandes;
    }

    public ArrayList<Event> getAllCommandes() {
        String url = Statics.BASE_URL2 + "event/Allevents";
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
