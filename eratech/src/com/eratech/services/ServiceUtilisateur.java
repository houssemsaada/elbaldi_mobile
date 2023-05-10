/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eratech.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.eratech.gui.utilisateur.ProfileForm;
import com.eratech.gui.utilisateur.SessionManager;

//import com.mycompany.gui.SessionManager;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class ServiceUtilisateur {

    //singleton 
    public static ServiceUtilisateur instance = null;

    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ServiceUtilisateur getInstance() {
        if (instance == null) {
            instance = new ServiceUtilisateur();
        }
        return instance;
    }

    public ServiceUtilisateur() {
        req = new ConnectionRequest();

    }

    //Signup
    public void signup(TextField username, TextField prenom, TextField password, TextField email, TextField confirmPassword, Resources res) {

        String url = "http://127.0.0.1:8000/registerApi?Nom=" + username.getText().toString() + "&Prenom=" + prenom.getText().toString()
                + "&email=" + email.getText().toString() + "&Password=" + password.getText().toString();

        req.setUrl(url);

        //Control saisi
        if (username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ") && prenom.getText().equals(" ")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        }

        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e) -> {

            //njib data ly7atithom fi form 
            byte[] data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 

            System.out.println("data ===>" + responseData);
        }
        );

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

//    SignIn
    public void signin(TextField email, TextField password, Resources rs) {

        String url = "http://127.0.0.1:8000/loginApiTest?email=" + email.getText().toString() + "&password=" + password.getText().toString();

        System.out.println(email.getText().toString());
        System.out.println(password.getText().toString());

        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

            try {

                if (json.equals("failed")) {
                    Dialog.show("Echec d'authentification", "Username ou mot de passe éronné", "OK", null);
                } else {
                    System.out.println("data ==" + json);
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //Session jibt id ta3 user ly3ml login w sajltha fi session ta3i
                    float id = Float.parseFloat(user.get("idUser").toString());
                    int idAsInt = (int) id;
                                        System.out.println("the id is : " + idAsInt);

                    SessionManager.setId((int) idAsInt);
                    System.out.println(SessionManager.getId()+"this is the id of the session");
                    SessionManager.setUserName(user.get("nom").toString());
                    SessionManager.setEmail(user.get("email").toString());

                    System.out.println("current user" + SessionManager.getEmail() + "," + SessionManager.getPassowrd());

                    if (user.size() > 0) // l9a user
                    {
                        new ProfileForm(rs).show();
                    }
                    System.out.println("welcome");

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void sms(TextField email, Resources rs) {

        String url = "http://127.0.0.1:8000/forgetPasswordApi?email=" + email.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    //heki 5dmtha taw nhabtha ala description
    public String getPasswordByEmail(String email, Resources rs) {

        String url = "http://localhost:8000/getPasswordByEmail?email=" + email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            json = new String(req.getResponseData()) + "";

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }

}
