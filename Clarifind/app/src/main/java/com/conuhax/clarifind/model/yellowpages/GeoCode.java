package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class GeoCode {
    String latitude;
    String longitude;

    @Override
    public String toString() {
        return "latitude: " + latitude + "\nlongitude " + longitude;
    }
}