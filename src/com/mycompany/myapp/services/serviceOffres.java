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
import com.mycompany.myapp.enities.Offres;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class serviceOffres {
     public ArrayList<Offres> offres;  
    
    public static serviceOffres instance=null; 
    public boolean resultOK;
    private ConnectionRequest req;

    private serviceOffres() {
         req =  new ConnectionRequest();
    }

    public static serviceOffres getInstance() {
        if (instance == null) {
            instance = new serviceOffres();
        }
        return instance;
    }
    
      public ArrayList<Offres> parseOffres(String jsonText){
        try {
 offres  =new ArrayList<Offres>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksDestinationJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksDestinationJson.get("root");
            for(Map<String,Object> obj : list){
                Offres t = new Offres ();
                
                //id
                float id = Float.parseFloat(obj.get("idOffre").toString());
                t.setId_offre((int)id);
                //nom
                if (obj.get("nom")==null)
                t.setNom("null");
                else
                t.setNom(obj.get("nom").toString());
                
                //description
                if (obj.get("description")==null)
                t.setDescription("null");
                else
                    t.setDescription(obj.get("description").toString());
                 
                //adresse
                 if (obj.get("prix")==null)
                t.setPrix(0f);
                else
                t.setPrix(Float.parseFloat(obj.get("prix").toString()));
                 
                 //email
                 if (obj.get("ville")==null)
                t.setVille("null");
                else
                    t.setVille(obj.get("ville").toString());
                 
              //categ
                 if (obj.get("categ")==null)
                t.setCateg("null");
                else
                    t.setCateg(obj.get("categ").toString());
                  //img
                 if (obj.get("image")==null)
                t.setImage("null");
                else
                    t.setImage(obj.get("image").toString());
                 
                offres.add(t);
            }
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            
        }
        return offres;
    }
    
       public ArrayList<Offres> getAllOffres(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"offres/Offres_mobile/";
        req.setUrl(url);
        req.setPost(false); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                offres = parseOffres(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return offres;
    }
}
