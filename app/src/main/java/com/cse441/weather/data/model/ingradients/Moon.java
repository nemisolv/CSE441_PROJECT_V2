package com.cse441.weather.data.model.ingradients;


import com.google.gson.annotations.SerializedName;

public class Moon {
    @SerializedName("Rise")
    private String rise;

    @SerializedName("EpochRise")
    private long epochRise;

    @SerializedName("Set")
    private String set;

    @SerializedName("EpochSet")
    private long epochSet;

    @SerializedName("Phase")
    private String phase;

    @SerializedName("Age")
    private int age;

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

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Moon{" +
                "rise=" + rise +
                ", epochRise=" + epochRise +
                ", set=" + set +
                ", epochSet=" + epochSet +
                ", phase='" + phase + '\'' +
                ", age=" + age +
                '}';
    }

}
