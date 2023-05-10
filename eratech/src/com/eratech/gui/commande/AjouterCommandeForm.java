/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eratech.gui.commande;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.eratech.entities.commande;
import com.eratech.entities.panier;
import com.eratech.gui.utilisateur.SessionManager;
import com.eratech.services.commandeService;


/**
 *
 * @author houss
 */
public class AjouterCommandeForm extends Form {

    public AjouterCommandeForm(Form previous) {
        setTitle("Add a new commande");
        setLayout(BoxLayout.y());

         TextField nomF = new TextField("","nom");
         nomF.setText(SessionManager.getUserName());
        TextField adresseTF = new TextField("", "adresse");

        Picker date = new Picker();
        Button btnValider = new Button("Add commande");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (adresseTF.getText().length() == 0) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                      
                        commande c = new commande();
                        c.setAdresse(adresseTF.getText());
                        c.setDate_cmd(date.getDate());
                        if (commandeService.getInstance().addcommande(c)) {
                            Dialog.show("Success", "commande ajoutée ", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "there is problem in adding ", new Command("OK"));
                    }

                }

            }
        });

        addAll( nomF,adresseTF,date, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
