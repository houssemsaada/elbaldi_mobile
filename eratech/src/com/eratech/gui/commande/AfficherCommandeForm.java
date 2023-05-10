package com.eratech.gui.commande;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import com.eratech.entities.commande;
import com.eratech.services.commandeService;

import java.io.IOException;
import java.util.ArrayList;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.commande;
import com.eratech.gui.uikit.BaseForm;
import com.eratech.services.commandeService;

import java.util.ArrayList;

/**
 *
 * @author houss
 */


public class AfficherCommandeForm extends Form {
    
    Form previous;

    public static commande currentCommande = null;
    Button addBtn;

    ArrayList<Component> componentModels;      
    
     public AfficherCommandeForm(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {


        addBtn = new Button("Ajouter Une Commande");
        this.add(addBtn);


        ArrayList<commande> listCommande = commandeService.getInstance().getAllCommandes();
        componentModels = new ArrayList<>();

        
        if (listCommande.size() > 0) {
            for (commande commande : listCommande) {
                Component model = makeCommandeModel(commande);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCommande = null;
            new AjouterCommandeForm(this).show();
        });

    }
    
    Label IdLabel, etatLabel,adresseLabel,dateLabel,totalLabel;
     private Container makeModelWithoutButtons(commande commande) {
        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");


         IdLabel = new Label("Id : " + String.valueOf(commande.getId_cmd()));
         IdLabel.setUIID("labelDefault");

         etatLabel = new Label("Etat : " + commande.getEtat());
         etatLabel.setUIID("labelDefault");

         adresseLabel = new Label("Adresse : " + commande.getAdresse());
        adresseLabel.setUIID("labelDefault");
        
       dateLabel = new Label("Date : " + commande.getDate_cmd() + "");
      dateLabel.setUIID("labelDefault");
      
      totalLabel = new Label("Total : " + String.valueOf(commande.getTotal()));
      totalLabel.setUIID("labelDefault");


        commandeModel.addAll(

                IdLabel, etatLabel,adresseLabel,dateLabel,totalLabel
        );

        return commandeModel;
    }
     Button editBtn, deleteBtn;
    Container btnsContainer;

       private Component makeCommandeModel(commande commande) {

        Container commandeModel = makeModelWithoutButtons(commande);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.addActionListener(action -> {
            currentCommande = commande;
            new AjouterCommandeForm(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer cette catégorie ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                boolean responseCode = commandeService.getInstance().supprimercommande(commande);

                if (responseCode == true) {
                    currentCommande = null;
                    dlg.dispose();
                    commandeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression de cette catégorie. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        commandeModel.add(btnsContainer);

        return commandeModel;
    }
}

   
