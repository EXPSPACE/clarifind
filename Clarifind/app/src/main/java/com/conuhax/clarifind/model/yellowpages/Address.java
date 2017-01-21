package com.conuhax.clarifind.model.yellowpages;

import com.google.gson.annotations.Expose;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class Address {
    private String street;
    private String city;
    private String prov;
    private String pcode;

    @Override
    public String toString() {
        return "street: " + street + "\ncity: " + city + "\nprov: " + prov + "\npcode: " + pcode;
    }
}
