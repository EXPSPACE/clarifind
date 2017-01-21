package com.conuhax.clarifind.model.yellowpages;

import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Listings {
    String parentId;
    boolean isParent;
    private String distance;

    Content content;

    private String id;
    private String name;
    private Address address;
    private GeoCode geoCode;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("distance: " + distance + "\nid: " + id + "\nname: " + name + "\ngeoCode: " + geoCode + "\n" + address.toString() +"\n\n");
        return sb.toString();
    }
}
