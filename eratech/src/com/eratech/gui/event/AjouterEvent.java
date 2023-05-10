package com.eratech.gui.event;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import com.eratech.entities.Event;
import com.eratech.services.EventService;


/**
 *
 * @author houss
 */
public class AjouterEvent extends Form {

    public AjouterEvent(Form previous) {
        setTitle("Add a new event");
        setLayout(BoxLayout.y());

        TextField Titre = new TextField("", "Titre");
        TextField description = new TextField("", "description");
        TextField organisation = new TextField("", "organisation");
        Picker date = new Picker();

        Button btnValider = new Button("Add event");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((Titre.getText().length() == 0) || (description.getText().length() == 0) || (organisation.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                        Event p = new Event();
                        p.setDescription_event(description.getText());
                        p.setTime_event(date.getDate());
                        p.setOrganisation(organisation.getText());
                        p.setTitle_event(Titre.getText());

                        if (EventService.getInstance().addcommande(p)) {
                            Dialog.show("Success", "evenement ajoutée ", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "there is problem in adding ", new Command("OK"));
                    }

                }

            }
        });

        addAll(Titre, description, organisation, date, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
