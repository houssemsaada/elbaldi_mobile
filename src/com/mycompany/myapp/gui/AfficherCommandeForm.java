/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;

import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.IconHolder;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.myapp.entities.commande;
import com.mycompany.myapp.services.commandeService;
;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author houss
 */


public class AfficherCommandeForm extends Form {

    public AfficherCommandeForm(Form previous) {
        setTitle("Liste de commandes");
        ArrayList<commande> commandes = commandeService.getInstance().getAllCommandes();

        setLayout(BoxLayout.y());

        Container panel = new Container(new GridLayout(commandes.size(), 6));
        panel.add(new Label("ID"));
        panel.add(new Label("Etat"));
        panel.add(new Label("Date"));
        panel.add(new Label("Total"));
        panel.add(new Label("Adresse"));
        panel.add(new Label("supprimer"));
        for (commande c : commandes) {
            TextField idField = new TextField(String.valueOf(c.getId_cmd()));
            TextField etatField = new TextField(c.getEtat());
            TextField dateField = new TextField(c.getDate_cmd() + "");
            TextField totalField = new TextField(String.valueOf(c.getTotal()));
            TextField adresseField = new TextField(c.getAdresse());
            panel.add(idField);
            panel.add(etatField);
            panel.add(dateField);
            panel.add(totalField);
            panel.add(adresseField);
            Button btnValider = new Button("supprimer");

            btnValider.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (commandeService.getInstance().supprimercommande(c)) {
                        Dialog.show("Success", "commande supprimée ! ", new Command("OK"));
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
Form currentForm = Display.getInstance().getCurrent();
 removeAll();
 
     new AfficherCommandeForm(previous).show();

                }
            });
            panel.add(btnValider);

        }
        add(panel);
        setVisible(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

    private AfficherCommandeForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addElement(commande commande) {

        CheckBox cb = new CheckBox(commande.getId_cmd() + "");
        cb.setEnabled(false);
        if (commande.getTotal() == 77) {
            cb.setSelected(true);
        }

        add(cb);

    }

}
