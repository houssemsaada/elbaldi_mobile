/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author houss
 */
public class HomeForm extends Form{

    public HomeForm() {
        
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnAddCommande = new Button("Ajouter commande");
        Button btnListCommande = new Button("Afficher commande");
        
     btnAddCommande.addActionListener(e-> new AjouterCommandeForm(this).show());
        btnListCommande.addActionListener(e-> new AfficherCommandeForm(this).show());
        addAll(btnAddCommande,btnListCommande);
        
        
    }
    
    
}