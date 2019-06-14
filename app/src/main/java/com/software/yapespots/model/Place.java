package com.software.yapespots.model;

import java.util.ArrayList;

public class Place {
    private String id;
    private String lat;
    private String lng;
    private String name;
    private ArrayList<String> type;
    private Boolean opennow;
    //private DetailPlace detailPlace;

    public Boolean getOpennow(){ return opennow;}

    public void setOpennow(Boolean open){  opennow = open;}

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

        /*public DetailPlace getDetailPlace() {
            return detailPlace;
        }

        public void setDetailPlace(DetailPlace newDetailPlace) {
            this.detailPlace = newDetailPlace;
        }*/
}