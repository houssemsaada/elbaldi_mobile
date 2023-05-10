package com.eratech.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.eratech.entities.Bonplan;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BonplanService {

    public static BonplanService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Bonplan> listBonplans;


    private BonplanService() {
        cr = new ConnectionRequest();
    }

    public static BonplanService getInstance() {
        if (instance == null) {
            instance = new BonplanService();
        }
        return instance;
    }

    public ArrayList<Bonplan> getAll() {
        listBonplans = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/bonplan");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listBonplans = getList();
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

        return listBonplans;
    }

    private ArrayList<Bonplan> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Bonplan bonplan = new Bonplan(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("titre"),
                        (String) obj.get("description"),
                        (String) obj.get("type"),
                        (String) obj.get("image")

                );

                listBonplans.add(bonplan);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listBonplans;
    }

    public int add(Bonplan bonplan) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Bonplan.jpg");

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/bonplan/add");

        try {
            cr.addData("file", bonplan.getImage(), "image/jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cr.addArgumentNoEncoding("image", bonplan.getImage());

        cr.addArgumentNoEncoding("titre", bonplan.getTitre());
        cr.addArgumentNoEncoding("description", bonplan.getDescription());
        cr.addArgumentNoEncoding("type", bonplan.getType());
        cr.addArgumentNoEncoding("image", bonplan.getImage());


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

    public int edit(Bonplan bonplan, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Bonplan.jpg");

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/bonplan/edit");
        cr.addArgumentNoEncoding("id", String.valueOf(bonplan.getId()));

        if (imageEdited) {
            try {
                cr.addData("file", bonplan.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", bonplan.getImage());
        }

        cr.addArgumentNoEncoding("titre", bonplan.getTitre());
        cr.addArgumentNoEncoding("description", bonplan.getDescription());
        cr.addArgumentNoEncoding("type", bonplan.getType());
        cr.addArgumentNoEncoding("image", bonplan.getImage());


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

    public int delete(int bonplanId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/bonplan/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(bonplanId));

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
