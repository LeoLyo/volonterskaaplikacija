package com.random.anagnosti.volonterskaaplikacija;

import java.util.ArrayList;

public class EventRole {
    private String name;
    private long uniqueId;
    private ArrayList<EventRole> subordinates;
    private ArrayList<EventRoleObligation> obligations;

    public EventRole(String name, long uniqueId, ArrayList<EventRole> subordinates, ArrayList<EventRoleObligation> obligations) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.subordinates = subordinates;
        this.obligations = obligations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public ArrayList<EventRole> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(ArrayList<EventRole> subordinates) {
        this.subordinates = subordinates;
    }

    public ArrayList<EventRoleObligation> getObligations() {
        return obligations;
    }

    public void setObligations(ArrayList<EventRoleObligation> obligations) {
        this.obligations = obligations;
    }
}
