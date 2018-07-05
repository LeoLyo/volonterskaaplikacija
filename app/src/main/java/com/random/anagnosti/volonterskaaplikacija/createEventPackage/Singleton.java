package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa koja predsdtavlja jedan Event pri kreiranju istog. Ovde se cuvaju svi podaci o eventu i brisu nakon ubacivanja event-a u bazu.
 */
public class Singleton {

    private static Singleton mInstance = null;
    public ArrayList<EventDay> mEventDays;
    public ArrayList<EventRole> mEventRoles;
    public ArrayList<EventPerson> mEventPeople;
    public ArrayList<Date> dates;
    public ArrayList<String> mUsedEmails;
    public boolean[] somethingDoneInEveryPart;
    public Bitmap eventImageBitmap;
    public Uri uriEventImage;
    public long currentNumberOfDays;
    public boolean currentEventDaysChanged;
    public boolean dateStartChanged;
    public boolean dateEndChanged;
    public String locationName;
    public String locationAddress;
    public GeoPoint locationCoordinates;
    public String eventName;
    public String organiserName;
    public String descriptionOfEvent;


    /**
     * Prazan konstruktor za podesavanje pocetnog stanja svake promenljive pri kreiranju Singleton-a.
     */
    private Singleton(){
        mEventDays = new ArrayList<>();
        mEventRoles = new ArrayList<>();
        mEventPeople = new ArrayList<>();
        mUsedEmails = new ArrayList<>();
        dates = new ArrayList<>();
        somethingDoneInEveryPart = new boolean[5];
        eventImageBitmap=Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
        currentNumberOfDays=-7;
        currentEventDaysChanged=false;
        dateStartChanged=false;
        dateEndChanged=false;
        eventName="";
        organiserName="";
        descriptionOfEvent="";
        locationName="";
        locationAddress="";
        locationCoordinates=new GeoPoint(0,0);

    }

    /**
     * Singleton patern.
     */
    public static Singleton Instance(){

        if(mInstance == null)
            mInstance = new Singleton();

        return mInstance;
    }

    /**
     * Metoda koja cisti sva polja i stavlja ih u nepostojece stanje, ako to nije moguce onda u nulto stanje.
     */
    public void destroyS(){
        mEventDays.clear();
        mEventRoles.clear();
        mEventPeople.clear();
        mUsedEmails.clear();
        dates.clear();
        somethingDoneInEveryPart=new boolean[0];
        eventImageBitmap=Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
        currentNumberOfDays=-7;
        currentEventDaysChanged=false;
        dateStartChanged=false;
        dateEndChanged=false;
        mInstance=null;
        eventName="";
        organiserName="";
        descriptionOfEvent="";
        locationName="";
        locationAddress="";
        locationCoordinates=new GeoPoint(0,0);

    }

}
