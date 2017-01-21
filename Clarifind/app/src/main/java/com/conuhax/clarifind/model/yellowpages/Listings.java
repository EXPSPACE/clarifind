package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Listings {
    private String parentId;
    private boolean isParent;
    private String distance;
    private String id;
    private String name;
    private Address address;
    private GeoCode geoCode;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("distance: " + distance + "\nid: " + id + "\nname: " + name + "\n" + geoCode.toString() + "\n" + address.toString() + "\n\n");
        return sb.toString();
    }
}
