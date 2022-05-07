/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.enities.Produits;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yeekt
 */
public class ProduitService {
      public ArrayList<Produits> produits;  
    
    public static ProduitService instance=null; 
    public boolean resultOK;
    private ConnectionRequest req;
    
    private ProduitService() {
         req =  new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }
    
      public ArrayList<Produits> parseProduit(String jsonText){
        try {
        produits  =new ArrayList<Produits>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksDestinationJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksDestinationJson.get("root");
            for(Map<String,Object> obj : list){
                Produits t = new Produits ();
                //id
                float id = Float.parseFloat(obj.get("idProd").toString());
                t.setIdProd((int)id);
                //nom
                if (obj.get("refProd")==null)
                t.setRefProd("null");
                else
                t.setRefProd(obj.get("refProd").toString());
                //designation
                if (obj.get("designation")==null)
                t.setDesignation("null");
                else
                    t.setDesignation(obj.get("designation").toString());
                //prix
                 if (obj.get("prix")==null)
                t.setPrix(0f);
                else
                t.setPrix(Float.parseFloat(obj.get("prix").toString()));
                 //image
                 if (obj.get("image")==null)
                t.setImage("null");
                else
                    t.setImage(obj.get("image").toString());
              produits.add(t);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            
        }
        return produits;
    }
    
       public ArrayList<Produits> getAllProduits(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"produit/ProduitMobile";
        req.setUrl(url);
        req.setPost(false); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProduit(new String(req.getResponseData()));
                           

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return produits;
    }
}
