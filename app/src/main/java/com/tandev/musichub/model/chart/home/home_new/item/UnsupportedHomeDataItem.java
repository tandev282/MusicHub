package com.tandev.musichub.model.chart.home.home_new.item;

public class UnsupportedHomeDataItem implements HomeDataItem {
    private final String sectionType;
    private final String sectionId;

    public UnsupportedHomeDataItem(String sectionType, String sectionId) {
        this.sectionType = sectionType;
        this.sectionId = sectionId;
    }

    public String getSectionType() {
        return sectionType;
    }

    public String getSectionId() {
        return sectionId;
    }
}
