package com.conuhax.clarifind.model.yellowpages;

import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class FindBusinessResponse {
    private Summary summary;
    private Listings[] listings;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Listings listing : listings) {
            sb.append(listing.toString() + "\n");
        }
        return sb.toString();
    }
}
