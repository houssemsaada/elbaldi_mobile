package com.eratech.gui.event;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author houss
 */
public class HomeFormSkander extends Form{

    public HomeFormSkander() {
        
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnAddCommande = new Button("Ajouter event");
        Button btnListCommande = new Button("Afficher event");
        
     btnAddCommande.addActionListener(e-> new AjouterEvent(this).show());
        btnListCommande.addActionListener(e-> new AfficherEvent(this).show());
        addAll(btnAddCommande,btnListCommande);
        
        
    }
    
    
}