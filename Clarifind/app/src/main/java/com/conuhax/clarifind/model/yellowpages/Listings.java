package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Listings {
    public String parentId;
    public boolean isParent;
    public String distance;
    public String id;
    public String name;
    public Address address;
    public GeoCode geoCode;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("distance: " + distance + "\nid: " + id + "\nname: " + name + "\n" + geoCode.toString() + "\n" + address.toString() + "\n\n");
        return sb.toString();
    }
}
