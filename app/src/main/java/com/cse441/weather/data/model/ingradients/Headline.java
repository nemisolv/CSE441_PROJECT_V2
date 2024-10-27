package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("EffectiveDate")
    private String effectiveDate;

    @SerializedName("EffectiveEpochDate")
    private long effectiveEpochDate;

    @SerializedName("Severity")
    private int severity;

    @SerializedName("Text")
    private String text;

    @SerializedName("Category")
    private String category;

    @SerializedName("EndDate")
    private String endDate;

    @SerializedName("EndEpochDate")
    private long endEpochDate;


    @SerializedName("Link")
    private String link;

    // Getters and setters
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public long getEffectiveEpochDate() {
        return effectiveEpochDate;
    }

    public void setEffectiveEpochDate(long effectiveEpochDate) {
        this.effectiveEpochDate = effectiveEpochDate;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getEndEpochDate() {
        return endEpochDate;
    }

    public void setEndEpochDate(long endEpochDate) {
        this.endEpochDate = endEpochDate;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Headline{" +
                "effectiveDate='" + effectiveDate + '\'' +
                ", effectiveEpochDate=" + effectiveEpochDate +
                ", severity=" + severity +
                ", text='" + text + '\'' +
                ", category='" + category + '\'' +
                ", endDate=" + endDate +
                ", endEpochDate=" + endEpochDate +
                ", link='" + link + '\'' +
                '}';
    }
}
