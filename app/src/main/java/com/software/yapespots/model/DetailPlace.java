package com.software.yapespots.model;

import java.util.Collection;
import java.util.List;

public class DetailPlace {
    private Place place;
    private boolean detailsFetched;
    private String address;
    private List addresComponents;
    private String phoneNumber;
    private Collection<String> photos;
    private boolean open;

    private void fetchData() {
    }

    public String getAddress() {
        return address;
    }

    public List getAddresComponents() {
        return addresComponents;
    }

    public String getPlaceIdentidier() {
        return this.place.getId();
    }

    /*public LatLng getPlaceLatLng() {
        return this.place.getLatLng();
    }*/

    public String getPlaceName() {
        return this.place.getName();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    //getPlaceType
}