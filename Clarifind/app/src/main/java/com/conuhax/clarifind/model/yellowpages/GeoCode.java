package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class GeoCode {
    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return "latitude: " + latitude + "\nlongitude " + longitude;
    }
}
