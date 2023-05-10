/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eratech.gui.event;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.eratech.entities.Event;
import com.eratech.services.EventService;
import java.util.ArrayList;

/**
 *
 * @author selim
 */
public class AfficherEventBack extends Form {
     Form previous;

    public static Event currentEvent = null;
    Button addBtn;

    ArrayList<Component> componentModels;      
    
     public AfficherEventBack(Form previous) {
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


        addBtn = new Button("Ajouter un event");
        this.add(addBtn);


        ArrayList<Event> listEvent = EventService.getInstance().getAllCommandes();
        componentModels = new ArrayList<>();

        
        if (listEvent.size() > 0) {
            for (Event Event : listEvent) {
                Component model = makeEventModel(Event);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentEvent = null;
            new AjouterEvent(this).show();
        });

    }
    
    Label IdLabel, titleLabel,desLabel,dateLabel,orgLabel;
     private Container makeModelWithoutButtons(Event event) {
        Container eventModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        eventModel.setUIID("containerRounded");


         IdLabel = new Label("Id : " + String.valueOf(event.getId()));
         IdLabel.setUIID("labelDefault");

         titleLabel = new Label("title_event : " + event.getTitle_event());
         titleLabel.setUIID("labelDefault");

         desLabel = new Label("description_event : " + event.getDescription_event()+ "");
       desLabel.setUIID("labelDefault");
        
       dateLabel = new Label("time_event : " + event.getTime_event()+"");
      dateLabel.setUIID("labelDefault");
      
     orgLabel = new Label("organisation : " + String.valueOf(event.getOrganisation()));
      orgLabel.setUIID("labelDefault");


        eventModel.addAll(

                IdLabel, titleLabel,desLabel,dateLabel,orgLabel
        );

        return eventModel;
    }
     Button editBtn, deleteBtn;
    Container btnsContainer;

       private Component makeEventModel(Event event) {

        Container eventModel = makeModelWithoutButtons(event);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.addActionListener(action -> {
            currentEvent = event;
            new AjouterEvent(this).show();
        });

       
     
          
        return eventModel;
    }
    
}
