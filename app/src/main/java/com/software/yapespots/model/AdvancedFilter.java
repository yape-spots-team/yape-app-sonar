package com.software.yapespots.model;
import java.util.HashMap;

// bar florist gas_station hardware_store restaurant store lodging museum
public class AdvancedFilter extends Filter {
    private  Integer distance;
    private String type="";
    private Boolean available;
    HashMap<String, Object> aFilters;

    public AdvancedFilter(){
        aFilters = new HashMap<String, Object>();
    }


    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer newRadius) {
        this.distance= newRadius;
    }

    public String getType(){ return type;}

    public void setType(String newType){this.type = newType;}

    public Boolean getAvailability(){ return available;}

    public void setAvailable(Boolean newAvailable){ this.available = newAvailable;}

    public HashMap<String,Object> getAdvancedFilters(){
        aFilters.put("radius",distance);
        aFilters.put("type",type);
        aFilters.put("opennow",available);
        return aFilters;
    }

}