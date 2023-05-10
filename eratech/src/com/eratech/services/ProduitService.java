package com.eratech.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.eratech.entities.Categorie;
import com.eratech.entities.Produit;
import com.eratech.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProduitService {

    public static ProduitService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Produit> listProduits;


    private ProduitService() {
        cr = new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public ArrayList<Produit> getAll() {
        listProduits = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/produit");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listProduits = getList();
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

        return listProduits;
    }

    private ArrayList<Produit> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Produit produit = new Produit(
                        (String) obj.get("ref"),
                        (String) obj.get("libelle"),
                        (String) obj.get("description"),
                        (String) obj.get("image"),
                        Float.parseFloat(obj.get("prixVente").toString()),
                        makeCategorie((Map<String, Object>) obj.get("categorie"))

                );

                listProduits.add(produit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listProduits;
    }

    public Categorie makeCategorie(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Categorie categorie = new Categorie();
        categorie.setId((int) Float.parseFloat(obj.get("id").toString()));
        categorie.setNom((String) obj.get("nom"));
        return categorie;
    }

    public int add(Produit produit) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Produit.jpg");


        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/produit/add");

        try {
            cr.addData("file", produit.getImage(), "image/jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cr.addArgumentNoEncoding("image", produit.getImage());

        cr.addArgumentNoEncoding("ref", produit.getRef());
        cr.addArgumentNoEncoding("libelle", produit.getLibelle());
        cr.addArgumentNoEncoding("description", produit.getDescription());
        cr.addArgumentNoEncoding("image", produit.getImage());
        cr.addArgumentNoEncoding("prixVente", String.valueOf(produit.getPrixVente()));
        cr.addArgumentNoEncoding("categorie", String.valueOf(produit.getCategorie().getId()));


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

    public int edit(Produit produit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Produit.jpg");

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/produit/edit");
        cr.addArgumentNoEncoding("ref", String.valueOf(produit.getRef()));

        if (imageEdited) {
            try {
                cr.addData("file", produit.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", produit.getImage());
        }

        cr.addArgumentNoEncoding("libelle", produit.getLibelle());
        cr.addArgumentNoEncoding("description", produit.getDescription());
        cr.addArgumentNoEncoding("image", produit.getImage());
        cr.addArgumentNoEncoding("prixVente", String.valueOf(produit.getPrixVente()));
        cr.addArgumentNoEncoding("categorie", String.valueOf(produit.getCategorie().getId()));


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

    public int delete(String produitId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/produit/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("ref", String.valueOf(produitId));

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
