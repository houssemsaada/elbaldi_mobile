package com.eratech.entities;

import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Meddeb sofien
 */
public class Event {
    
  private int id ;
  private  String title_event ,description_event,organisation;
  private Date time_event;

    public Event() {
    }

    public Event(int id, String title_event, String description_event, String organisation, Date time_event) {
        this.id = id;
        this.title_event = title_event;
        this.description_event = description_event;
        this.organisation = organisation;
        this.time_event = time_event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_event() {
        return title_event;
    }

    public void setTitle_event(String title_event) {
        this.title_event = title_event;
    }

    public String getDescription_event() {
        return description_event;
    }

    public void setDescription_event(String description_event) {
        this.description_event = description_event;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public Date getTime_event() {
        return time_event;
    }

    public void setTime_event(Date time_event) {
        this.time_event = time_event;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", title_event=" + title_event + ", description_event=" + description_event + ", organisation=" + organisation + ", time_event=" + time_event + '}';
    }


    
    

    
}
