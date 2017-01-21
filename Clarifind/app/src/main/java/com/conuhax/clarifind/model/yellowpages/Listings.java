package com.conuhax.clarifind.model.yellowpages;

import java.util.Arrays;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Listings {
    String distance;
    String id;
    String name;
    Address[] address;
    String geoCode;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("distance: " + distance + "\nid: " + id + "\nname: " + "\ngeoCode: " + geoCode + "\n");
        for(Address specAdress : address) {
            sb.append(specAdress.toString() + "\n");
        }
        return sb.toString();
    }
}
