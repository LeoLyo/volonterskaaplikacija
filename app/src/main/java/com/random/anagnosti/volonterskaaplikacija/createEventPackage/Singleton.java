package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    private static Singleton mInstance = null;
    public ArrayList<EventDay> mEventDays;
    public ArrayList<EventRole> mEventRoles;
    public ArrayList<EventPerson> mEventPeople;
    public ArrayList<String> mUsedEmails;
    public ArrayList<Boolean> somethingDoneInEveryPart;
    public long currentNumberOfDays;
    public boolean currentEventDaysChanged;
    public boolean dateStartChanged;
    public boolean dateEndChanged;
    public String eventName;
    public String organiserName;
    public String descriptionOfEvent;

    private Singleton(){
        mEventDays = new ArrayList<>();
        mEventRoles = new ArrayList<>();
        mEventPeople = new ArrayList<>();
        mUsedEmails = new ArrayList<>();
        somethingDoneInEveryPart = new ArrayList<>();
        currentNumberOfDays=-7;
        currentEventDaysChanged=false;
        dateStartChanged=false;
        dateEndChanged=false;
        eventName="";
        organiserName="";
        descriptionOfEvent="";
    }

    public static Singleton Instance(){

        if(mInstance == null)
            mInstance = new Singleton();

        return mInstance;
    }

    public void destroyS(){
        mEventDays.clear();
        mEventRoles.clear();
        mEventPeople.clear();
        mUsedEmails.clear();
        somethingDoneInEveryPart.clear();
        currentNumberOfDays=-7;
        currentEventDaysChanged=false;
        dateStartChanged=false;
        dateEndChanged=false;
        mInstance=null;
        eventName="";
        organiserName="";
        descriptionOfEvent="";
    }

}
