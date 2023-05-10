package com.eratech.gui.event;

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
import com.eratech.entities.Event;
import com.eratech.services.EventService;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author houss
 */


public class AfficherEvent extends Form {

    public AfficherEvent(Form previous) {
        setTitle("Liste de events");
        ArrayList<Event> commandes = EventService.getInstance().getAllCommandes();

        setLayout(BoxLayout.y());

        Container panel = new Container(new GridLayout(commandes.size(), 5));
        panel.add(new Label("ID"));
        panel.add(new Label("title_event"));
        panel.add(new Label("description_event"));
        panel.add(new Label("organisation"));
        panel.add(new Label("time_event"));
        for (Event c : commandes) {
            TextField idField = new TextField(String.valueOf(c.getId()));
            TextField etatField = new TextField(c.getTitle_event());
            TextField dateField = new TextField(c.getDescription_event()+ "");
            TextField totalField = new TextField(String.valueOf(c.getOrganisation()));
            TextField adresseField = new TextField(c.getTime_event()+"");
            panel.add(idField);
            panel.add(etatField);
            panel.add(dateField);
            panel.add(totalField);
            panel.add(adresseField);


        }
        add(panel);
        setVisible(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

    private AfficherEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}