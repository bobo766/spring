package ru.korshun.eda.requests;

import javax.validation.constraints.NotBlank;

public class InsertLocationRequest {

    @NotBlank
    private int id;

    @NotBlank
    private double lat, lon;

    @NotBlank
    private boolean isGpsEnable, isNetworkEnable;

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean getIsGpsEnable() {
        return isGpsEnable;
    }

    public boolean getIsNetworkEnable() {
        return isNetworkEnable;
    }
}
