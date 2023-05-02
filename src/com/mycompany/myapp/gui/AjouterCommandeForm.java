/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

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
import com.mycompany.myapp.entities.commande;
import com.mycompany.myapp.entities.panier;
import com.mycompany.myapp.services.commandeService;

/**
 *
 * @author houss
 */
public class AjouterCommandeForm extends Form {

    public AjouterCommandeForm(Form previous) {
        setTitle("Add a new commande");
        setLayout(BoxLayout.y());

        TextField etatTF = new TextField("", "Etat");
        TextField adresseTF = new TextField("", "adresse");
        TextField totalTF = new TextField("", "total");
        TextField idPanierTF = new TextField("", "panier id");
        Picker date = new Picker();

        Button btnValider = new Button("Add commande");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((etatTF.getText().length() == 0) || (adresseTF.getText().length() == 0) || (totalTF.getText().length() == 0) || (idPanierTF.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        panier p = new panier();
                        p.setId_panier(Integer.parseInt(idPanierTF.getText()));
                        commande c = new commande(p, etatTF.getText(), date.getDate());
                        c.setAdresse(adresseTF.getText());
                        c.setTotal(Float.parseFloat(totalTF.getText()));
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

        addAll(etatTF, adresseTF,totalTF,idPanierTF,date, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
