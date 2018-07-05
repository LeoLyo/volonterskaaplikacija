package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.support.annotation.NonNull;

/**
 * Poseban jedinstven id osobe.
 */
public class PeopleId {

    public String personId;

    /**
     * Jedinstvena metoda za vracanje id-a osobe.
     */
    public <T extends PeopleId>T withId(@NonNull final String id){
        this.personId=id;
        return (T) this;
    }
}
