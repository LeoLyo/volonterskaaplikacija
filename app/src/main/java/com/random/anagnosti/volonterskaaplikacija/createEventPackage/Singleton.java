package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    private static Singleton mInstance = null;
    public ArrayList<EventDay> mEventDays;
    public ArrayList<EventRole> mEventRoles;
    public long currentNumberOfDays;
    public boolean currentEventDaysChanged;
    public boolean dateStartChanged;
    public boolean dateEndChanged;

    private Singleton(){
        mEventDays = new ArrayList<>();
        mEventRoles = new ArrayList<>();
        currentNumberOfDays=-7;
        currentEventDaysChanged=false;
        dateStartChanged=false;
        dateEndChanged=false;
    }

    public static Singleton Instance(){

        if(mInstance == null)
            mInstance = new Singleton();

        return mInstance;
    }

    public void destroyS(){
        mEventDays.clear();
        mEventRoles.clear();
        currentNumberOfDays=-7;
        currentEventDaysChanged=false;
        dateStartChanged=false;
        dateEndChanged=false;
        mInstance=null;
    }

}
