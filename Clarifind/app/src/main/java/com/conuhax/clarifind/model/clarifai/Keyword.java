package com.conuhax.clarifind.model.clarifai;

/**
 * Created by michal wozniak on 1/21/2017.
 */

public class Keyword {

    private String name;
    private float value;

    public Keyword(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }
}
