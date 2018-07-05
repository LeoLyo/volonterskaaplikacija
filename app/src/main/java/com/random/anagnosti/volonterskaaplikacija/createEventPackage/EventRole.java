package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;

/**
 * Klasa koja predstavlja ulogu na festivalu, sa svojim podacima: ime i opis uloge, jedinstven id, listu sledbenika,  kao i boolean za stanje uloge: checked ili not checked.
 */
public class EventRole {
    private String name, description;
    private long uniqueId;
    private ArrayList<EventRole> subordinates;
    private boolean isChecked;


    /**
     * Konstruktor koji preuzima ime, jedinstven id, kao i listu sledbenika.
     * @param name ime uloge
     * @param uniqueId id uloge
     * @param subordinates lista sledbenika ove uloge, to jest lista uloga kojima je ova uloga nadredjeni
     */
    public EventRole(String name, long uniqueId, ArrayList<EventRole> subordinates) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.subordinates = subordinates;
        this.isChecked=false;
    }

    /**
     * Konstruktor koji preuzima ime, jedinstven id, listu sledbenika i opis uloge.
     * @param name ime uloge
     * @param uniqueId id uloge
     * @param subordinates lista sledbenika sledbenika ove uloge, to jest lista uloga kojima je ova uloga nadredjeni
     * @param description opis zaduzenja uloge tokom eventa.
     */
    //FOR TESTING
    public EventRole(String name, long uniqueId, ArrayList<EventRole> subordinates, String description){
        this.name = name;
        this.uniqueId = uniqueId;
        this.subordinates = subordinates;
        this.description=description;
        this.isChecked=false;
    }

    /**
     * Konstruktor koji preuzima ime i jedinstven id
     * @param name ime uloge
     * @param uniqueId id uloge
     */
    public EventRole(String name, long uniqueId){
        this.name = name;
        this.uniqueId = uniqueId;
        this.description="";
        this.isChecked=false;
    }

    /**
     * Metoda za preuzimanje svih sledbenika u obliku formatiranog stringa.
     */
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

    /**
     * Metoda za uklanjanje svih sledbenika iz liste.
     */
    public void removeSubordinates(){
        subordinates.clear();
    }

    /**
     * Vraca ime uloge.
     */
    @Override
    public String toString() {
        return name;
    }
}
