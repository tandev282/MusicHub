package com.tandev.musichub.model.chart.home.home_new.editor_theme_3;

import com.tandev.musichub.model.chart.home.home_new.item.HomeDataItem;
import com.tandev.musichub.model.chart.home.home_new.option.HomeOption;
import com.tandev.musichub.model.playlist.DataPlaylist;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeDataItemPlaylistEditorTheme3 implements HomeDataItem, Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String sectionType;
    private String viewType;
    private ArrayList<DataPlaylist> items;
    private String link;
    private String itemType;
    private HomeOption options;
    private String sectionId;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public ArrayList<DataPlaylist> getItems() {
        return items;
    }

    public void setItems(ArrayList<DataPlaylist> items) {
        this.items = items;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public HomeOption getOptions() {
        return options;
    }

    public void setOptions(HomeOption options) {
        this.options = options;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}
