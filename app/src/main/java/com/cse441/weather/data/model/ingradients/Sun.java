package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

public class Sun {
    @SerializedName("Rise")
    private String rise;
    @SerializedName("EpochRise")
    private long epochRise;
    @SerializedName("Set")
    private String set;
    @SerializedName("EpochSet")
    private long epochSet;

    // Getters and setters for encapsulation
    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public long getEpochRise() {
        return epochRise;
    }

    public void setEpochRise(long epochRise) {
        this.epochRise = epochRise;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public long getEpochSet() {
        return epochSet;
    }

    public void setEpochSet(long epochSet) {
        this.epochSet = epochSet;
    }

    @Override
    public String toString() {
        return "Sun{" +
                "rise=" + rise +
                ", epochRise=" + epochRise +
                ", set=" + set +
                ", epochSet=" + epochSet +
                '}';
    }
}
