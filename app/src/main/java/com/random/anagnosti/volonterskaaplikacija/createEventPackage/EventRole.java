package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;

public class EventRole {
    private String name, description;
    private long uniqueId;
    private ArrayList<EventRole> subordinates;
    private boolean isChecked;

    private ArrayList<EventRoleObligation> obligations;

    public EventRole(String name, long uniqueId, ArrayList<EventRole> subordinates, ArrayList<EventRoleObligation> obligations) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.subordinates = subordinates;
        this.obligations = obligations;
        this.isChecked=false;
    }

    //FOR TESTING
    public EventRole(String name, long uniqueId, ArrayList<EventRole> subordinates, String description){
        this.name = name;
        this.uniqueId = uniqueId;
        this.subordinates = subordinates;
        this.description=description;
        this.isChecked=false;
    }
    public EventRole(String name, long uniqueId){
        this.name = name;
        this.uniqueId = uniqueId;
        this.description="";
        this.isChecked=false;
    }
    public String getAllSubordinatesInString(){
        String sSubs="";
        if(subordinates.size()==0){
            return "No subordinates.";
        }
        else if(subordinates.size()==1){
            return subordinates.get(subordinates.size()-1).getName()+".";
        }
        for(int i=0;i<subordinates.size()-1;i++){
            sSubs+=sSubs+subordinates.get(i).getName()+", ";
        }
        sSubs+=subordinates.get(subordinates.size()-1).getName()+".";
        return sSubs;

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public void removeSubordinates(){
        subordinates.clear();
    }
    public ArrayList<EventRoleObligation> getObligations() {
        return obligations;
    }

    public void setObligations(ArrayList<EventRoleObligation> obligations) {
        this.obligations = obligations;
    }

    @Override
    public String toString() {
        return name;
    }
}
