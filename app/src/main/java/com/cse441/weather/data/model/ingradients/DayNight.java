package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

public class DayNight {

    @SerializedName("Icon")
    private int icon;


    @SerializedName("HasPrecipitation")
    private boolean hasPrecipitation;

    @SerializedName("PrecipitationType")
    private String precipitationType;

    @SerializedName("ShortPhrase")
    private String shortPhrase;

    @SerializedName("LongPhrase")
    private String longPhrase;

    @SerializedName("PrecipitationProbability")
    private int precipitationProbability;

    @SerializedName("ThunderstormProbability")
    private int thunderstormProbability;

    @SerializedName("RainProbability")
    private int rainProbability;

    @SerializedName("SnowProbability")
    private int snowProbability;

    @SerializedName("IceProbability")
    private int iceProbability;

    @SerializedName("Wind")
    private Wind wind;



    // Getters and Setters for each field
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }



    public boolean isHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setHasPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }


    public String getShortPhrase() {
        return shortPhrase;
    }

    public void setShortPhrase(String shortPhrase) {
        this.shortPhrase = shortPhrase;
    }

    public String getLongPhrase() {
        return longPhrase;
    }

    public void setLongPhrase(String longPhrase) {
        this.longPhrase = longPhrase;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public int getThunderstormProbability() {
        return thunderstormProbability;
    }

    public void setThunderstormProbability(int thunderstormProbability) {
        this.thunderstormProbability = thunderstormProbability;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }

    public int getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(int snowProbability) {
        this.snowProbability = snowProbability;
    }

    public int getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(int iceProbability) {
        this.iceProbability = iceProbability;
    }

    public boolean hasPrecipitation() {
        return hasPrecipitation;
    }

    public void setPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Day{" +
                "icon=" + icon +
                ", hasPrecipitation=" + hasPrecipitation +
                ", precipitationType='" + precipitationType + '\'' +
                ", shortPhrase='" + shortPhrase + '\'' +
                ", longPhrase='" + longPhrase + '\'' +
                ", precipitationProbability=" + precipitationProbability +
                ", thunderstormProbability=" + thunderstormProbability +
                ", rainProbability=" + rainProbability +
                ", snowProbability=" + snowProbability +
                ", iceProbability=" + iceProbability +
                ", wind=" + wind +
                '}';
    }

}
