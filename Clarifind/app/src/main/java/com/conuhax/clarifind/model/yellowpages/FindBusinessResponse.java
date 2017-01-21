package com.conuhax.clarifind.model.yellowpages;

/**
 * Created by NSPACE on 1/21/2017.
 */

public class FindBusinessResponse {

    private Listings[] listings;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Listings listing : listings) {
            sb.append(listing.toString() + "\n");
        }
        return sb.toString();
    }
}
