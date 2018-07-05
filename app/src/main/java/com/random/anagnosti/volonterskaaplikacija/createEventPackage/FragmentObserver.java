package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.Observable;

/**
 * Observer za fragmente
 */
public class FragmentObserver extends Observable{
    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}
