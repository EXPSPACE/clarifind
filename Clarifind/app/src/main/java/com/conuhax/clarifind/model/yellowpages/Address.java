package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Address {
    public String street;
    public String city;
    public String prov;
    public String pcode;

    @Override
    public String toString() {
        return "street: " + street + "\ncity: " + city + "\nprov: " + prov + "\npcode: " + pcode;
    }
}
