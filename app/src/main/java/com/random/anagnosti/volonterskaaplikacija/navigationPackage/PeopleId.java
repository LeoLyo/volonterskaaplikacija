package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.support.annotation.NonNull;

public class PeopleId {

    public String personId;

    public <T extends PeopleId>T withId(@NonNull final String id){
        this.personId=id;
        return (T) this;
    }
}
