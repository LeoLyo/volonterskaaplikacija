package com.random.anagnosti.volonterskaaplikacija;

import java.util.ArrayList;

public class Singleton {

    private static Singleton mInstance = null;
    public ArrayList<EventDay> mEventDays;
    public long currentNumberOfDays;

    private Singleton(){
        mEventDays = new ArrayList<>();
        currentNumberOfDays=-7;
    }

    public static Singleton Instance(){

        if(mInstance == null)
            mInstance = new Singleton();

        return mInstance;
    }



}
