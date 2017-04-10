package com.uoa.compsci_702_g12.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by z on 10/04/17.
 */

public class Buddy {

    @SerializedName("VolunteerId")
    private int volunteerId;
    @SerializedName("NewbieId")
    private int newbieId;

    public Buddy(int volunteerId, int newbieId) {
        this.volunteerId = volunteerId;
        this.newbieId = newbieId;
    }
}
