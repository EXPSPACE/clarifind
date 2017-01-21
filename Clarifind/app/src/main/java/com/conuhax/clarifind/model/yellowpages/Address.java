package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Address {
    String street;
    String city;
    String prov;
    String pcode;

    @Override
    public String toString() {
        return "street: " + street + "\ncity: " + city + "\nprov: " + prov + "\npcode: " + pcode;
    }
}
