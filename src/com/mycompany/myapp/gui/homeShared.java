/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import com.mycompany.myapp.gui.GetOffres;
import java.io.IOException;

/**
 *
 * @author user
 */
public class homeShared extends Form{
    Form current;
    private Resources theme;
      public homeShared() {
         
        setTitle("Liste des Destinations");
        setScrollableY(true);
        Form hi = new Form("Bienvenue chez shared");
        
      
       
        
      
        hi.getToolbar().addCommandToSideMenu("Liste des offres", theme.getImage("round.png"), e->{
           try {
               new GetOffres().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        hi.getToolbar().addCommandToSideMenu("Liste des produits", theme.getImage("round.png"), e->{
           try {
               new ProduitsListe(current).show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        hi.getToolbar().addCommandToSideMenu("Ajouter Produit", theme.getImage("round.png"), e->{
           try {
               new ProduitAjout().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
           }
        });
   
        hi.show();
}
}