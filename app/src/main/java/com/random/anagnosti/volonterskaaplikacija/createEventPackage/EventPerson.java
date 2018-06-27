package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;

public class EventPerson {
    private String name;
    private String email;
    private EventRole roleOfIndividual;
    private ArrayList<EventPerson> subordinates;

    public EventPerson(String name, String email, EventRole roleOfIndividual, ArrayList<EventPerson> subordinates) {
        this.name = name;
        this.email = email;
        this.roleOfIndividual = roleOfIndividual;
        this.subordinates = subordinates;
    }

    public EventPerson(String name, String email, EventRole roleOfIndividual) {
        this.name = name;
        this.email = email;
        this.roleOfIndividual = roleOfIndividual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EventRole getRoleOfIndividual() {
        return roleOfIndividual;
    }

    public void setRoleOfIndividual(EventRole roleOfIndividual) {
        this.roleOfIndividual = roleOfIndividual;
    }

    public ArrayList<EventPerson> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(ArrayList<EventPerson> subordinates) {
        this.subordinates = subordinates;
    }
}
