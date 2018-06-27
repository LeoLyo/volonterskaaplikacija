package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;

public class EventPerson {
    private String email;
    private EventRole roleOfIndividual;
    private ArrayList<EventPerson> subordinates;
    private EventPerson parentOfIndividual;
    private ArrayList<Integer> previousPositions;
    private String color;
    private int depth;

    public EventPerson(String email, EventRole roleOfIndividual, ArrayList<EventPerson> subordinates) {
        this.email = email;
        this.roleOfIndividual = roleOfIndividual;
        this.subordinates = subordinates;
    }

    public EventPerson( String email, EventRole roleOfIndividual, int depth, ArrayList<Integer> previousPositions) {
        this.email = email;
        this.roleOfIndividual = roleOfIndividual;
        this.subordinates = new ArrayList<>();
        this.depth=depth;
        this.previousPositions=previousPositions;
        this.color = "FFFFFF";
    }

    public EventPerson( String email, EventRole roleOfIndividual, int depth, EventPerson parentOfIndividual) {
        this.email = email;
        this.roleOfIndividual = roleOfIndividual;
        this.subordinates = new ArrayList<>();
        this.depth=depth;
        this.previousPositions=new ArrayList<>();
        this.parentOfIndividual=parentOfIndividual;
        this.color = "FFFFFF";
    }

    public EventPerson( String email, EventRole roleOfIndividual, int depth) {
        this.email = email;
        this.roleOfIndividual = roleOfIndividual;
        this.subordinates = new ArrayList<>();
        this.depth=depth;
        this.previousPositions=new ArrayList<>();
        this.color = "FFFFFF";
    }


    public void darkenBy(int howMuch){
        int count = howMuch;
        char[] charOfColor = color.toCharArray();
        int[] intOfColor = new int[6];
        for(int i=0;i<intOfColor.length;i++){
            intOfColor[i]=(int)charOfColor[i];
        }
        while(count>0){

            count--;

            //Oduzmemo neparne indekse boja za jedan
            intOfColor[5]--;
            intOfColor[3]--;
            intOfColor[1]--;

            if(intOfColor[5]<48){
                // Ako je pozicija neparnih indeksa na ASCII tabeli otisla ispod pozciije '0',
                // prebacujemo neparne indekse na poziciju 70, to jest poziciju hex broja 'F',
                // dok parne indekse oduzmemo za jedan.

                intOfColor[5]=70;
                intOfColor[3]=70;
                intOfColor[1]=70;

                intOfColor[4]--;
                intOfColor[2]--;
                intOfColor[0]--;
                if(intOfColor[4]<48){
                    // Posto smo dosli do situacije da su parni indeksi pali ispod '0', sve
                    // indekse stavljamo na poziciju broja '0', to jest 48, ili te crne boje.

                    intOfColor[5]=48;
                    intOfColor[3]=48;
                    intOfColor[1]=48;

                    intOfColor[4]=48;
                    intOfColor[2]=48;
                    intOfColor[0]=48;

                    //Posto ne postoji tamnija boja od crne, izlazimo iz petlje.

                    break;
                }
                else if(intOfColor[4]<65){
                    // Ako je pri smanjenju zbog prebacivanje neparnih indeksa doslo do toga
                    // da su parni indeksi stigli do pozicije ispod 65, to jest ispod hexa
                    // broja 'A', onda se prebacuju na poziciju broja '9', to jest 57.

                    intOfColor[4]=57;
                    intOfColor[2]=57;
                    intOfColor[0]=57;
                }
            }else if(intOfColor[5]<65){
                // Ako je pozicija neparnih indeksa na ASCII tabelo otisla ispod pozicije hexa
                // broja 'A', to jest pozicije 65, onda se prebacuju na poziciju broja '9', to
                // jest 57.

                intOfColor[5]=57;
                intOfColor[3]=57;
                intOfColor[1]=57;
            }
        }
        for(int i=0;i<intOfColor.length;i++){
            charOfColor[i]=(char)intOfColor[i];
        }
        String newColor = new String(charOfColor);
        this.color=newColor;

    }

    public EventPerson getParentOfIndividual() {
        return parentOfIndividual;
    }

    public void setParentOfIndividual(EventPerson parentOfIndividual) {
        this.parentOfIndividual = parentOfIndividual;
    }

    public void addNewPreviousPosition(int position){
        previousPositions.add(position);
    }
    public ArrayList<Integer> getPreviousPositions() {
        return previousPositions;
    }

    public void setPreviousPositions(ArrayList<Integer> previousPositions) {
        this.previousPositions = previousPositions;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
    public String toString() {
        return email;
    }
}
