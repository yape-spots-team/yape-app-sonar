package com.software.yapespots.model;

import java.util.Collection;

public class User {
    private String name;
    private Collection<Place> favorites;

    public Collection<Place> getFavorites() {
        return favorites;
    }

    public boolean addFavorites(Place newPlace) {
        if (this.favorites.add(newPlace)) {
            return true;
        }
        return false;
    }
}